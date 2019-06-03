package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ClientRemoteRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ClientDisconnectionEvent;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


// RMI SERVER
public class ClientHandlerRMI extends AbstractClientHandler implements ServerRemoteRMI {

    static final Logger LOGGER = Logger.getLogger(ClientHandlerRMI.class.getName());

    private Map<Integer, ClientRemoteRMI> clientsRMI;
    private boolean set;


    public ClientHandlerRMI(Server server) throws RemoteException {
        //TODO
        //listener = new RemoteView("tony");
        super(server);
        clientsRMI = new HashMap<>();

        UnicastRemoteObject.exportObject(this, 1099);
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
            clientsRMI.put(clientID, clientRMI);

            server.addClient(clientID, this);

        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        LOGGER.log(Level.INFO, "established connection RMI");
    }

    @Override
    public void removeClientRMI(ClientRemoteRMI clientRMI) throws RemoteException {
        int clientID = clientRMI.getClientID();
        clientsRMI.remove(clientID);
        server.removeClient(clientID);

        eventsQueue.offer(new ClientDisconnectionEvent(clientID));
    }

    public boolean isSet() {
        return set;
    }

    public void setSet(boolean set) {
        this.set = set;
    }

    @Override
    public void sendToAll(AbstractModelEvent event) {
        if (event.isPrivateEvent()) {

            if (event.getClientID() != 0) {
                sendTo(event.getClientID(), event);
            } else if ((event.getPlayer() != null) && (event.getPlayer().equals(""))) {
                throw new NullPointerException("Private event has no player ID!");
            } else
                sendTo(event.getPlayer(), event);
        } else {
            for (ClientRemoteRMI client : clientsRMI.values()) {
                send(client, event);
            }
        }
    }

    @Override
    public void sendTo(int clientID, AbstractModelEvent event) {
        ClientRemoteRMI targetClient = clientsRMI.get(clientID);

        if (targetClient != null)
            send(targetClient, event);
    }

    @Override
    public void sendTo(String player, AbstractModelEvent event) {
        // TODO implement
    }

    private void send(ClientRemoteRMI client, AbstractModelEvent event) {
        try {
            client.receive(event);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void receive(AbstractViewEvent event) throws RemoteException {
        received(event);
    }
}