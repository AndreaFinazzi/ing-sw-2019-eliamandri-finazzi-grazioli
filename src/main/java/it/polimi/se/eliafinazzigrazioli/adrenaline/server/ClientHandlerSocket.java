package it.polimi.se.eliafinazzigrazioli.adrenaline.server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandlerSocket  implements Runnable{

    private Socket socket;
    private ObjectOutputStream sender;
    private ObjectInputStream receiver;
    private Server server;
    private static final Logger LOGGER = Logger.getLogger (ClientHandlerSocket.class.getName ());


    public ClientHandlerSocket(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            sender = new ObjectOutputStream(socket.getOutputStream());
            receiver = new ObjectInputStream(socket.getInputStream());
        }catch (IOException e) {
            LOGGER.log (Level.SEVERE, e.toString (), e);
        }
    }

    @Override
    public void run() {

    }
}