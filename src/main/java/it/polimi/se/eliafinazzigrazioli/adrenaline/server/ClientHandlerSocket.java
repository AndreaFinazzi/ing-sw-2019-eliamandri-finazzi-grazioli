package it.polimi.se.eliafinazzigrazioli.adrenaline.server;


import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observable;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandlerSocket extends AbstractClientHandler implements Observable {

    private Socket socket;
    private ObjectOutputStream sender;
    private ObjectInputStream receiver;
    private Server server;

    private String playerName;
    private int clientId;

    private static final Logger LOGGER = Logger.getLogger(ClientHandlerSocket.class.getName());
    private List<Observer> observers = new ArrayList<>();


    public ClientHandlerSocket(Server server, Socket socket) {
        super(server);
        this.socket = socket;
        clientId = server.getCurrentClientID();
        try {
            sender = new ObjectOutputStream(socket.getOutputStream());
            receiver = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void sendToAll(AbstractModelEvent event) {
        send(event);
    }

    @Override
    public void sendTo(int clientID, AbstractModelEvent event) {
        if (clientID == this.clientId) {
            send(event);
        }
    }

    private void send(AbstractModelEvent event) {
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
            while (true) {
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

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}