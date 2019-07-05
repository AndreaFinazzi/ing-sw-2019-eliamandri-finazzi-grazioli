package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Server socket manager.
 */
public class ServerSocketManager implements Runnable {

    private final int port;
    private Server server;
    private static final Logger LOGGER = Logger.getLogger(ServerSocketManager.class.getName());
    private ServerSocket serverSocket;

    /**
     * Instantiates a new Server socket manager.
     *
     * @param server the server
     * @param port the port
     */
    public ServerSocketManager(Server server, int port) {
        this.port = port;
        this.server = server;
    }

    /**
     * instantiates ServerSocket instance and accept new client connections until server is online.
     * As a client connects, a new ClientHandlerSocket is instantiated and signed on Server for a free slot in the next starting match.
     */
    public void startServerSocket() {

        ExecutorService clientHandlersExecutor = Executors.newCachedThreadPool();
        try {
            serverSocket = new ServerSocket(port);
            LOGGER.log(Level.INFO, Messages.MESSAGE_LOGGING_INFO_SOCKET_STARTING);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            return;
        }

        try {
            while (server.isOnline()) {
                LOGGER.log(Level.INFO, Messages.MESSAGE_LOGGING_INFO_SOCKET_ACCEPT);
                Socket clientSocket = serverSocket.accept();

                LOGGER.log(Level.INFO, Messages.MESSAGE_LOGGING_INFO_SOCKET_NEW_CLIENT);
                ClientHandlerSocket newClientHandler = new ClientHandlerSocket(server, clientSocket);
                clientHandlersExecutor.submit(newClientHandler);

                server.signIn(newClientHandler);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void run() {
        startServerSocket();
    }
}
