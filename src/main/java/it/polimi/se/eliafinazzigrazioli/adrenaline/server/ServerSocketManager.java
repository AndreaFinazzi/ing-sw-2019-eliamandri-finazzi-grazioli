package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSocketManager {

    private final int PORT;
    private static final Logger LOGGER = Logger.getLogger(ServerSocketManager.class.getName ());


    public ServerSocketManager(int PORT) {
        this.PORT = PORT;
    }

    public void startServerSocket() {
        ExecutorService executor = Executors.newCachedThreadPool ();
        ServerSocket serverSocket;
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
                executor.submit (new ClientHandlerSocket (socket));
            }catch (IOException e) {
                LOGGER.log (Level.SEVERE, e.toString (), e);
                break;
            }
        }
    }
}
