package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ClientRemoteRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.LightModel;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.PlayerConnectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.view.RemoteView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
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
    private boolean response;
    private String responseMessage;



    public ClientHandlerRMI(Server server) throws RemoteException {
        //TODO
        listener = new RemoteView("tony");
        this.server = server;
    }

    @Override
    public int getClientID() throws RemoteException {
        return server.getCurrentClientID();
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

    @Override
    public void handleEvent(PlayerConnectedEvent event) throws HandlerNotImplementedException, RemoteException {
        try {
            server.addPlayer(event.getPlayer());
            response = true;
            // Test
            if(clientRMI.getPlayerName().toLowerCase().equals(event.getPlayer().toLowerCase())){
                for(int i=0; i<1000; i++)
                    clientRMI.print();
            }


        }catch(PlayerAlreadyPresentException e){
            response = false;
            responseMessage = "Player already present";
        }catch(MaxPlayerException e){
            response = false;
            responseMessage = "Max numbers of player";
        }
        server.voteMap(event.getChosenMap());
    }

    @Override
    public boolean response() {
        return response;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    @Override
    public List<String> getPlayersConnected() throws RemoteException {
        return server.getNextMatch().getPlayersNicknames();
    }

    @Override
    public List<String> getAvatarAvaible() throws RemoteException {
        return null;
    }

}
