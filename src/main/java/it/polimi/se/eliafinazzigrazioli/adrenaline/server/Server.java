package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

// Server main class

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller.MatchController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


//TODO CHECK CONCURRENCY ISSUES OF THE WHOLE LOGIC!!!

public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private ServerSocketManager serverSocketManager;
    private HashMap<String, MatchController> playerToMatchMap;
    private HashMap<String, AbstractClientHandler> playerToClientHandler;
    private MatchController nextMatch;
    private Registry registry;
    private Timer timer;


    private Server() {
        LOGGER.info("Creating Server"); //TODO move to messages
        timer = new Timer();
        playerToMatchMap = new HashMap<>();
        nextMatch = new MatchController();
        try {
            registry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public void startServerSocket() {
        LOGGER.info("Starting socket manager");
        serverSocketManager = new ServerSocketManager(this, Config.CONFIG_SERVER_SOCKET_PORT);
        serverSocketManager.startServerSocket();
    }

    public void startServerRMI() {
        LOGGER.info("Starting RMI manager");

    }

    private void mapPlayerToMatch(String player, MatchController matchController) {
        playerToMatchMap.put(player, matchController);
    }

    //TODO It may not be the best choice to block Server...
    public synchronized void addPlayer(String player) {
        stopTimer();

        nextMatch.addPlayer(player);
        mapPlayerToMatch(player, nextMatch);

        if (nextMatch.isFull()) {
            startNextMatch();
        } else if (nextMatch.isReady()) {
            startTimer();
        }
    }

    public synchronized void addPlayer(String player, AbstractClientHandler clientHandler) {
        stopTimer();

        nextMatch.addPlayer(player);
        mapPlayerToMatch(player, nextMatch);
        playerToClientHandler.put(player, clientHandler);

        if (nextMatch.isFull()) {
            startNextMatch();
        } else if (nextMatch.isReady()) {
            startTimer();
        }
    }

    private synchronized void startNextMatch() {
        // Add next match EventController as an Observer of RemoteViews
        ArrayList<String> nextPlayingPlayers = nextMatch.getPlayersNicknames();
        for (String player : nextPlayingPlayers) {
            playerToClientHandler.get(player).bindViewToEventController(nextMatch.getEventController());
        }

        nextMatch.initMatch();
        //TODO: move to Messages
        LOGGER.info("Game started");
        nextMatch = new MatchController();
    }

    public synchronized void addPlayer(String player, MatchController matchController) {
        nextMatch.addPlayer(player);
        mapPlayerToMatch(player, matchController);
    }

    public void removePlayer(String player) {
        playerToMatchMap.remove(player);
    }

    public void removePlayer(Player player) {
        playerToMatchMap.remove(player.getPlayerNickname());
    }

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

    public Registry getRegistry() {
        return registry;
    }

    public static void main(String[] args) {
        Server server = new Server();

        new Thread(new ServerSocketManager(server, Config.CONFIG_SERVER_SOCKET_PORT)).start();

        new Thread(new ServerRmiManager(server)).start();

        //server.startServerSocket ();
        //server.startServerRMI ();


        //nextMatch.startRecruiting();

        LOGGER.info(Messages.MESSAGE_LOGGING_INFO_SERVER_STARTED);
    }
}
