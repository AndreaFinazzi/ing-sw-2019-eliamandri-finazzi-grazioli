package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManagerSocket extends AbstractConnectionManager {

    private static final String IP_SERVER = "localhost";
    private static final int PORT_SEVER = 9999;
    private static final Logger LOGGER = Logger.getLogger(ConnectionManagerSocket.class.getName());
    private Socket clientSocket;
    private ObjectOutputStream sender;
    private ObjectInputStream receiver;

    public ConnectionManagerSocket(Client client) {
        super(client);
        try {
            clientSocket = new Socket(IP_SERVER, PORT_SEVER);
            sender = new ObjectOutputStream(clientSocket.getOutputStream());
            receiver = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void send(AbstractViewEvent event) {
        try {
            sender.writeObject(event);
            sender.flush();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void listen() {
        new Thread(() -> {
            AbstractModelEvent event;
            while (true) {
                try {
                    event = (AbstractModelEvent) receiver.readObject();
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
    public void performRegistration() {

    }

    public void closeConnection() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}
