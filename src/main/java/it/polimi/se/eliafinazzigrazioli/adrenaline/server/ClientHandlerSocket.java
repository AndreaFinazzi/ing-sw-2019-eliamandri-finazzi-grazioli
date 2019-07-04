package it.polimi.se.eliafinazzigrazioli.adrenaline.server;


import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandlerSocket extends AbstractClientHandler {

    private Socket socket;
    private ObjectOutputStream sender;
    private ObjectInputStream receiver;

    private static final Logger LOGGER = Logger.getLogger(ClientHandlerSocket.class.getName());
    private List<Observer> observers = new ArrayList<>();

    private ExecutorService serviceExecutor = Executors.newFixedThreadPool(2);

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
            unregister();
        }
    }

    @Override
    public void run() {
        // incoming messages listener
        serviceExecutor.execute(() -> {
            AbstractViewEvent event;
            try {
                while (server.isUp()) {
                    event = (AbstractViewEvent) receiver.readObject();
                    received(event);
                }
            } catch (IOException e) {
                LOGGER.info(Messages.MESSAGE_LOGGING_INFO_CLIENT_DISCONNECTED + clientID);
                unregister();
            } catch (ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
                unregister();
            }
        });

        // connection check thread
        serviceExecutor.execute(() -> {
            InetAddress inetSocketAddress = socket.getInetAddress();
            try {
                while (inetSocketAddress.isReachable(Server.PING_TIMEOUT)) {
                    Thread.sleep(Server.PING_TIMEOUT);
                }
                LOGGER.info(Messages.MESSAGE_LOGGING_INFO_CLIENT_DISCONNECTED + clientID);
                unregister();
            } catch (IOException | InterruptedException e) {
                LOGGER.info(Messages.MESSAGE_LOGGING_INFO_CLIENT_DISCONNECTED + clientID);
                unregister();
            }
        });
    }

}