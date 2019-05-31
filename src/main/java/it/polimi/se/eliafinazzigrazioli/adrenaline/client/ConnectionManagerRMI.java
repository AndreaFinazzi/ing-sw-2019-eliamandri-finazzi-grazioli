package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.ServerRemoteRMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Level;

public class ConnectionManagerRMI extends AbstractConnectionManager implements ClientRemoteRMI {

    private Registry registry;

    private ServerRemoteRMI serverRemoteRMI;

    private String playerName;

    public ConnectionManagerRMI(RemoteView view) throws RemoteException, NotBoundException {
        super(view);

        registry = LocateRegistry.getRegistry();
        serverRemoteRMI = (ServerRemoteRMI) registry.lookup("//localhost/ClientHandlerRMI");
        clientID = serverRemoteRMI.getClientID();

        LOGGER.info("ClientHandlerRMI: Lookup succesfully executed.");


        //TODO just for debugging purpose
        Scanner input = new Scanner(System.in);
        System.out.println("Tell me which port for RMI: ");
        int port = input.nextInt();
        UnicastRemoteObject.exportObject(this, port);
    }

    // AbstractConnectionManager
    @Override
    public void send(AbstractViewEvent event) {
        try {
            LOGGER.info("Client ConnectionManagerRMI sending event: " + event);
            serverRemoteRMI.receive(event);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void listen() {
        LOGGER.info("Client ConnectionManagerRMI ready.");
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    public void addClient() throws RemoteException {
        serverRemoteRMI.addClientRMI(this);
    }

    @Override
    public int getClientID() {
        return clientID;
    }


    @Override
    public void receive(AbstractModelEvent event) throws RemoteException {
        LOGGER.info("Client ConnectionManagerRMI receiving: " + event);
        received(event);
    }
}
