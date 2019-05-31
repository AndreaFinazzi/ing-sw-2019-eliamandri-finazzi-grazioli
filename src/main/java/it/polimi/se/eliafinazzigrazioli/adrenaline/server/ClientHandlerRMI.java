package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ClientRemoteRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


// RMI SERVER
public class ClientHandlerRMI extends AbstractClientHandler implements ServerRemoteRMI {

    private boolean setted;
    private Map<Integer, ClientRemoteRMI> clientsRMI;
    private EventListenerInterface listener;
    private transient Server server;
    private String playerName;
    static final Logger LOGGER = Logger.getLogger(ClientHandlerRMI.class.getName());
    private boolean response;
    private String responseMessage;


    public ClientHandlerRMI(Server server) throws RemoteException {
        //TODO
        //listener = new RemoteView("tony");
        this.server = server;
        clientsRMI = new HashMap<>();

        UnicastRemoteObject.exportObject(this, 1099);
    }

    @Override
    public int getClientID() throws RemoteException {
        return server.getCurrentClientID();
    }

    /**
     * @param clientRMI
     */
    @Override
    public void addClientRMI(ClientRemoteRMI clientRMI) throws RemoteException {
        try {
            if (clientRMI.getClientID() == 0) {
                Integer clientID = server.getCurrentClientID();
                clientsRMI.put(clientID, clientRMI);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        LOGGER.log(Level.INFO, "established connection RMI");
    }


    public boolean isSetted() {
        return setted;
    }

    public void setSetted(boolean setted) {
        this.setted = setted;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    @Override
    void send(AbstractEvent event) {
        for (ClientRemoteRMI client : clientsRMI.values()) {
            synchronized (client) {

            }
        }
    }

    @Override
    public void receive(AbstractViewEvent event) throws RemoteException {
        received(event);
    }
}
