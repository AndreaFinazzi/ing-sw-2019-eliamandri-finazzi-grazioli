package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRemoteRMI extends Remote {

    String getPlayerName() throws RemoteException;

    int getClientID() throws RemoteException;

    void receive(AbstractModelEvent event) throws RemoteException;
}
