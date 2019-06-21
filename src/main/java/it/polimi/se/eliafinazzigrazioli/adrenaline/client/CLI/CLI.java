package it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.Client;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerSocket;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.RemoteView;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;
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
            try {
                nextInt = input.nextInt();
                input.nextLine();
            } catch(NoSuchElementException e) {
                e.printStackTrace();
                nextInt = -1;
            }
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
            if(choice.equals(PlayerAction.SHOW_OWNED_WEAPONS)){
                List<WeaponCardClient> weaponCardClients = localModel.getWeaponCards();
                List<String[][]> weaponCardsMatrix = new ArrayList<>();
                for(WeaponCardClient iterator : weaponCardClients) {
                    weaponCardsMatrix.add(iterator.drawCard());
                }
                showMessage(CLIUtils.alignSquare(weaponCardsMatrix));
            }
            /*if( choice.equals(PlayerAction.SHOW_OWNED_PLAYERBOARD)) {
                showMessage(CLIUtils.serializeMap(localModel.getPlayerBoards()));
            }*/
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

    // TODO Never used
    @Override
    public void updateWeaponOnMap(WeaponCardClient weaponCardClient, Coordinates coordinates) {
        showMessage("You have collected this Weapon card");
        showMessage(weaponCardClient);
        if(localModel.getGameBoard().getBoardSquareByCoordinates(coordinates).addWeaponCard(weaponCardClient)) {
            showMessage(weaponCardClient);
            showMessage("is drawed on coordinates: " + coordinates);
        } else
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

    //todo implement
    @Override
    public PowerUpCardClient selectPowerUp(List<PowerUpCardClient> cards) {
        showMessage("You can select :");
        List<String[][]> listMatrix = new ArrayList<>();
        String string = "";
        int count = 0;
        for(PowerUpCardClient powerUpCardClient : cards) {
            listMatrix.add(powerUpCardClient.drawCard(true));
        }
        showMessage(CLIUtils.alignSquare(listMatrix));

        for(int i=0; i<cards.size(); i++){
            count++;
            string = string + count + ") \t\t\t\t\t";
        }
        showMessage("");
        showMessage(string);
        showMessage(count+1 + "Nothing \n");
        int selected = nextInt(cards.size()+1);
        if(selected<cards.size())
            return cards.get(selected);
        return null;
    }

    /*@Override
    public WeaponCardClient selectWeaponToReload(List<WeaponCardClient> reloadableWeapons) {
        if(reloadableWeapons == null) {
            showMessage("ops, something didn't work");
            return null;
        }
        else if (!reloadableWeapons.isEmpty()) {
            int choice;
            showMessage("Do you want to reload a weapon? [Y/n]");
            String temp = input.nextLine();
            if (temp.equalsIgnoreCase("n") || temp.equalsIgnoreCase("no") || temp.equalsIgnoreCase("not"))
                return null;
            showMessage("Insert your choice: ");
            showMessage(serializeList(reloadableWeapons));
            choice = nextInt(reloadableWeapons.size());
            return reloadableWeapons.get(choice);
        }
        else {
            showMessage("No weapons can be reloaded.");
            return null;
        }
    }*/

    @Override
    public void showPaymentUpdate(String player, List<PowerUpCardClient> powerUpCardClients, List<Ammo> ammos) {
        String message;
        List<String[][]> powerUpsMatrix = new ArrayList<>();
        if (player.equals(getClient().getPlayerName()))
            message = "You ";
        else
            message = "Player " + player;
        message = message + "payed the fallowing power ups:";
        for (PowerUpCardClient powerUpCardClient: powerUpCardClients)
                powerUpsMatrix.add(powerUpCardClient.drawCard(true));
        message = message + CLIUtils.alignSquare(powerUpsMatrix);
        message = message + "\nand the fallowing ammos: " + ammos;

        showMessage(message);
    }

    @Override
    public void showSpawn(String player, Coordinates spawnPoint, PowerUpCardClient spawnCard, boolean isOpponent) {
        String message = "";
        if(isOpponent) {
            message = message + "Player " + player;
        }
        else
            message = message + "You collected \n";
        message = message + " spawned on " + spawnPoint.toString() + CLIUtils.matrixToString(spawnCard.drawCard(true));
        showMessage(message);
    }

    @Override
    public void showPowerUpCollection(String player, PowerUpCardClient cardCollected, boolean isOpponent) {
        String message = "";
        if (isOpponent)
            message = message + "Player " + player + "collected a powerup\n";
        else
            message = message + "You collected \n" + CLIUtils.matrixToString(cardCollected.drawCard()) + "!\n";
        showMessage(message);
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
    public void showWeaponCollectionUpdate(String player, WeaponCardClient collectedCard, WeaponCardClient droppedCard, Room roomColor) {
        String message = "";
        String weapon = CLIUtils.matrixToString(collectedCard.drawCard(true));
        if(player.equalsIgnoreCase(getClient().getPlayerName()))
            message = "You ";
        else
            message = "Player " + player;
        message = message.concat(" collected the weapon:\n".concat(weapon).concat("from ").concat(roomColor.toString()));
        if(droppedCard != null) {
            String droppedWeapon = CLIUtils.matrixToString(droppedCard.drawCard(true));
            message = message.concat(" and dropped the weapon\n").concat(droppedWeapon);
            message = message.concat("\nback on board");
        }
        showMessage(message);
    }

    @Override
    public synchronized void showMessage(Object message) {
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