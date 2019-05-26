package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRemoteRMI extends Remote {

    public String getPlayerName() throws RemoteException;

    public void print() throws RemoteException;


}
