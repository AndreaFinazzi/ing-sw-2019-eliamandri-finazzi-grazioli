package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ClientRemoteRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server rmi manager is the class exposing the remote methods of ServerRemoteRMI.
 * It also keeps track of the instantiated ClientHandlerRMI objects, that is of the connected clients.
 * <p>
 * When an incoming object is received through the dedicated method, this is dispatched to the correspondent instance of ClientHandlerRMI.
 */
public class ServerRMIManager implements Runnable, ServerRemoteRMI {

    /**
     * Static reference to LOGGER.
     */
    static final Logger LOGGER = Logger.getLogger(ClientHandlerRMI.class.getName());

    /**
     * Reference to Server instance.
     */
    private Server server;

    /**
     * RMI Registry reference.
     */
    private static Registry registry;

    /**
     * List of connected client's handlers
     */
    private ArrayList<ClientHandlerRMI> clientHandlers = new ArrayList<>();

    /**
     * Threads pool for reachability check tasks.
     */
    private ExecutorService pingersExecutor = Executors.newCachedThreadPool();

    static {
        try {
            System.setProperty("java.rmi.server.hostname", Config.CONFIG_CLIENT_SERVER_IP);

            registry = LocateRegistry.createRegistry(Config.CONFIG_SERVER_RMI_PORT);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            registry = null;
        }
    }

    /**
     * Instantiates a new Server rmi manager and exports this instance on server rmi port.
     *
     * @param server the server
     * @throws RemoteException the remote exception
     */
    public ServerRMIManager(Server server) throws RemoteException {
        this.server = server;

        UnicastRemoteObject.exportObject(this, Config.CONFIG_SERVER_RMI_PORT);
    }


    /**
     * Server instance getter.     *
     * @return the server
     */
    public Server getServer() {
        return server;
    }

    @Override
    public int askNewClientId() throws RemoteException {
        return server.getCurrentClientID();
    }

    /**
     * Sign on method implementation.
     * It calls Server signIn() method for registration and starts the ping task.
     * @param clientRMI
     */
    @Override
    public void addClientRMI(ClientRemoteRMI clientRMI) throws RemoteException {
        int clientID = clientRMI.getClientID();
        try {
            if (clientID == 0) {
                clientID = server.getCurrentClientID();
                clientRMI.setClientID(clientID);
            }

            ClientHandlerRMI newClientHandler = new ClientHandlerRMI(this, clientRMI, clientID);
            clientHandlers.add(newClientHandler);

            server.signIn(newClientHandler);

            startPing(newClientHandler);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        LOGGER.log(Level.INFO, "established connection RMI");

    }

    /**
     * unregisters a leaving client.
     * @param clientRMI the client rmi
     * @throws RemoteException
     */
    @Override
    public void removeClientRMI(ClientRemoteRMI clientRMI) throws RemoteException {
        int clientID = clientRMI.getClientID();

        ClientHandlerRMI clientHandlerRMI = getClientHandlerRMI(clientID);
        clientHandlers.remove(clientHandlerRMI);

        if (clientHandlerRMI != null) {
            clientHandlerRMI.unregister();
        }
    }

    @Override
    public void receive(AbstractViewEvent event) throws RemoteException {
        ClientHandlerRMI clientHandlerRMI = getClientHandlerRMI(event.getClientID());
        if (clientHandlerRMI != null) clientHandlerRMI.received(event);
    }


    /**
     * Get ClientHandlerRMI instance by clientID.
     *
     * @param clientID
     * @return
     */
    private ClientHandlerRMI getClientHandlerRMI(int clientID) {
        for (ClientHandlerRMI clientHandler : clientHandlers) {
            if (clientHandler.getClientID() == clientID) return clientHandler;
        }

        return null;
    }

    private void startPing(ClientHandlerRMI newClientHandler) {
        pingersExecutor.execute(() -> {
            try {
                while (newClientHandler.isReachable()) {
                    Thread.sleep(Config.CONFIG_SERVER_PING_TIMEOUT);
                }
                LOGGER.info(Messages.MESSAGE_LOGGING_INFO_CLIENT_DISCONNECTED + newClientHandler.getClientID());
                newClientHandler.unregister();
            } catch (IOException | InterruptedException e) {
                LOGGER.info(Messages.MESSAGE_LOGGING_INFO_CLIENT_DISCONNECTED + newClientHandler.getClientID());
                newClientHandler.unregister();
            }
        });
    }

    @Override
    public void run() {
        if (registry != null) {
            try {
                registry.bind(Config.CONFIG_SERVER_RMI_NAME, this);
            } catch (RemoteException | AlreadyBoundException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        } else {
            LOGGER.info(Messages.MESSAGE_LOGGING_INFO_SERVER_RMI_INTERRUPTED);
            Thread.currentThread().interrupt();
        }
    }
}
