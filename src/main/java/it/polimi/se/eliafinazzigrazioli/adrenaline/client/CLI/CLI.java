package it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.*;
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

    private Client client;

    public CLI(Client client) {
        input = new Scanner(System.in);
        this.client = client;
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
        playerConnectedEvent.setClientID(client.getClientID());
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
    public void showSelectableSquare(List<Coordinates> selectable) {
        System.out.println("You can select this square: ");
        int count = 1;
        for(Coordinates coordinates : selectable) {
            BoardSquareClient boardSquareClient = localModel.getGameBoard().getBoardSquareByCoordinates(coordinates);
            if(boardSquareClient != null) {
                System.out.println(count + ") " + boardSquareClient.getRoom() + " with coordinates: " + coordinates);
                count++;
            }
        }
    }

    @Override
    public void choseAction() {
        int choice;
        do {
            System.out.println("Choose your action: ");
            System.out.println("1) Move");
            System.out.println("2) Shoot");
            System.out.println("3) Collect");
            choice = Integer.parseInt(input.nextLine());
            if(choice < 1 || choice > 3)
                System.out.println("Not valid action");
        } while(choice < 1 || choice > 3);

        switch(choice) {
            case 1:
                //todo
                break;

            case 2:
                selectWeaponCard();
                break;

            case 3:
                //todo
                break;
        }
    }

    @Override
    public void selectWeaponCard() {
        List<String> weapons = localModel.getWeaponCards();
        int count = 1;
        for(String weapon : weapons){
            System.out.println(count + ") " + weapon);
            count++;
        }
        count--;
        System.out.println("Insert your choice");
        int choice;
        do {
            String temp = input.nextLine();
            choice = Integer.parseInt(temp);
        }while( choice < 1 || choice > count );
        String weapon = weapons.get(choice-1);
        notifySelectedWeaponCard(weapon);
    }

    @Override
    public void selectSelectableSquare(List<Coordinates> selectable) {
        System.out.println("You can select this square: ");
        int count = 1;
        for(Coordinates coordinates : selectable) {
            BoardSquareClient boardSquareClient = localModel.getGameBoard().getBoardSquareByCoordinates(coordinates);
            if(boardSquareClient != null) {
                System.out.println(count + ") " + boardSquareClient.getRoom() + " with coordinates: " + coordinates);
                count++;
            }
        }

        System.out.println("Insert your choice");
        count--;
        int choice;
        do {
            String temp = input.nextLine();
            choice = Integer.parseInt(temp);
        }while( choice < 1 || choice > count );
        Coordinates square = selectable.get(choice-1);
        notifySelectedSquare(square);
    }

    @Override
    public void selectSelectableEffect(List<String> callableEffects) {
        System.out.println("You can select this effects ");
        int count = 1;
        for(String effect : callableEffects) {
            System.out.println(count + ") " + effect);
            count++;
        }
        System.out.println("Insert your choice");
        count--;
        int choice;
        do {
            String temp = input.nextLine();
            choice = Integer.parseInt(temp);
        }while( choice < 1 || choice > count );
        String temp = callableEffects.get(count);
        notifySelectedEffects(temp);
    }

    @Override
    public void showBeginTurn(BeginTurnEvent event) {

    }


    @Override
    public void run() {

    }

    public void setClientID(int clientID) {
        client.setClientID(clientID);
    }

    @Override
    public int getClientID() {
        return client.getClientID();
    }
}
