package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

public class ServerRMIManager implements Runnable {
    private Server server;
    private ServerRemoteRMI clientHandlerRMI;

    public ServerRMIManager(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            clientHandlerRMI = new ClientHandlerRMI(server);
            server.getRegistry().bind("//localhost/ClientHandlerRMI", clientHandlerRMI);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
