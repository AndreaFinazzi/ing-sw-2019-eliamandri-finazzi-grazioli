package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

// Server main class

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ConnectionResponseEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


//TODO CHECK CONCURRENCY ISSUES OF THE WHOLE LOGIC!!!

public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private ServerSocketManager serverSocketManager;
    private HashMap<AbstractClientHandler, MatchBuilder> playerToMatchMap;

    // Match-threads pool
    private ExecutorService matchesExecutor = Executors.newCachedThreadPool();
    // Network threads pool
    private ExecutorService networkExecutor = Executors.newFixedThreadPool(2);

    // Player.username to ClientHandler
    // TODO change it to support RMI. Player.username to Client.ID
    private HashMap<String, AbstractClientHandler> playerToClientHandler;
    private MatchBuilder nextMatch;
    private Registry registry;
    private Timer timer;
    private int currentClientID;
    private boolean online;

    private Server() {
        LOGGER.info("Creating Server"); //TODO move to messages
        timer = new Timer();
        playerToMatchMap = new HashMap<>();

        nextMatch = new MatchBuilder();
        currentClientID = 0;
        try {
            registry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public synchronized MatchBuilder getNextMatch() {
        return nextMatch;
    }

    public void startServerSocket() {
        LOGGER.info("Starting socket manager");
        networkExecutor.execute(new ServerSocketManager(this, Config.CONFIG_SERVER_SOCKET_PORT));
    }

    public void startServerRMI() {
        LOGGER.info("Starting RMI manager");
        networkExecutor.execute(new ServerRMIManager(this));
    }

    private void mapPlayerToMatch(AbstractClientHandler clientHandler, MatchBuilder matchController) {
        playerToMatchMap.put(clientHandler, matchController);
    }

    //TODO It may not be the best choice to block Server...
    public synchronized void addPlayer(String player, AbstractClientHandler clientHandler) throws MaxPlayerException, PlayerAlreadyPresentException {
        stopTimer();

        nextMatch.getMatchController().addPlayer(player);
        mapPlayerToMatch(clientHandler, nextMatch);

        if (nextMatch.getMatchController().isFull()) {
            startNextMatch();
        } else if (nextMatch.getMatchController().isReady()) {
            startTimer();
        }
    }

    public synchronized void addPlayer(AbstractClientHandler clientHandler) {
        stopTimer();

        nextMatch.getMatchController().addPlayer(clientHandler);
        clientHandler.setEventsQueue(nextMatch.getEventsQueue());

        mapPlayerToMatch(clientHandler, nextMatch);
        clientHandler.send(new ConnectionResponseEvent("Username required."));
    }

    public synchronized void addPlayer(int clientID, AbstractClientHandler clientHandler) {
        stopTimer();

        nextMatch.getMatchController().addPlayer(clientHandler);
        clientHandler.setEventsQueue(nextMatch.getEventsQueue());

        mapPlayerToMatch(clientHandler, nextMatch);
        clientHandler.send(clientID, new ConnectionResponseEvent("Username required."));
    }

    private synchronized void startNextMatch() {
        LOGGER.info("Next match initialization: start");

        //TODO: move to Messages
        LOGGER.info("Game starting");

        // Kick-off next game
        matchesExecutor.execute(nextMatch);

        // initialize next starting match
        nextMatch = new MatchBuilder();
    }

    public void removePlayer(String player) {
        playerToMatchMap.remove(player);
    }

    public void removePlayer(Player player) {
        playerToMatchMap.remove(player.getPlayerNickname());
    }


    // ####################### TIMER #######################
    public void startTimer() {
        LOGGER.info("Timer tarted"); //TODO move to messages
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                LOGGER.info("New game timeout occurred, starting next match"); //TODO move to messages
                startNextMatch();
            }
        }, Config.CONFIG_SERVER_NEW_GAME_TIMEOUT);
    }

    public void stopTimer() {
        timer.cancel();
    }


    // ####################### RMI #######################
    public synchronized Registry getRegistry() {
        return registry;
    }

    public synchronized int getCurrentClientID() {
        currentClientID++;
        return currentClientID;
    }

    private void close() {

    }

    public static void main(String[] args) {
        LOGGER.info(Messages.MESSAGE_LOGGING_INFO_SERVER_STARTED);

        Server server = new Server();

        try {
            server.startServerSocket();
            server.startServerRMI();
        } finally {
            //TODO do something to properly control Server shutdown
        }
    }

    public boolean isUp() {
        return online;
    }

    public void setUp(boolean online) {
        this.online = online;
    }
}