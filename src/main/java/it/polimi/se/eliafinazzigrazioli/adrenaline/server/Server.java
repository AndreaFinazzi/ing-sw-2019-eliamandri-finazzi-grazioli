package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

// Server main class

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


//TODO CHECK CONCURRENCY ISSUES OF THE WHOLE LOGIC!!!

public class Server implements Runnable {

    protected static final int PING_TIMEOUT = 1000;

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private ServerSocketManager serverSocketManager;

    // Network threads pool
    private ExecutorService networkExecutor = Executors.newFixedThreadPool(2);

    // Player.username to ClientHandler
    // TODO change it to support RMI. Player.username to Client.ID
    private MatchBuilder matchBuilder;
    private Registry registry;
    private boolean online;
    private int currentClientID = 0;

    private boolean initialied = false;

    public Server() {
        if (!initialied) {
            initialied = true;
            LOGGER.info("Creating Server"); //TODO move to messages

            matchBuilder = new MatchBuilder();

            try {
                //System.setProperty("java.rmi.server.hostname", "192.168.43.185");

                registry = LocateRegistry.createRegistry(1099);
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }

            online = true;
        }
    }

    public void startServerSocket() {
        LOGGER.info("Starting socket manager");
        networkExecutor.execute(new ServerSocketManager(this, Config.CONFIG_SERVER_SOCKET_PORT));
    }

    public void startServerRMI() {
        LOGGER.info("Starting RMI manager");
        try {
            networkExecutor.execute(new ServerRMIManager(this));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public synchronized void signIn(AbstractClientHandler clientHandler) {
        matchBuilder.signNewClient(clientHandler);
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
        online = false;
        Thread.currentThread().interrupt();
    }

    public boolean isUp() {
        return online;
    }

    public void setUp(boolean online) {
        this.online = online;
    }

    @Override
    public void run() {
        LOGGER.info(Messages.MESSAGE_LOGGING_INFO_SERVER_STARTED);

        try {
            startServerSocket();
            startServerRMI();
        } finally {
            //TODO do something to properly control Server shutdown
        }

        Thread currentThread = Thread.currentThread();
        currentThread.setName("MainServerThread");
    }
}