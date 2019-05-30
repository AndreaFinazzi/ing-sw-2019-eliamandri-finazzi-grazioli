package it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerSocket;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.LocalModel;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.RemoteView;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.BeginTurnEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.LightModel;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.LoginRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.PlayerConnectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CLI implements RemoteView {
    static final Logger LOGGER = Logger.getLogger(CLI.class.getName ());

    //todo
    private String playerName;

    private Scanner input;

    private ConnectionManagerRMI connectionManagerRMI;

    private ConnectionManagerSocket connectionManagerSocket;

    private LocalModel localModel;

    private int clientID;

    public CLI() {
        input = new Scanner(System.in);
        System.out.println("Insert your player name");
        playerName = input.nextLine();
    }

    public void login() {
        String playername = input.nextLine();
        notifyLoginRequestEvent(playername);
    }

    @Override
    public void showSelectableEvent(List<Coordinates> selectable) {
        System.out.println("You can select this ");
    }
    public static void main(String[] args) {

        CLI cli = new CLI();



/*
        cli.playersConnected();
        while(!cli.connectionManagerRMI.loginPlayer(cli.enterGame()));
        cli.playersConnected();
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

    @Override
    public void showBeginTurn(BeginTurnEvent event) {

    }
}
