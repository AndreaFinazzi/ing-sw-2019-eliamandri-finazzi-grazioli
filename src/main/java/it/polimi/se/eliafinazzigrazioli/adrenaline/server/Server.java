package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

// Server main class

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.MatchController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Config;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Messages;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private ServerSocketManager serverSocketManager;
    private HashMap<String, MatchController> playerToMatchMap;
    private MatchController nextMatch;
    private Registry registry;
    private Timer timer;


    private Server() {
        timer = new Timer();
        playerToMatchMap = new HashMap<>();
        nextMatch = new MatchController();
        try {
            registry = LocateRegistry.createRegistry (1099);
        }catch (RemoteException e) {
            LOGGER.log (Level.SEVERE, e.toString (), e);
        }
    }

    public void startServerSocket() {
        serverSocketManager = new ServerSocketManager(this, Config.CONFIG_SERVER_SOCKET_PORT);
        serverSocketManager.startServerSocket();
    }

    public void startServerRMI() {

    }

    public void addPlayer(String player) {
        stopTimer();

        nextMatch.addPlayer(player);
        playerToMatchMap.put(player, nextMatch);

        if (nextMatch.isFull()) {
            nextMatch.initMatch();
            //TODO: move to Messages
            LOGGER.info("Game started");
            nextMatch = new MatchController();
        } else if (nextMatch.isReady()) {
            startTimer();
        }
    }

    public void addPlayer(String player, MatchController matchController) {
        nextMatch.addPlayer(player);
        playerToMatchMap.put(player, matchController);
    }

    public void removePlayer(String player) {
        playerToMatchMap.remove(player);
    }

    public void removePlayer(Player player) {
        playerToMatchMap.remove(player.getPlayerNickname());
    }

    public void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextMatch.initMatch();
                //TODO: move to Messages
                LOGGER.info("Game started");
                nextMatch = new MatchController();
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
        new Thread (new ServerSocketManager (server, Config.CONFIG_SERVER_SOCKET_PORT)).start ();

        new Thread (new ServerRmiManager (server)).start ();


        //server.startServerSocket ();
        //server.startServerRMI ();


        //nextMatch.startRecruiting();

        LOGGER.info(Messages.MESSAGE_LOGGING_INFO_SERVER_STARTED);
    }
}
