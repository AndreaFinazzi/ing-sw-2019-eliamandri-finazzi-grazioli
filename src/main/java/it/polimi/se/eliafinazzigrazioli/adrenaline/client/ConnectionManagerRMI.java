package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.ServerRemoteRMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;

public class ConnectionManagerRMI extends AbstractConnectionManager implements ClientRemoteRMI {

    private Registry registry;

    private ServerRemoteRMI serverRemoteRMI;

    public ConnectionManagerRMI(Client client) throws RemoteException, NotBoundException {
        this(client, Config.CONFIG_SERVER_RMI_PORT);
    }

    public ConnectionManagerRMI(Client client, int port) throws RemoteException, NotBoundException {
        super(client);
        this.port = port;
    }

    // AbstractConnectionManager
    @Override
    public void send(AbstractViewEvent event) {
        try {
            LOGGER.info("Client ConnectionManagerRMI sending event: " + event);
            serverRemoteRMI.receive(event);
            connection_attempts = 0;

        } catch (RemoteException e) {
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

    @Override
    public void init() {
        try {
            registry = LocateRegistry.getRegistry(Config.CONFIG_CLIENT_SERVER_IP, Config.CONFIG_SERVER_RMI_PORT);
            serverRemoteRMI = (ServerRemoteRMI) registry.lookup(Config.CONFIG_SERVER_RMI_NAME);

            LOGGER.info("ClientHandlerRMI: Lookup successfully executed.");

            UnicastRemoteObject.exportObject(this, port);

            performRegistration();
            LOGGER.info("Client ConnectionManagerRMI ready.");

        } catch (RemoteException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            try {
                if (connection_attempts <= Config.CONFIG_CLIENT_CONNECTION_MAX_ATTEMPTS) {
                    connection_attempts++;
                    LOGGER.info("Trying again in a few seconds");
                    Thread.sleep(Config.CONFIG_CLIENT_CONNECTION_ATTEMPT_DELAY);
                    init();
                }
            } catch (InterruptedException ex) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        } catch (NotBoundException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    @Override
    public void performRegistration() {
        LOGGER.info("Trying to sign up.");

        try {
            client.setClientID(serverRemoteRMI.askNewClientId());
            serverRemoteRMI.addClientRMI(this);
            LOGGER.info("Registration successfully executed. ClientId:\t" + client.getClientID());
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void disconnect() {
        LOGGER.info("Disconnecting. Bye bye!");
        if (serverRemoteRMI != null) {
            try {
                serverRemoteRMI.removeClientRMI(this);
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
                Thread.currentThread().interrupt();
            }
        }
        Thread.currentThread().interrupt();
    }

    @Override
    public String getPlayerName() {
        return client.getPlayerName();
    }

    @Override
    public int getClientID() {
        return client.getClientID();
    }

    @Override
    public void setClientID(int clientID) throws RemoteException {
        client.setClientID(clientID);
    }

    @Override
    public void receive(AbstractModelEvent event) throws RemoteException {
        LOGGER.info("Client ConnectionManagerRMI receiving: " + event);
        received(event);
    }

    @Override
    public boolean isReachable() throws RemoteException {
        return true;
    }
}