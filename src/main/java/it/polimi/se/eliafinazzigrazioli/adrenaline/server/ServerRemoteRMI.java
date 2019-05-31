package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ClientRemoteRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerRemoteRMI extends Remote {
    void receive(AbstractViewEvent event) throws RemoteException;

    int getClientID() throws RemoteException;

    void addClientRMI(ClientRemoteRMI clientRMI) throws RemoteException;
}
