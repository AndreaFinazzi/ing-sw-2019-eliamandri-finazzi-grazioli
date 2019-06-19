package it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.Client;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerSocket;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.RemoteView;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
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
        showMessage("Insert your player name");
        String username = input.nextLine();

        showMessage("choose one of the following avatars:\n" + CLIUtils.serializeList(availableAvatars));

        notifyLoginRequestEvent(username, availableAvatars.get(nextInt(availableAvatars.size())));
    }

    @Override
    public void showLoginSuccessful() {
        showMessage("Login successful, waiting for other players.");
    }

    @Override
    public void showMapVote(ArrayList<MapType> availableMaps) {
        showMessage("Vote one of the following maps: ");
        showMessage(CLIUtils.serializeList(availableMaps));

        notifyMapVoteEvent(availableMaps.get(nextInt(availableMaps.size())));
    }

    @Override
    public void showBeginMatch() {

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
    public PlayerAction choseAction() {
        ArrayList<PlayerAction> availableActions = new ArrayList<>(Arrays.asList(PlayerAction.values()));
        PlayerAction choice;
        do {
            showMessage("Choose your action: ");
            showMessage(CLIUtils.serializeList(availableActions));

            choice = availableActions.get(nextInt(availableActions.size()));
            if (choice == PlayerAction.SHOW_MAP) {
                showMap();
            }
            if( choice.equals(PlayerAction.SHOW_OWNED_PLAYERBOARD)) {
                showMessage(CLIUtils.serializeMap(localModel.getPlayerBoards()));
            }
            if(choice.equals(PlayerAction.SHOW_OWNED_POWERUPS)) {
                List<PowerUpCardClient> powerUpCardClients = localModel.getPowerUpCards();
                List<String[][]> powerUpsMatrix = new ArrayList<>();
                for(PowerUpCardClient iterator:powerUpCardClients) {
                    powerUpsMatrix.add(iterator.drawCard());
                }
                showMessage(CLIUtils.alignSquare(powerUpsMatrix));
            }
        } while (choice.toString().contains("Show"));

        return choice;
    }

    @Override
    public void showSelectableSquare(List<Coordinates> selectable) {
        showMessage("You can select this square: ");
        showMessage(CLIUtils.serializeList(selectable));
    }

    public void collectPlay() {
        List<Coordinates> path = getPathFromUser(Rules.MAX_MOVEMENTS_BEFORE_COLLECTION);
        Coordinates finalCoordinates = path.get(path.size()); // Last element
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
        showMessage(CLIUtils.serializeList(availableMoves));

        choice = availableMoves.get(nextInt(availableMoves.size()));

        return choice;
    }

    @Override
    public void selectWeaponCard() {
        List<WeaponCardClient> weapons = localModel.getWeaponCards();
        showMessage("Insert your choice");
        showMessage(CLIUtils.serializeList(weapons));
        int choice = nextInt(weapons.size());
        String weapon = weapons.get(choice).getWeaponName();
        notifySelectedWeaponCard(weapon);
    }

    @Override
    public void selectSelectableSquare(List<Coordinates> selectable) {
        showMessage("You can select this square: ");
        int count = 1;
        for (Coordinates coordinates : selectable) {
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
        showMessage(CLIUtils.serializeList(callableEffects));
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
    public PowerUpCardClient selectPowerUpToKeep(List<PowerUpCardClient> cards) {
        showMessage("Choose your ");
        List<String[][]> list = new ArrayList<>();
        for(PowerUpCardClient iterator:cards){
            list.add(iterator.drawCard());
        }
        showMessage(CLIUtils.alignSquare(list));
        int choice = nextInt(cards.size());
        return cards.get(choice);
    }

    @Override
    public WeaponCardClient selectWeaponToReload(List<WeaponCardClient> reloadableWeapons) {
        int choice;
        showMessage("Do you want to reload a weapon? [Y/n]");
        String temp = input.nextLine();
        if (temp.equalsIgnoreCase("n") || temp.equalsIgnoreCase("no") || temp.equalsIgnoreCase("not"))
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
        showMessage(CLIUtils.serializeList(reloadableWeapons));
        choice = nextInt(reloadableWeapons.size());
        return reloadableWeapons.get(choice);
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



    // RICHIEDE DIMENSIONI MAGGIORI DI 2X2



}