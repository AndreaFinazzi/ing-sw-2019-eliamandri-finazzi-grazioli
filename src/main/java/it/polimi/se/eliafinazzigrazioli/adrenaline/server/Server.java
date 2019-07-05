package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

// Server main class

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Singleton class which initializes server-side application.
 */
public class Server implements Runnable {


    /**
     * Reference to static object.
     */
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    /**
     * Network managers threads pool.
     */
    private ExecutorService networkExecutor = Executors.newFixedThreadPool(2);


    /**
     * MatchBuilder is the effective delegated to handle match generation, players registry and disconnection events.
     */
    private MatchBuilder matchBuilder;

    /**
     * Keeps track of online state of Server.
     */
    private boolean online;

    /**
     * Client identification number, assigned to each client at connection time.
     */
    private int currentClientID = 0;

    /**
     * Tracks the effective singleness of the instance.
     */
    private static boolean instantiated = false;

    /**
     * Instantiates a new Server.
     */
    public Server() {
        if (!instantiated) {

            instantiated = true;
            matchBuilder = new MatchBuilder();
            online = true;

        }
    }

    /**
     * Launches server socket manager in the networkExecutor pool.
     */
    public void startServerSocket() {
        LOGGER.info(Messages.MESSAGE_LOGGING_INFO_SOCKET_STARTING);
        networkExecutor.execute(new ServerSocketManager(this, Config.CONFIG_SERVER_SOCKET_PORT));
    }

    /**
     * Launches server rmi manager in the networkExecutor pool.
     */
    public void startServerRMI() {
        LOGGER.info(Messages.MESSAGE_LOGGING_INFO_RMI_STARTING);
        try {
            networkExecutor.execute(new ServerRMIManager(this));
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    /**
     * Synchronized method which signs new clients on the first available match on matchBuilder.
     *
     * @param clientHandler the last connected client handler
     */
    public synchronized void signIn(AbstractClientHandler clientHandler) {
        matchBuilder.signNewClient(clientHandler);
    }


    /**
     * Gets next client id.
     *
     * @return the current client id
     */
    public synchronized int getCurrentClientID() {
        currentClientID++;
        return currentClientID;
    }

    private void close() {
        online = false;
        Thread.currentThread().interrupt();
    }

    /**
     * online status getter.
     *
     * @return boolean boolean
     */
    public boolean isOnline() {
        return online;
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