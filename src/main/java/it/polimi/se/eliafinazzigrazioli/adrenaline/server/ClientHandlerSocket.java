package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller.GenericEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandlerSocket implements ClientHandler {

    private Socket socket;
    private Server server;
    private ObjectOutputStream sender;
    private ObjectInputStream receiver;
    private static final Logger LOGGER = Logger.getLogger(ClientHandlerSocket.class.getName ());
    private EventController eventController;
    private AbstractEvent nextReceivedEvent;


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

    //TODO: this should do something useful
    //TODO: add message constants to dedicated class
    @Override
    public void run() {
        LOGGER.info("Client initialized: waiting for player's nickname");
        send(new GenericEvent("Waiting for nickname"));
        nextReceivedEvent = receive();
        server.addPlayer(nextReceivedEvent.getMessage());
    }
}