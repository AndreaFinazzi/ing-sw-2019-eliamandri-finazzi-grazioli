package it.polimi.se.eliafinazzigrazioli.adrenaline.server;


import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandlerSocket extends AbstractClientHandler {

    private Socket socket;
    private ObjectOutputStream sender;
    private ObjectInputStream receiver;

    private static final Logger LOGGER = Logger.getLogger(ClientHandlerSocket.class.getName());
    private List<Observer> observers = new ArrayList<>();


    public ClientHandlerSocket(Server server, Socket socket) {
        super(server);
        this.socket = socket;
        clientID = server.getCurrentClientID();
        try {
            sender = new ObjectOutputStream(socket.getOutputStream());
            receiver = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void send(AbstractModelEvent event) {
        try {
            sender.writeObject(event);
            sender.flush();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void run() {
        new Thread(() -> {
            AbstractViewEvent event;
            while (server.isUp()) {
                try {
                    event = (AbstractViewEvent) receiver.readObject();
                    received(event);
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                } catch (ClassNotFoundException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                } catch (ClassCastException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }
            }
        }).start();
    }

}