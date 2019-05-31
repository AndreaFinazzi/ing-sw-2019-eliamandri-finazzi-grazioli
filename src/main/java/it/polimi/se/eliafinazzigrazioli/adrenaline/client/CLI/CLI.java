package it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerSocket;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.LocalModel;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.RemoteView;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.BeginTurnEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.PlayerConnectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class CLI implements RemoteView {
    static final Logger LOGGER = Logger.getLogger(CLI.class.getName());

    //TODO
    private String playerName;

    private Scanner input;

    private ConnectionManagerRMI connectionManagerRMI;

    private ConnectionManagerSocket connectionManagerSocket;

    private LocalModel localModel;

    private int clientID;

    public CLI() {
        input = new Scanner(System.in);
        this.clientID = clientID;
    }

    public void login() {
        System.out.println("Insert your player name");
        playerName = input.nextLine();

        notifyLoginRequestEvent(playerName);
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }


    //
    public PlayerConnectedEvent enterGame() {
        PlayerConnectedEvent playerConnectedEvent;
        String buffer;
        int chosen;
        //TODO check player already present in lightmodel
        playerConnectedEvent = new PlayerConnectedEvent(playerName);
        playerConnectedEvent.setClientID(clientID);
        do {
            System.out.print("Chose map:\n1) first map\n2) second map\n3) third map\n4) fourth map\n");
            buffer = input.nextLine();
            chosen = Integer.parseInt(buffer);
        } while (chosen < 1 || chosen > 4);
        playerConnectedEvent.setChosenMap(chosen);
        do {
            System.out.print("Chose avatar:\n1) :D-STRUCT-OR\n2) BANSHEE\n3) DOZER\n4) VIOLET\n5) SPROG\n");
            buffer = input.nextLine();
            chosen = Integer.parseInt(buffer);
        } while (chosen < 1 || chosen > 5);

        switch (chosen) {
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

    @Override
    public void showSelectableEvent(List<Coordinates> selectable) {
        System.out.println("You can select this ");
    }

    @Override
    public void showBeginTurn(BeginTurnEvent event) {

    }

    @Override
    public void run() {

    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public int getClientID() {
        return clientID;
    }
}
