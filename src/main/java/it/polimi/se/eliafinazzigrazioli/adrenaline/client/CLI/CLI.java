package it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.Client;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerSocket;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.RemoteView;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
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
    public Client getClient() {
        return client;
    }

    @Override
    public void error(Exception e) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }

    public void showLogin(ArrayList<Avatar> availableAvatars) {
        System.out.println("Insert your player name");
        String username = input.nextLine();

        System.out.println("choose one of the following avatars:\n" + serializeArray(availableAvatars));

        notifyLoginRequestEvent(username, availableAvatars.get(nextInt(availableAvatars.size() - 1)));
    }

    @Override
    public void showLoginSuccessful() {
        showMessage("Login successful, waiting for other players.");
    }

    @Override
    public void showMapVote(ArrayList<MapType> availableMaps) {
        showMessage("Vote one of the following maps: ");
        showMessage(serializeArray(availableMaps));

        notifyMapVoteEvent(availableMaps.get(nextInt(availableMaps.size() - 1)));
    }

    @Override
    public void showBeginMatch() {

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

    private int nextInt(int maxBound) {
        int nextInt;
        try {
            nextInt = input.nextInt();
            input.nextLine();
        } catch (NumberFormatException e) {
            showMessage("Invalid choice");
            nextInt = nextInt(maxBound);
        }

        if (nextInt > maxBound) {
            showMessage("Choice out of bound. Sit down and focus, you can do it: ");
            nextInt = nextInt(maxBound);
        }

        return nextInt;
    }

    private String nextLine() {
        String nextLine = "";
        while (input.hasNext()) {
            nextLine = input.nextLine();
        }
        return nextLine;
    }

    @Override
    public PlayerAction choseAction() {
        ArrayList<PlayerAction> availableActions = new ArrayList<>(Arrays.asList(PlayerAction.values()));
        PlayerAction choice;
        do {
            showMessage("Choose your action: ");
            showMessage(serializeArray(availableActions));

            choice = availableActions.get(nextInt(availableActions.size() - 1));
            if (choice == PlayerAction.SHOW_MAP) {
                showMap();
            }
        } while (choice == PlayerAction.SHOW_MAP);

        return choice;
    }

    @Override
    public void showSelectableSquare(List<Coordinates> selectable) {
        System.out.println("You can select this square: ");
        int count = 1;
        for (Coordinates coordinates : selectable) {
            BoardSquareClient boardSquareClient = localModel.getGameBoard().getBoardSquareByCoordinates(coordinates);
            if (boardSquareClient != null) {
                System.out.println(count + ") " + boardSquareClient.getRoom() + " with coordinates: " + coordinates);
                count++;
            }
        }
    }

    public void collectPlay() {
        List<Coordinates> path = getPathFromUser(Rules.MAX_MOVEMENTS_BEFORE_COLLECTION);
        Coordinates finalCoordinates = path.get(path.size() - 1); // Last element
        BoardSquareClient boardSquareClient = localModel.getGameBoard().getBoardSquareByCoordinates(finalCoordinates);
        if (boardSquareClient.isSpawnBoard()) {
            //new Weapon collect event
        } else {
            //new ammoCard collect event
        }
    }

    @Override
    public MoveDirection getMoveFromUser(BoardSquareClient currentPose, ArrayList<MoveDirection> availableMoves) {
        showMessage("You are in " + currentPose);
        MoveDirection choice;


        showMessage("Chose a move direction: ");
        showMessage(serializeArray(availableMoves));

        choice = availableMoves.get(nextInt(availableMoves.size() - 1));

        return choice;
    }

    @Override
    public void selectWeaponCard() {
        List<WeaponCardClient> weapons = localModel.getWeaponCards();
        int count = 1;
        for (WeaponCardClient weapon : weapons) {
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
            } catch (NumberFormatException e) {
                System.out.println("Invalid choose");
                choice = -1;
            }
        } while (choice < 1 || choice > count);
        String weapon = weapons.get(choice - 1).getWeaponName();
        notifySelectedWeaponCard(weapon);
    }

    @Override
    public void selectSelectableSquare(List<Coordinates> selectable) {
        System.out.println("You can select this square: ");
        int count = 1;
        for (Coordinates coordinates : selectable) {
            BoardSquareClient boardSquareClient = localModel.getGameBoard().getBoardSquareByCoordinates(coordinates);
            if (boardSquareClient != null) {
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
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice");
                choice = -1;
            }
        } while (choice < 1 || choice > count);
        Coordinates square = selectable.get(choice - 1);
        notifySelectedSquare(square);
    }

    @Override
    public void selectSelectableEffect(List<String> callableEffects) {
        System.out.println("You can select this effects ");
        int count = 1;
        for (String effect : callableEffects) {
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
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice");
                choice = -1;
            }
        } while (choice < 1 || choice > count);
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
        if (localModel.getGameBoard().getBoardSquareByCoordinates(coordinates).addWeaponCard(weaponCardClient)) {
            System.out.println(weaponCardClient);
            System.out.println("is drawed on coordinates: " + coordinates);
        } else
            System.out.println("ops, something didn't work");

    }


    @Override
    public void showMap() {
        String mapText = localModel.getGameBoard().getMapText(localModel.getPlayersAvatarMap());
        showMessage(mapText);
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
        for (PowerUpCard temp : cards) {
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
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice");
                choice = -1;
            }
        } while (choice < 1 || choice > count);
        return cards.get(choice - 1);
    }

    @Override
    public WeaponCardClient selectWeaponToReload(List<WeaponCardClient> reloadableWeapons) {
        Integer choice;
        System.out.println("Do you want to reload a weapon? [Y/n]");
        String temp = input.nextLine();
        if (temp.equalsIgnoreCase("n") || temp.equalsIgnoreCase("no") || temp.equalsIgnoreCase("not"))
            return null;
        if (reloadableWeapons == null) {
            System.out.println("ops, something didn't work");
            return null;
        }
        if (reloadableWeapons.size() == 0) {
            System.out.println("No weapons can be reloaded.");
            return null;
        }
        System.out.println("Insert your choice: ");
        int count = 0;
        for (WeaponCardClient weapon : reloadableWeapons) {
            count++;
            System.out.println(count + ") " + weapon.toReloadString());
        }
        do {
            try {
                temp = input.nextLine();
                choice = Integer.parseInt(temp);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice ");
                choice = -1;
            }
        } while (choice < 1 || choice > count);
        return reloadableWeapons.get(choice - 1);
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