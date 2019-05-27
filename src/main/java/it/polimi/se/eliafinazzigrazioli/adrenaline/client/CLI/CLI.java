package it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerSocket;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.LightModel;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.PlayerConnectedEvent;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CLI {
    static final Logger LOGGER = Logger.getLogger(CLI.class.getName ());

    //todo
    private String playerName;

    private Scanner input;

    private int clientId;

    private ConnectionManagerRMI connectionManagerRMI;

    private ConnectionManagerSocket connectionManagerSocket;

    private LightModel lightModel;

    private int clientID;

    public CLI() {
        input = new Scanner(System.in);
        System.out.println("Insert your player name");
        playerName = input.nextLine();
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void playersConnected(){
        try {
            List<String> players = connectionManagerRMI.getPlayersConnected();
            for(String nickname : players) {
                System.out.println(nickname);
            }
        }catch(RemoteException e){
            e.printStackTrace();
        }

    }


    public void connectionRMI() {
        try {
            connectionManagerRMI = new ConnectionManagerRMI(playerName);
            connectionManagerRMI.addClient();
            clientID = connectionManagerRMI.getClientID();
        }catch(RemoteException|NotBoundException e){
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public void connectionSocket(){
        connectionManagerSocket = new ConnectionManagerSocket(playerName);
    }

    //
    public PlayerConnectedEvent enterGame(){
        PlayerConnectedEvent playerConnectedEvent;
        String buffer; int chosen;
        //TODO check player already present in lightmodel
        playerConnectedEvent = new PlayerConnectedEvent(playerName);
        playerConnectedEvent.setClientID(clientID);
        do {
            System.out.print("Chose map:\n1) first map\n2) second map\n3) third map\n4) fourth map\n");
            buffer = input.nextLine();
            chosen = Integer.parseInt(buffer);
        }while(chosen < 1 || chosen > 4);
        playerConnectedEvent.setChosenMap(chosen);
        do {
            System.out.print("Chose avatar:\n1) :D-STRUCT-OR\n2) BANSHEE\n3) DOZER\n4) VIOLET\n5) SPROG\n");
            buffer = input.nextLine();
            chosen = Integer.parseInt(buffer);
        }while(chosen < 1 || chosen > 5);

        switch(chosen){
            case 1:
                playerConnectedEvent.setAvatar(":D-STRUCT-OR");
                break;
            case 2:
                playerConnectedEvent.setAvatar("BANSHEE");
                break;
            case 3:
                playerConnectedEvent.setAvatar("DOZER");
                break;
            case 4:
                playerConnectedEvent.setAvatar("VIOLET");
                break;
            case 5:
                playerConnectedEvent.setAvatar("SPROG");
                break;
        }
        return playerConnectedEvent;
    }

    public int getClientID() {
        return clientID;
    }

    public static void main(String[] args) {
        CLI cli = new CLI();
        cli.connectionRMI();
        cli.playersConnected();
        while(!cli.connectionManagerRMI.loginPlayer(cli.enterGame()));
        cli.playersConnected();
        //System.out.println("My clientID is " + cli.clientID);

        System.out.println("finish");

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
