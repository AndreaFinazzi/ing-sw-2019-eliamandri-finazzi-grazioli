package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

// Server main class

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ConnectionResponseEvent;
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
    private HashMap<Integer, MatchBuilder> playerToMatchMap = new HashMap<>();
    private HashMap<Integer, AbstractClientHandler> clientIDToClientHandlerMap = new HashMap<>();

    // Match-threads pool
    private ExecutorService matchesExecutor = Executors.newCachedThreadPool();
    // Network threads pool
    private ExecutorService networkExecutor = Executors.newFixedThreadPool(2);

    // Player.username to ClientHandler
    // TODO change it to support RMI. Player.username to Client.ID
    private MatchBuilder nextMatch;
    private Registry registry;
    private boolean online;
    private Timer timer = new Timer();
    private int currentClientID = 0;

    private Server() {
        LOGGER.info("Creating Server"); //TODO move to messages

        nextMatch = new MatchBuilder();

        try {
            registry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public synchronized MatchBuilder getNextMatch() {
        LOGGER.info("Getting nextMatch");
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

    private void mapPlayerToMatch(Integer clientID, MatchBuilder matchController) {
        playerToMatchMap.put(clientID, matchController);
    }


    public synchronized void addClient(int clientID, AbstractClientHandler clientHandler) {
        stopTimer();

        nextMatch.getMatchController().signClient(clientID, clientHandler);
        clientHandler.setEventsQueue(nextMatch.getEventsQueue());

        mapPlayerToMatch(clientID, nextMatch);

        clientHandler.sendTo(clientID, new ConnectionResponseEvent(clientID, "Username required."));

        //TODO verify
        if (nextMatch.getMatchController().isFull()) {
            startNextMatch();
        } else if (nextMatch.getMatchController().isReady()) {
            startTimer();
        }
    }

    public void removeClient(int clientID) {

        clientIDToClientHandlerMap.remove(clientID);
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

    public boolean isUp() {
        return online;
    }

    public void setUp(boolean online) {
        this.online = online;
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

        Thread currentThread = Thread.currentThread();
        currentThread.setName("MAIN SERVER THREAD");
    }
}