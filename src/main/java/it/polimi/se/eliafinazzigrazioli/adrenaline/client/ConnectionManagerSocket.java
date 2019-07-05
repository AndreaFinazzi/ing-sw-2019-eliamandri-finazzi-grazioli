package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManagerSocket extends AbstractConnectionManager {

    private static final Logger LOGGER = Logger.getLogger(ConnectionManagerSocket.class.getName());

    private Socket clientSocket;
    private ObjectOutputStream sender;
    private ObjectInputStream receiver;

    public ConnectionManagerSocket(Client client) {
        this(client, Config.CONFIG_SERVER_SOCKET_PORT);
    }

    public ConnectionManagerSocket(Client client, int port) {
        super(client);
        this.port = port;
    }

    @Override
    public void send(AbstractViewEvent event) {
        if (!clientSocket.isClosed()) {
            try {
                sender.writeObject(event);
                sender.flush();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, e.toString(), e);
                connection_attempts++;
                try {
                    LOGGER.info("Trying again in a few seconds");
                    Thread.sleep(Config.CONFIG_CLIENT_CONNECTION_ATTEMPT_DELAY);
                    if (connection_attempts <= Config.CONFIG_CLIENT_CONNECTION_MAX_ATTEMPTS) send(event);
                } catch (InterruptedException ex) {
                    LOGGER.log(Level.SEVERE, e.getMessage(), e);
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override
    public void init() {
        try {
            clientSocket = new Socket(Config.CONFIG_CLIENT_SERVER_IP, Config.CONFIG_SERVER_SOCKET_PORT);
            sender = new ObjectOutputStream(clientSocket.getOutputStream());
            receiver = new ObjectInputStream(clientSocket.getInputStream());
            startListener();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            try {
                LOGGER.info("Trying again in a few seconds");
                Thread.sleep(Config.CONFIG_CLIENT_CONNECTION_ATTEMPT_DELAY);
                init();
            } catch (InterruptedException ex) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }


    }

    private void startListener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AbstractModelEvent event;
                try {
                    while (!clientSocket.isClosed()) {
                        event = (AbstractModelEvent) receiver.readObject();
                        received(event);
                        connection_attempts = 0;
                    }
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                    if (connection_attempts <= Config.CONFIG_CLIENT_CONNECTION_MAX_ATTEMPTS) {
                        connection_attempts++;
                        run();
                    }
                } catch (ClassNotFoundException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                } finally {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    @Override
    public void performRegistration() {

    }

    @Override
    public void disconnect() {
        try {
            LOGGER.info("Disconnecting. Bye bye!");
            if (!clientSocket.isClosed()) {
                receiver.close();
                sender.close();
                clientSocket.close();
            }
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}
