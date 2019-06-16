package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSocketManager implements Runnable {

    private final int PORT;
    private Server server;
    private static final Logger LOGGER = Logger.getLogger(ServerSocketManager.class.getName());
    private ServerSocket serverSocket;

    public ServerSocketManager(Server server, int PORT) {
        this.PORT = PORT;
        this.server = server;
    }

    public void startServerSocket() {

        ExecutorService clientHandlersExecutor = Executors.newCachedThreadPool();
        try {
            serverSocket = new ServerSocket(PORT);
            LOGGER.log(Level.INFO, "server socket created");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            return;
        }

        try {
            while (server.isUp()) {
                LOGGER.log(Level.INFO, "Waiting a connection...");
                Socket clientSocket = serverSocket.accept();

                LOGGER.log(Level.INFO, "New client connected.");
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
