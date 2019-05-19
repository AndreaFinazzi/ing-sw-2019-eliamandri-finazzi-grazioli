package it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManager;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerSocket;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.LightModel;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.GenericViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.PlayerConnectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CLI {
    static final Logger LOGGER = Logger.getLogger(CLI.class.getName ());

    private String playerName;

    private Scanner input;

    private int clientId;

    private int chosenMap;

    private ConnectionManagerRMI connectionManagerRMI;

    private ConnectionManagerSocket connectionManagerSocket;

    private LightModel lightModel;

    public CLI() {
        input = new Scanner(System.in);
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }


    public void connectionRMI() {
        try {
            connectionManagerRMI = new ConnectionManagerRMI(playerName);
            connectionManagerRMI.addClient();
        }catch(RemoteException|NotBoundException e){
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public PlayerConnectedEvent enterGame(){
        System.out.println("Insert your name");
        //TODO check player already present in lightmodel
        playerName = input.nextLine();
        return new PlayerConnectedEvent(playerName);
    }


    public static void main(String[] args) throws RemoteException, NotBoundException, HandlerNotImplementedException {


/*
        Registry registry;
        registry = LocateRegistry.getRegistry ();
        EventViewListenerRemote RMI = (EventViewListenerRemote) registry.lookup ("ClientHandlerRMI");
        System.out.println("inserisci 1 o 2");
        int scelta = input.nextInt();
        if(scelta == 1)
            RMI.handleEvent (new GenericViewEvent ("Paccadent", "ciao"));
        else
            RMI.handleEvent(new GenericViewEvent("tony", "cpippo"));

 */

    }
}
