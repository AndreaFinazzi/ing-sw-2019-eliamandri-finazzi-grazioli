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
    private static final Logger LOGGER = Logger.getLogger(ServerSocketManager.class.getName ());
    ServerSocket serverSocket;

    public ServerSocketManager(Server server, int PORT) {
        this.PORT = PORT;
        this.server = server;
    }

    public void startServerSocket() {
        ExecutorService executor = Executors.newCachedThreadPool ();
        try {
            serverSocket = new ServerSocket (PORT);
        }catch (IOException e) {
            LOGGER.log (Level.SEVERE, e.toString (), e);
            return;
        }
        LOGGER.log (Level.INFO, "ServerSocket is ready");

        while (true) {
            try {
                LOGGER.log (Level.INFO, "Waiting a connection");
                Socket socket = serverSocket.accept ();
                executor.submit(new ClientHandlerSocket(server, socket));
            } catch (IOException e) {
                LOGGER.log (Level.SEVERE, e.toString (), e);
                break;
            }
        }
    }

    @Override
    public void run() {
        startServerSocket();
    }
}
