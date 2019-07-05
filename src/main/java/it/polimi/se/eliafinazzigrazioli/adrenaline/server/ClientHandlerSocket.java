package it.polimi.se.eliafinazzigrazioli.adrenaline.server;


import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles a single client connected through a socket interface.
 * It starts two distinct thread for incoming events listening and client reachability checking.
 * <p>
 * Both of these threads unregisters the client from the respective match in case of errors or disconnection.
 */
public class ClientHandlerSocket extends AbstractClientHandler {

    /**
     * Client socket reference
     */
    private Socket socket;

    /**
     * Output stream for socket write.
     */
    private ObjectOutputStream sender;

    /**
     * Input stream for socket read.
     */
    private ObjectInputStream receiver;

    /**
     * Static reference to logger.
     */
    private static final Logger LOGGER = Logger.getLogger(ClientHandlerSocket.class.getName());

    /**
     * Thread pool for listener and pinger thread
     */
    private ExecutorService serviceExecutor = Executors.newFixedThreadPool(2);

    /**
     * Instantiates a new Client handler socket. Gets a new client ID from Server.
     *
     * @param server server instance
     * @param socket client socket instance
     */
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


    /**
     * Send method implementation. Writes the specified event on the OutputStream.
     * Unregisters the corresponding client from match in case of error.
     *
     * @param event the event
     */
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
                while (server.isOnline()) {
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
                while (inetSocketAddress.isReachable(Config.CONFIG_SERVER_PING_TIMEOUT)) {
                    Thread.sleep(Config.CONFIG_SERVER_PING_TIMEOUT);
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