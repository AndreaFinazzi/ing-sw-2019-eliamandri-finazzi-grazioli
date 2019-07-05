package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ClientRemoteRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Server remote object.
 * It exposes a method to receive incoming events, a method for ID requests, one for clients sign on and one for sign off.
 */
public interface ServerRemoteRMI extends Remote {

    /**
     * Receive method is called by remote clients which send view-generated events as a parameter.
     *
     * @param event the incoming view-generated event
     * @throws RemoteException the remote exception
     */
    void receive(AbstractViewEvent event) throws RemoteException;

    /**
     * Offers a new client id.
     *
     * @return the int
     * @throws RemoteException the remote exception
     */
    int askNewClientId() throws RemoteException;

    /**
     * Implementations of this method should register the clientRMI passed as parameter to the first available match.
     *
     * @param clientRMI the registering client rmi
     * @throws RemoteException the remote exception
     */
    void addClientRMI(ClientRemoteRMI clientRMI) throws RemoteException;

    /**
     * Remove client rmi.
     *
     * @param clientRMI the client rmi
     * @throws RemoteException the remote exception
     */
    void removeClientRMI(ClientRemoteRMI clientRMI) throws RemoteException;
}
