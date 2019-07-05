package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The interface Client remote rmi.
 */
public interface ClientRemoteRMI extends Remote {

    /**
     * Gets player name.
     *
     * @return the player name
     * @throws RemoteException the remote exception
     */
    String getPlayerName() throws RemoteException;

    /**
     * Gets client id.
     *
     * @return the client id
     * @throws RemoteException the remote exception
     */
    int getClientID() throws RemoteException;

    /**
     * Sets client id.
     *
     * @param clientID the client id
     * @throws RemoteException the remote exception
     */
    void setClientID(int clientID) throws RemoteException;

    /**
     * Receive.
     *
     * @param event the event
     * @throws RemoteException the remote exception
     */
    void receive(AbstractModelEvent event) throws RemoteException;

    /**
     * Is reachable boolean.
     *
     * @return the boolean
     * @throws RemoteException the remote exception
     */
    boolean isReachable() throws RemoteException;
}
