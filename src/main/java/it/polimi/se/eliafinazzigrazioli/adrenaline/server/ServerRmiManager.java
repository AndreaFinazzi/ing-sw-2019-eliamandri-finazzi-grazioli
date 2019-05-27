package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

public class ServerRmiManager implements Runnable{
    private Server server;
    private ClientHandlerRMI clientHandlerRMI;

    public ServerRmiManager(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            clientHandlerRMI = new ClientHandlerRMI (server);
            server.getRegistry().bind ("ClientHandlerRMI", clientHandlerRMI);
        }catch (RemoteException|AlreadyBoundException e) {
            e.printStackTrace ();
        }
    }
}
