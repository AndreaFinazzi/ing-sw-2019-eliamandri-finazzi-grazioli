package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.GenericViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.PlayerConnectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.EventViewListenerRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ConnectionManagerRMI extends UnicastRemoteObject implements ClientRemoteRMI {

    private Registry registry;

    private EventViewListenerRemote clientHandlerRMI;

    private String playerName;

    private int clientID;

    public ConnectionManagerRMI(String playerName) throws RemoteException, NotBoundException {
        this.playerName = playerName;
        registry = LocateRegistry.getRegistry ();
        clientHandlerRMI = (EventViewListenerRemote)registry.lookup("ClientHandlerRMI");
        System.out.println("lookup fatta");
        clientID = clientHandlerRMI.getClientID();
    }

    public boolean loginPlayer(PlayerConnectedEvent playerConnectedEvent) {
        try {
                clientHandlerRMI.handleEvent(playerConnectedEvent);
                if(!clientHandlerRMI.response())
                    System.out.println(clientHandlerRMI.getResponseMessage());
                return clientHandlerRMI.response();
        }catch(RemoteException|HandlerNotImplementedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String getPlayerName() throws RemoteException{
        return playerName;
    }

    @Override
    public void print(String message) throws RemoteException {
        System.out.println(message);
    }

    public List<String> getAvatarAvaible() {
        //TODO
        return null;
    }

    public void addClient() throws RemoteException{
        clientHandlerRMI.addClientRMI(this);
    }

    public void sendEvent(GenericViewEvent event) throws HandlerNotImplementedException, RemoteException {
        clientHandlerRMI.handleEvent(event);
    }

    public List<String> getPlayersConnected() throws RemoteException {
        return clientHandlerRMI.getPlayersConnected();
    }

    @Override
    public int getClientID() {
        return clientID;
    }
}
