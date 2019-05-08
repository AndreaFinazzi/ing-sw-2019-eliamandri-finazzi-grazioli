package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.ClientHandlerRMI;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

public class ConnectionManagerRMI extends ConnectionManager implements Remote, Serializable {

    private static final Logger LOGGER = Logger.getLogger(ConnectionManagerRMI.class.getName ());
    private Registry registry;
    private ClientHandlerRMI clientHandlerRMI;
    private AbstractEvent sended;
    private AbstractEvent received;

    public ConnectionManagerRMI(String playerName) throws RemoteException, NotBoundException {
        super (playerName);
        registry = LocateRegistry.getRegistry (1099);
        clientHandlerRMI = (ClientHandlerRMI) registry.lookup ("ClientHandler RMI");
        UnicastRemoteObject.exportObject (this,0);
    }

    public void setup() {
        clientHandlerRMI.setClientRMI (this);
    }

    @Override
    public void send(AbstractViewEvent event) {
        clientHandlerRMI.setReceived (event);
    }

    @Override
    public AbstractEvent receive() {
       return clientHandlerRMI.getSended ();
    }

    public AbstractEvent getSended() {
        return sended;
    }

    public void setSended(AbstractEvent sended) {
        this.sended = sended;
    }

    public AbstractEvent getReceived() {
        return received;
    }

    public void setReceived(AbstractEvent received) {
        this.received = received;
    }
}
