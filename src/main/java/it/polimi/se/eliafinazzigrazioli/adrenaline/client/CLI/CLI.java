package it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.InterSquareLink;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CLI implements RemoteView, Runnable {
    static final Logger LOGGER = Logger.getLogger(CLI.class.getName());

    //TODO
    private String playerName;

    private Scanner input;

    private ConnectionManagerRMI connectionManagerRMI;

    private ConnectionManagerSocket connectionManagerSocket;

    private LocalModel localModel;

    private Client client;

    private List<Observer> observers = new ArrayList<>();

    public CLI(Client client) {
        input = new Scanner(System.in);
        this.client = client;
        localModel = new LocalModel();
    }

    @Override
    public LocalModel getLocalModel() {
        return localModel;
    }

    @Override
    public void error(Exception e) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }

    public void login(ArrayList<Avatar> availableAvatars) {
        System.out.println("Insert your player name");
        String username = input.nextLine();

        System.out.println("choose one of the following avatars:\n" + serializeArray(availableAvatars));

        notifyLoginRequestEvent(username, availableAvatars.get(nextInt()));
    }

    @Override
    public void loginSuccessful() {
        showMessage("Login successful, waiting for other players.");
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
        this.playerName = player;
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
    public int choseAction() {

        int choice;
        do {
            System.out.println("Choose your action: ");
            System.out.println("1) Move");
            System.out.println("2) Shoot");
            System.out.println("3) Collect");
            System.out.println("4) Show Map");
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Invalid choice");
                choice = -1;
            }
            if(choice == 4) {
                showMap();
                choice = -1;
            }
            if(choice < 1 || choice > 3)
                System.out.println("Not valid action");
        } while(choice < 1 || choice > 3);

        return choice;
    }

    public void collectPlay() {
        List<Coordinates> path = getPathFromUser(Rules.MAX_MOVEMENTS_BEFORE_COLLECTION);
        Coordinates finalCoordinates = path.get(path.size()-1); // Last element
        BoardSquareClient boardSquareClient = localModel.getGameBoard().getBoardSquareByCoordinates(finalCoordinates);
        if(boardSquareClient.isSpawnBoard()) {
            //new Weapon collect event
        }
        else {
            //new ammoCard collect event
        }
    }

    @Override
    public List<Coordinates> getPathFromUser(int maxSteps) {
        BoardSquareClient currentPose = localModel.getGameBoard().getPlayerPositionByName(this.playerName);
        List<Coordinates> path = new ArrayList<>();
        Map<Integer, Integer> allowChoice = new HashMap<>();
        int x, y;
        int step = 0;
        int choice;
        if(currentPose == null) {
            return null;
        }
        System.out.println("You are in " + currentPose);
        do {
            int count;
            do {
                count = 0;
                System.out.println("Insert your choose: ");
                if(currentPose.getNorth().equals(InterSquareLink.SAMEROOM) || currentPose.getNorth().equals(InterSquareLink.DOOR)) {
                    count++;
                    System.out.println(count + ") Up");
                    allowChoice.put(count, 1);
                }
                if(currentPose.getEast().equals(InterSquareLink.SAMEROOM) || currentPose.getEast().equals(InterSquareLink.DOOR)) {
                    count++;
                    System.out.println(count + ") Right");
                    allowChoice.put(count, 2);
                }
                if(currentPose.getSouth().equals(InterSquareLink.SAMEROOM) || currentPose.getSouth().equals(InterSquareLink.DOOR)) {
                    count++;
                    System.out.println(count + ") Down");
                    allowChoice.put(count, 3);
                }
                if(currentPose.getWest().equals(InterSquareLink.SAMEROOM) || currentPose.getWest().equals(InterSquareLink.DOOR)) {
                    count++;
                    System.out.println(count + ") Left");
                    allowChoice.put(count, 4);
                }
                count++;
                System.out.println(count + ") Stop");
                allowChoice.put(count, 5);
                String string = input.nextLine();
                try {
                    choice = Integer.parseInt(string);
                } catch(NumberFormatException e) {
                    System.out.println("Invalid choice");
                    choice = -1;
                }
            }while(choice < 1 || choice > count);

            choice = allowChoice.get(choice);
            Coordinates currentCoordinates = currentPose.getCoordinates();
            x = currentCoordinates.getXCoordinate();
            y = currentCoordinates.getYCoordinate();

            switch(choice) {
                case 1:
                    y++;
                    step++;
                    break;

                case 2:
                    x++;
                    step++;
                    break;

                case  3:
                    y--;
                    step++;
                    break;

                case 4:
                    x--;
                    step++;
                    break;
                case 5:
                    System.out.println("Creates path!");
                    break;
            }
            currentCoordinates = new Coordinates(x,y);
            currentPose = localModel.getGameBoard().getBoardSquareByCoordinates(currentCoordinates);
            if(choice != 5)
                path.add(new Coordinates(x,y));
            else if(choice == 5 && step == 0)
                return null;
        } while(step < maxSteps && choice != 5);
        return path;
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
            try {
                choice = Integer.parseInt(temp);
            } catch(NumberFormatException e) {
                System.out.println("Invalid choose");
                choice = -1;
            }
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
            try {
                choice = Integer.parseInt(temp);
            } catch(NumberFormatException e) {
                System.out.println("Invalid choice");
                choice = -1;
            }
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
            try {
                choice = Integer.parseInt(temp);
            } catch(NumberFormatException e) {
                System.out.println("Invalid choice");
                choice = -1;
            }
        }while( choice < 1 || choice > count );
        String temp = callableEffects.get(count);
        notifySelectedEffects(temp);
    }

    @Override
    public void collectWeapon(String collectedWeapon, String dropOfWeapon) {

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
    public void showMap() {

        String[][] mapText = localModel.getGameBoard().getMapText(localModel.getPlayersAvatarMap());
        for(int j=mapText[0].length-1; j>=0; j--) {
            for(int i=0; i<mapText.length; i++){
                System.out.print(mapText[i][j]);
            }
            System.out.println();
        }
    }



    @Override
    public void updatePlayerPosition(String nickname, Coordinates coordinates) {
        System.out.println(nickname + " position it's changed");
        localModel.getGameBoard().setPlayerPosition(nickname, coordinates);
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
            try {
                choice = Integer.parseInt(temp);
            } catch(NumberFormatException e) {
                System.out.println("Invalid choice");
                choice = -1;
            }
        }while( choice < 1 || choice > count );
        return cards.get(choice-1);
    }

    @Override
    public String getPlayer() {
        return client.getPlayerName();
    }


    @Override
    public void setClientID(int clientID) {
        client.setClientID(clientID);
    }

    @Override
    public int getClientID() {
        return client.getClientID();
    }

    @Override
    public void showMessage(Object message) {
        System.out.println(message);
    }

    @Override
    public void run() {

    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}