package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerRmiManager implements Runnable {

    private Server server;
    private ClientHandlerRMI clientHandlerRMI;
    private static final Logger LOGGER = Logger.getLogger(ServerRmiManager.class.getName ());


    public ServerRmiManager(Server server){
        this.server = server;
    }

    public void startServerRMI() throws RemoteException, AlreadyBoundException {
        clientHandlerRMI = new ClientHandlerRMI (server);
        server.getRegistry ().bind ("ClientHandler RMI", clientHandlerRMI);
    }

    @Override
    public void run() {
        try {
            System.out.println ("Server RMI is started...");
            startServerRMI ();
        }catch (RemoteException e) {
            LOGGER.log (Level.SEVERE, e.toString (), e);
        }catch (AlreadyBoundException e) {
            LOGGER.log (Level.SEVERE, e.toString (), e);
        }
    }
}
