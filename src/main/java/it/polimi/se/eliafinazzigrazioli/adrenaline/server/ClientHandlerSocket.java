package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandlerSocket implements ClientHandler {

    private Socket socket;
    private Server server;
    private ObjectOutputStream send;
    private ObjectInputStream received;
    private static final Logger LOGGER = Logger.getLogger(ClientHandlerSocket.class.getName ());
    private EventController eventController;


    public ClientHandlerSocket(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            send = new ObjectOutputStream (socket.getOutputStream ());
            received = new ObjectInputStream(socket.getInputStream());
        }catch (IOException e) {
            LOGGER.log (Level.SEVERE, e.toString (), e);
        }
    }

    @Override
    public void send(AbstractEvent event) {
        try {
            send.writeObject (event);
            send.flush ();
        }catch (IOException e) {
            LOGGER.log (Level.SEVERE, e.toString (), e);
        }
    }

    @Override
    public AbstractEvent received() {
        AbstractEvent event;
        Object object;
        try {
            object = received.readObject();
            if(object == null)
                throw new NullPointerException ();
            event = (AbstractEvent) object;
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log (Level.SEVERE, e.toString (), e);
            throw new NullPointerException ();
        }
        return event;
    }


    @Override
    public void run() {
        //read player name
        AbstractEvent event = received();
        eventController.update (event);
    }
}
