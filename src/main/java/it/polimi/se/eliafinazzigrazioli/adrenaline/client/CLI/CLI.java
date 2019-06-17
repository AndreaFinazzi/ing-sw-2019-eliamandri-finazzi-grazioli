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
        showMessage("Insert your player name");
        String username = input.nextLine();

        showMessage("choose one of the following avatars:\n" + serializeList(availableAvatars));

        notifyLoginRequestEvent(username, availableAvatars.get(nextInt(availableAvatars.size())));
    }

    @Override
    public void loginSuccessful() {
        showMessage("Login successful, waiting for other players.");
    }

    @Override
    public void mapVote(ArrayList<MapType> availableMaps) {
        showMessage("Vote one of the following maps: ");
        showMessage(serializeList(availableMaps));

        notifyMapVoteEvent(availableMaps.get(nextInt(availableMaps.size())));
    }

    @Override
    public void updatePlayerInfo(String player) {
        client.setPlayerName(player);
        this.playerName = player;
    }

    private <T> String serializeList(List<T> list) {
        Iterator<T> iterator = list.iterator();
        int index = 0;
        String result = "";

        while (iterator.hasNext()) {
            index++;
            result = result.concat(String.format("%d)\t%s%n", index, iterator.next()));
        }

        return result;
    }

    private int nextInt() {
        int nextInt = input.nextInt();
        input.nextLine();

        return nextInt;
    }

    private int nextInt(int size) {

        int nextInt;
        do {
            nextInt = input.nextInt();
            input.nextLine();
            if(nextInt < 1 || nextInt > size) {
                showMessage("Choice out of bound. Sit down and focus, you can do it: ");
            }
        } while(nextInt < 1 || nextInt > size);

        return nextInt-1;
    }

    private String nextLine() {
        String nextLine = "";
        while (input.hasNext()) {
            nextLine = input.nextLine();
        }
        return nextLine;
    }

    @Override
    public void showSelectableSquare(List<Coordinates> selectable) {
        showMessage("You can select this square: ");
        serializeList(selectable);
    }

    @Override
    public void buildLocalMap(MapType mapType) {
        localModel.generatesGameBoard(mapType);
        showMessage("Chosen Map is: " + mapType);
        showMap();
    }

    @Override
    public int choseAction() {

        int choice;
        do {
            showMessage("Choose your action: ");
            showMessage("1) Move");
            showMessage("2) Shoot");
            showMessage("3) Collect");
            showMessage("4) Show Map");
            try {
                choice = Integer.parseInt(input.nextLine());
            } catch(NumberFormatException e) {
                showMessage("Invalid choice");
                choice = -1;
            }
            if(choice == 4) {
                showMap();
                choice = -1;
            }
            if(choice < 1 || choice > 3)
                showMessage("Not valid action");
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
        showMessage("You are in " + currentPose);
        do {
            int count;
            do {
                count = 0;
                showMessage("Insert your choice: ");
                if(currentPose.getNorth().equals(InterSquareLink.SAMEROOM) || currentPose.getNorth().equals(InterSquareLink.DOOR)) {
                    count++;
                    showMessage(count + ") Up");
                    allowChoice.put(count, 1);
                }
                if(currentPose.getEast().equals(InterSquareLink.SAMEROOM) || currentPose.getEast().equals(InterSquareLink.DOOR)) {
                    count++;
                    showMessage(count + ") Right");
                    allowChoice.put(count, 2);
                }
                if(currentPose.getSouth().equals(InterSquareLink.SAMEROOM) || currentPose.getSouth().equals(InterSquareLink.DOOR)) {
                    count++;
                    showMessage(count + ") Down");
                    allowChoice.put(count, 3);
                }
                if(currentPose.getWest().equals(InterSquareLink.SAMEROOM) || currentPose.getWest().equals(InterSquareLink.DOOR)) {
                    count++;
                    showMessage(count + ") Left");
                    allowChoice.put(count, 4);
                }
                count++;
                showMessage(count + ") Stop");
                allowChoice.put(count, 5);
                String string = input.nextLine();
                try {
                    choice = Integer.parseInt(string);
                } catch(NumberFormatException e) {
                    showMessage("Invalid choice");
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
                    showMessage("Creates path!");
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
        showMessage("Insert your choice");
        serializeList(weapons);
        int choice = nextInt(weapons.size());
        String weapon = weapons.get(choice).getWeaponName();
        notifySelectedWeaponCard(weapon);
    }

    @Override
    public void selectSelectableSquare(List<Coordinates> selectable) {
        showMessage("You can select this square: ");
        int count = 1;
        for(Coordinates coordinates : selectable) {
            BoardSquareClient boardSquareClient = localModel.getGameBoard().getBoardSquareByCoordinates(coordinates);
            if(boardSquareClient != null) {
                showMessage(count + ") " + boardSquareClient.getRoom() + " with coordinates: " + coordinates);
                count++;
            }
        }
        int choice = nextInt(selectable.size());
        Coordinates square = selectable.get(choice);
        notifySelectedSquare(square);
    }

    @Override
    public void selectSelectableEffect(List<String> callableEffects) {
        showMessage("You can select this effects ");
        serializeList(callableEffects);
        showMessage("Insert your choice");
        int choice = nextInt(callableEffects.size());
        String temp = callableEffects.get(choice);
        notifySelectedEffects(temp);
    }

    @Override
    public void collectWeapon(String collectedWeapon, String dropOfWeapon) {

    }

    @Override
    public void updateWeaponOnMap(WeaponCardClient weaponCardClient, Coordinates coordinates) {
        showMessage("You have collected this Weapon card");
        showMessage(weaponCardClient);
        if(localModel.getGameBoard().getBoardSquareByCoordinates(coordinates).addWeaponCard(weaponCardClient)) {
            showMessage(weaponCardClient);
            showMessage("is drawed on coordinates: " + coordinates);
        }
        else
            showMessage("ops, something didn't work");

    }


    @Override
    public void showMap() {
        String mapText = localModel.getGameBoard().getMapText(localModel.getPlayersAvatarMap());
        showMessage(mapText);
    }





    @Override
    public void updatePlayerPosition(String nickname, Coordinates coordinates) {
        showMessage(nickname + " position it's changed");
        localModel.getGameBoard().setPlayerPosition(nickname, coordinates);
    }

    @Override
    public PowerUpCard selectPowerUpToKeep(List<PowerUpCard> cards) {
        showMessage("Choose your ");
        serializeList(cards);
        int choice = nextInt(cards.size());
        return cards.get(choice);
    }

    @Override
    public WeaponCardClient selectWeaponToReload(List<WeaponCardClient> reloadableWeapons) {
        int choice;
        showMessage("Do you want to reload a weapon? [Y/n]");
        String temp = input.nextLine();
        if(temp.equalsIgnoreCase("n") || temp.equalsIgnoreCase("no") || temp.equalsIgnoreCase("not"))
            return null;
        if(reloadableWeapons == null) {
            showMessage("ops, something didn't work");
            return null;
        }
        if(reloadableWeapons.size() == 0) {
            showMessage("No weapons can be reloaded.");
            return null;
        }
        showMessage("Insert your choice: ");
        showMessage(serializeList(reloadableWeapons));
        choice = nextInt(reloadableWeapons.size());
        return reloadableWeapons.get(choice);
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