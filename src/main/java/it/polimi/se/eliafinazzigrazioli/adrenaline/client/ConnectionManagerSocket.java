package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ClientDisconnectionEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManagerSocket extends AbstractConnectionManager {

    private static final Logger LOGGER = Logger.getLogger(ConnectionManagerSocket.class.getName());

    //TODO move in config file
    private static final String IP_SERVER = "localhost";
    private static final int PORT_SEVER = 9999;

    private Socket clientSocket;
    private ObjectOutputStream sender;
    private ObjectInputStream receiver;

    public ConnectionManagerSocket(Client client) {
        super(client);

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
    public void init() {
        try {
            clientSocket = new Socket(IP_SERVER, PORT_SEVER);
            sender = new ObjectOutputStream(clientSocket.getOutputStream());
            receiver = new ObjectInputStream(clientSocket.getInputStream());

            startListener();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            try {
                LOGGER.info("Trying again in a few seconds");
                Thread.sleep(CONNECTION_ATTEMPT_DELAY);
                init();
            } catch (InterruptedException ex) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }


    }

    private void startListener() {
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

    @Override
    public void disconnect() {
        send(new ClientDisconnectionEvent(client.getClientID(), client.getPlayerName()));
    }

    public void closeConnection() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}
