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

    public ConnectionManagerRMI(Client client) throws RemoteException, NotBoundException {
        super(client);

        registry = LocateRegistry.getRegistry();
        serverRemoteRMI = (ServerRemoteRMI) registry.lookup("//localhost/ClientHandlerRMI");

        LOGGER.info("ClientHandlerRMI: Lookup successfully executed.");

        //TODO just for debugging purpose
        Scanner input = new Scanner(System.in);
        System.out.println("Tell me which port for RMI: ");
        int port = input.nextInt();
        UnicastRemoteObject.exportObject(this, port);

        performRegistration();
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
    public void performRegistration() {
        LOGGER.info("Trying to sign up.");

        try {
            client.setClientID(serverRemoteRMI.askNewClientId());
            serverRemoteRMI.addClientRMI(this);
            LOGGER.info("Registration successfully executed. ClientId:\t" + client.getClientID());
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public void disconnect() {
        if (serverRemoteRMI != null) {
            try {
                serverRemoteRMI.removeClientRMI(this);
            } catch (RemoteException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
    }

    @Override
    public String getPlayerName() {
        return client.getPlayerName();
    }

    @Override
    public int getClientID() {
        return client.getClientID();
    }

    @Override
    public void setClientID(int clientID) throws RemoteException {
        client.setClientID(clientID);
    }

    @Override
    public void receive(AbstractModelEvent event) throws RemoteException {
        LOGGER.info("Client ConnectionManagerRMI receiving: " + event);
        received(event);
    }
}