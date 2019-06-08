package it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CLI implements RemoteView {
    static final Logger LOGGER = Logger.getLogger(CLI.class.getName());

    //TODO
    private Scanner input;

    private ConnectionManagerRMI connectionManagerRMI;

    private ConnectionManagerSocket connectionManagerSocket;

    private LocalModel localModel;

    private Client client;

    public CLI(Client client) {
        input = new Scanner(System.in);
        this.client = client;
        localModel = new LocalModel();
    }

    @Override
    public void error(Exception e) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }

    public void login(ArrayList<Avatar> availableAvatars) {
        System.out.println("Insert your player name");
        String playerName = input.nextLine();

        System.out.println("choose one of the following avatars:\n" + serializeArray(availableAvatars));

        notifyLoginRequestEvent(playerName, availableAvatars.get(nextInt()));
    }

    @Override
    public void mapVote(ArrayList<MapType> availableMaps) {
        showMessage("Vote one of the following maps: ");
        showMessage(serializeArray(availableMaps));

        notifyMapVoteEvent(availableMaps.get(nextInt()));
    }

    @Override
    public void updatePlayerInfo(String player) {
        client.setPlayerName(player);
    }

    @Override
    public void updateMatchPlayers(HashMap<String, Avatar> playerToAvatarMap) {
        //TODO
    }

    private <T> String serializeArray(ArrayList<T> list) {
        Iterator<T> iterator = list.iterator();
        int index = 0;
        String result = "";

        while (iterator.hasNext()) {
            result = result.concat(String.format("%d)\t%s%n", index, iterator.next()));
            index++;
        }

        return result;
    }

    private int nextInt() {
        int nextInt = input.nextInt();
        input.nextLine();

        return nextInt;
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
    public void buildLocalMap(MapType mapType) {
        localModel.generatesGameBoard(mapType);
        System.out.println("Map is chosen is: " + mapType);
        //todo showmap();
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
                notifyRequestMove(client.getClientID(), client.getPlayerName());
                break;

            case 2:
                selectWeaponCard();
                //wait response with selectable effect
                break;

            case 3:
                //todo
                break;
        }
    }

    @Override
    public void selectWeaponCard() {
        List<WeaponCardClient> weapons = localModel.getWeaponCards();
        int count = 1;
        for(WeaponCardClient weapon : weapons){
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
        String weapon = weapons.get(choice-1).getWeaponName();
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
    public void collectWeapon(String collectedWeapon, String dropOfWeapon) {

    }

    @Override
    public void showBeginTurn(String currentPlayer) {
        showMessage("The current player is: " + currentPlayer);
    }

    @Override
    public void updateWeaponOnMap(WeaponCardClient weaponCardClient, Coordinates coordinates) {
        System.out.println("You have collected this Weapon card");
        System.out.println(weaponCardClient);
        if(localModel.getGameBoard().getBoardSquareByCoordinates(coordinates).addWeaponCard(weaponCardClient)) {
            System.out.println(weaponCardClient);
            System.out.println("is drawed on coordinates: " + coordinates);
        }
        else
            System.out.println("ops, something didn't work");

    }

    @Override
    public void showPlayerMovement(String playerName, List<Coordinates> path) {

    }

    @Override
    public void showMap() {
        System.out.println(" ________________________________________________________________");
        System.out.printf("|\t\t\t\t|\t\t\t\t|\t\t\t\t|\t\t\t\t|\n");
        System.out.printf("|\t\t\t\t|\t\t\t\t|\t\t\t\t|\t\t\t\t|\n");
        System.out.printf("|\t  Color  \t|\t  Color  \t|\t  Color  \t|\t  Color  \t|\n");
        System.out.println("|\t\t\t\t|\t\t\t\t|\t\t\t\t|\t\t\t\t|");
        System.out.printf("|\t\t\t\t|\t\t\t\t|\t\t\t\t|\t\t\t\t|\n");
        System.out.print(" ________________");
        System.out.print("________________");
        System.out.print("________________");
        System.out.println("________________");
        System.out.printf("|\t\t\t\t|\t\t\t\t|\t\t\t\t|\t\t\t\t|\n");
        System.out.printf("|\t\t\t\t|\t\t\t\t|\t\t\t\t|\t\t\t\t|\n");
        System.out.printf("|\t  Color  \t|\t  Color  \t|\t  Color  \t|\t  Color  \t|\n");
        System.out.printf("|\t\t\t\t|\t\t\t\t|\t\t\t\t|\t\t\t\t|\n");
        System.out.printf("|\t\t\t\t|\t\t\t\t|\t\t\t\t|\t\t\t\t|\n");
        System.out.print(" ________________");
        System.out.print("________________");
        System.out.print("________________");
        System.out.println("________________");
        System.out.printf("|\t\t\t\t|\t\t\t\t|\t\t\t\t|\t\t\t\t|\n");
        System.out.printf("|\t\t\t\t|\t\t\t\t|\t\t\t\t|\t\t\t\t|\n");
        System.out.printf("|\t  Color  \t|\t  Color  \t|\t  Color  \t|\t  Color  \t|\n");
        System.out.printf("|\t\t\t\t|\t\t\t\t|\t\t\t\t|\t\t\t\t|\n");
        System.out.printf("|\t\t\t\t|\t\t\t\t|\t\t\t\t|\t\t\t\t|\n");
        System.out.print(" ________________");
        System.out.print("________________");
        System.out.print("________________");
        System.out.println("________________");
    }



    @Override
    public void updatePlayerPosition(String nickname, Coordinates coordinates) {
        System.out.println(nickname + " position it's changed");
        BoardSquareClient boardSquareClient = localModel.getGameBoard().getBoardSquareByCoordinates(coordinates);
        localModel.getPlayersPosition().put(nickname, boardSquareClient);
    }

    @Override
    public PowerUpCard selectPowerUpToKeep(List<PowerUpCard> cards) {
        System.out.println("Choose your ");
        int count = 1;
        for(PowerUpCard temp : cards){
            System.out.println(count + ") " + temp.getPowerUpType() + " " + temp.getEquivalentAmmo());
            count++;
        }
        System.out.println("Insert your choice");
        count--;
        int choice;
        do {
            String temp = input.nextLine();
            choice = Integer.parseInt(temp);
        }while( choice < 1 || choice > count );
        localModel.addPowerUp(cards.get(choice-1));
        return cards.get(choice-1);
    }

    @Override
    public String getPlayer() {
        return client.getPlayerName();
    }

    @Override
    public int getClientID() {
        return client.getClientID();
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void run() {

    }

}
