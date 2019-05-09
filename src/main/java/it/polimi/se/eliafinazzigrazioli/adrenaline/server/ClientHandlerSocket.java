package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;

public class ClientHandlerSocket extends AbstractClientHandler {

    private Socket socket;
    private ObjectOutputStream sender;
    private ObjectInputStream receiver;

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
    public void send(AbstractEvent event) {
        try {
            sender.writeObject(event);
            sender.flush();
        }catch (IOException e) {
            LOGGER.log (Level.SEVERE, e.toString (), e);
        }
    }

    @Override
    public AbstractEvent receive() {
        AbstractEvent event = null;

        try {
            event = (AbstractEvent) receiver.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log (Level.SEVERE, e.toString (), e);
        }
        return event;
    }
}