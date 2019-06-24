package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ClientRemoteRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerRMIManager implements Runnable, ServerRemoteRMI {

    static final Logger LOGGER = Logger.getLogger(ClientHandlerRMI.class.getName());

    private Server server;
    private ArrayList<ClientHandlerRMI> clientHandlers = new ArrayList<>();

    public ServerRMIManager(Server server) throws RemoteException {
        this.server = server;

        //System.setProperty("java.rmi.server.hostname", "192.168.43.185");

        UnicastRemoteObject.exportObject(this, 1099);
    }


    public Server getServer() {
        return server;
    }

    @Override
    public int askNewClientId() throws RemoteException {
        return server.getCurrentClientID();
    }

    /**
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

        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        LOGGER.log(Level.INFO, "established connection RMI");
    }

    // necessary due to security purposes.
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

    @Override
    public void run() {
        try {
            server.getRegistry().bind("ServerRMIManager", this);
        } catch (RemoteException | AlreadyBoundException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private ClientHandlerRMI getClientHandlerRMI(int clientID) {
        for (ClientHandlerRMI clientHandler : clientHandlers) {
            if (clientHandler.getClientID() == clientID) return clientHandler;
        }

        return null;
    }
}
