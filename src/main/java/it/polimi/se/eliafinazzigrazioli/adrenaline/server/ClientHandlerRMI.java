package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ClientRemoteRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.LightModel;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.view.RemoteView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


// RMI SERVER
public class ClientHandlerRMI extends UnicastRemoteObject implements EventViewListenerRemote {

    private boolean setted;
    private ClientRemoteRMI clientRMI;
    private EventListenerInterface listener;
    private transient Server server;
    private String playerName;
    static final Logger LOGGER = Logger.getLogger(ClientHandlerRMI.class.getName ());




    public ClientHandlerRMI(Server server) throws RemoteException {
        listener = new RemoteView("tony");
        this.server = server;
    }

    @Override
    public int getClientID() throws RemoteException {
        return 0;
    }

    @Override
    public void setClientRMI(ClientRemoteRMI clientRMI) {
        this.clientRMI = clientRMI;
        try{
            playerName = clientRMI.getPlayerName();
        }catch(RemoteException e){
            e.printStackTrace();
        }
        LOGGER.log(Level.INFO, "established connection RMI");
    }

    @Override
    public ClientRemoteRMI getClientRMI() {
        return clientRMI;
    }

    public boolean isSetted() {
        return setted;
    }

    public void setSetted(boolean setted) {
        this.setted = setted;
    }

    @Override
    public boolean checkPlayerName() throws RemoteException {
        return false;
    }

    //TODO
    @Override
    public LightModel getLightModel() throws RemoteException {
        return null;
    }
}
