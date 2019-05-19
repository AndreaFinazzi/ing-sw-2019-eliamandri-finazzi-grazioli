package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.GenericViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.EventViewListenerRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ConnectionManagerRMI extends UnicastRemoteObject implements ClientRemoteRMI{

    private Registry registry;

    private EventViewListenerRemote clientHandlerRMI;

    private String playerName;

    public ConnectionManagerRMI(String playerName) throws RemoteException, NotBoundException {
        this.playerName = playerName;
        registry = LocateRegistry.getRegistry ();
        clientHandlerRMI = (EventViewListenerRemote)registry.lookup("ClientHandlerRMI");
        System.out.println("lookup fatta");
    }

    @Override
    public String getPlayerName() throws RemoteException{
        return playerName;
    }

    public void addClient() throws RemoteException{
        clientHandlerRMI.setClientRMI(this);
    }

    public void sendEvent(GenericViewEvent event) throws HandlerNotImplementedException, RemoteException {
        clientHandlerRMI.handleEvent(event);
    }
}
