package it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.Client;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerRMI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.ConnectionManagerSocket;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.RemoteView;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
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
    public void error(Exception e) {
        LOGGER.log(Level.SEVERE, e.getMessage(), e);
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
    public synchronized void showMessage(Object message) {
        System.out.println(message + Color.RESET.toString());
    }

    @Override
    public void showMap() {
        String mapText = localModel.getGameBoard().getMapText(localModel.getPlayersAvatarMap());
        showMessage(mapText);
    }

    public void showLogin(List<Avatar> availableAvatars) {
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
    public void showMapVote(List<MapType> availableMaps) {
        showMessage("Vote one of the following maps: ");
        showMessage(CLIUtils.serializeList(availableMaps));
        notifyMapVoteEvent(availableMaps.get(nextInt(availableMaps.size())));
    }

    @Override
    public void showBeginMatch() {

    }

    @Override
    public void showPaymentUpdate(String player, List<PowerUpCardClient> powerUpCardClients, List<Ammo> ammos) {
        String message;
        List<String[][]> powerUpsMatrix = new ArrayList<>();
        if (player.equals(getClient().getPlayerName()))
            message = "You ";
        else
            message = "Player " + player + " " + localModel.getPlayersAvatarMap().get(player);
        message = message + " payed the fallowing power ups:";
        for (PowerUpCardClient powerUpCardClient: powerUpCardClients)
            powerUpsMatrix.add(powerUpCardClient.drawCard(true));
        message = message + CLIUtils.alignSquare(powerUpsMatrix);
        message = message + "\nand the fallowing ammos: " + ammos;

        showMessage(message);
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
    public void showPlayerMovedByWeaponUpdate(String attackingPlayer, String playerMoved, String weaponUsed, Coordinates finalPosition) {
        String message;
        if (attackingPlayer.equals(getClient().getPlayerName()))
            message = "You ";
        else
            message = attackingPlayer;
        message = message + " moved ";
        if (playerMoved.equals(getClient().getPlayerName()))
            message = message + "you ";
        else
            message = message + playerMoved;
        message = message + " to square " + getLocalModel().getGameBoard().getBoardSquareByCoordinates(finalPosition) + " using weapon " + weaponUsed + "!";
        showMessage(message);
    }

    @Override
    public void showShotPlayerUpdate(String damagedPlayer, List<DamageMark> damages, List<DamageMark> marks, List<DamageMark> removedMarks) {
        String message;
        if(damagedPlayer.equals(getClient().getPlayerName()))
            message = "You ";
        else
            message = damagedPlayer;

        String stringMarks = "";
        String stringDamages = "";
        for(DamageMark mark : marks) {
            stringMarks = stringMarks + mark.toString() + " ";
        }
        for(DamageMark damage : damages) {
            stringDamages = stringDamages + damage.toString() + " ";
        }
        message = message + " receive damages " + stringDamages + " " + (marks.size() > 0 ? "\n and marks " + marks : "");
        if (removedMarks.size() > 0)
            message = message + "\n" + removedMarks.size() + " damages were previously delivered marks.";
        showMessage(message);
    }

    @Override
    public void showPlayerMovementUpdate(String player, List<Coordinates> path) {
        if (path != null && path.size() > 0) {
            if (player.equals(getClient().getPlayerName()))
                showMessage("You moved to square" + getLocalModel().getGameBoard().getBoardSquareByCoordinates(path.get(path.size()-1)) + " through the path: ");
            else
                showMessage("Player " + player + " moved to square" + getLocalModel().getGameBoard().getBoardSquareByCoordinates(path.get(path.size()-1)) + " through the path: ");
            for (Coordinates coordinates: path)
                showMessage(getLocalModel().getGameBoard().getBoardSquareByCoordinates(coordinates));
        }
    }

    @Override
    public void showSpawnUpdate(String player, Coordinates spawnPoint, PowerUpCardClient spawnCard, boolean isOpponent) {
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
    public void showPowerUpCollectionUpdate(String player, PowerUpCardClient cardCollected, boolean isOpponent) {
        String message = "";
        if (isOpponent)
            message = message + "Player " + player + "collected a powerup\n";
        else
            message = message + "You collected \n" + CLIUtils.matrixToString(cardCollected.drawCard()) + "!\n";
        showMessage(message);
    }

    @Override
    public void showBeginTurnUpdate(String player) {
        Avatar avatar = localModel.getPlayersAvatarMap().get(player);
        if(avatar == null) {
            showMessage("ops, something didn't work");
            return;
        }
        if(player.equals(getClient().getPlayerName())) {
            showMessage("Your turn began!!!\nYou are : " + avatar);
        }
        else
            showMessage(player + "'s turn began!!!\n" + player + " avatar is: " + avatar);
    }

    @Override
    public void showEndTurnUpdate(String player) {
        Avatar avatar = localModel.getPlayersAvatarMap().get(player);
        if(avatar == null) {
            showMessage("ops, something didn't work");
            return;
        }
        if(player.equals(getClient().getPlayerName())) {
            showMessage("YEAH!!! You concluded your turn\nYou are : " + avatar);
        }
        else
            showMessage(player + "'s turn began!!!\n" + player + " avatar is: " + avatar);
    }

    @Override
    public void showAmmoCollectedUpdate(String player, Ammo ammo, boolean actuallyCollected, boolean lastOfCard) {
        Avatar avatar = localModel.getPlayersAvatarMap().get(player);
        if(avatar == null) {
            showMessage("ops, something didn't work");
            return;
        }
        if (player.equals(getClient().getPlayerName()))
            showMessage("You collected a " + " "+ ammo.toString() + " munition " + (actuallyCollected ? "!" : " but your playerBoard was full!"));
        else
            showMessage(player + " " + avatar + " collected a " + ammo.toString() + " munition " + (actuallyCollected ? "!" : " but his playerBoard was full!"));
    }

    @Override
    public void showAmmoCardCollectedUpdate(String player, AmmoCardClient ammoCard, Coordinates coordinates) {
        Avatar avatar = localModel.getPlayersAvatarMap().get(player);
        if(avatar == null) {
            showMessage("ops, something didn't work");
            return;
        }
        String message = " gathered the ammo card \n" + CLIUtils.matrixToString(ammoCard.drawCard()) + " from square " + coordinates + "!";
        if (player.equals(getClient().getPlayerName()))
            showMessage("You "+ avatar + " " + message);
        else
            showMessage(player + " " + avatar + message);
    }

    @Override
    public void showAmmoCardResettingUpdate(Map<Coordinates, AmmoCardClient> coordinatesAmmoCardMap) {
        showMessage("Gathered ammo cards are being replaced...");
    }

    @Override
    public void showWeaponCardResettingUpdate(Map<Coordinates, List<WeaponCardClient>> coordinatesWeaponsMap) {
        showMessage("Weapons are being placed on the spawn points...\n");

        for(Map.Entry<Coordinates, List<WeaponCardClient>> entry : coordinatesWeaponsMap.entrySet()) {
            showMessage("At the spawn point in " + entry.getKey() +" " + localModel.getGameBoard().getBoardSquareByCoordinates(entry.getKey()).getRoom());
            List<String[][]> listWeapon = new ArrayList<>();
            for(WeaponCardClient weapon : entry.getValue()) {
                listWeapon.add(weapon.drawCard(true));
            }
            showMessage((CLIUtils.alignSquare(listWeapon)));
        }
    }

    /**INTERACTION (INPUT) METHODS   NB: THEY MUST NOT BE VOID FUNCTIONS--------------------------------------------------------------------------------------------------------------------------------------------------------*/

    @Override
    public MoveDirection selectDirection(BoardSquareClient currentPose, ArrayList<MoveDirection> availableMoves) {
        showMessage("You are in " + currentPose);
        MoveDirection choice;
        showMessage("Chose a move direction: ");
        showMessage(CLIUtils.serializeList(availableMoves));
        choice = availableMoves.get(nextInt(availableMoves.size()));
        return choice;
    }

    @Override
    public PlayerAction selectAction() {
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
                if(weaponCardClients.isEmpty()) {
                    showMessage("No weapon to show");
                }
                else {
                    List<String[][]> weaponCardsMatrix = new ArrayList<>();
                    for(WeaponCardClient iterator : weaponCardClients) {
                        weaponCardsMatrix.add(iterator.drawCard());
                    }
                    showMessage(CLIUtils.alignSquare(weaponCardsMatrix));
                }
            }
            if(choice.equals(PlayerAction.SHOW_PLAYERBOARDS)) {
                List<PlayerClient> playerClients = new ArrayList<>();
                for(PlayerClient playerClient : localModel.getOpponentsList()) {
                    playerClients.add(playerClient);
                }
                for(PlayerClient playerClient : playerClients) {
                    showMessage(CLIUtils.matrixToString(playerClient.drawCard()));
                }
                showMessage(CLIUtils.matrixToString(localModel.drawCard()));
            }
            if(choice.equals(PlayerAction.SHOW_OWNED_POWERUPS)) {
                List<PowerUpCardClient> powerUpCardClients = localModel.getPowerUpCards();
                List<String[][]> powerUpsMatrix = new ArrayList<>();
                for(PowerUpCardClient iterator:powerUpCardClients) {
                    powerUpsMatrix.add(iterator.drawCard());
                }
                showMessage(CLIUtils.alignSquare(powerUpsMatrix));
            }
            if(choice.equals(PlayerAction.SHOW_SPAWN_WEAPON)) {
                showWeaponOnSpawn();
            }
        } while (choice.toString().contains("Show"));

        return choice;
    }

    @Override
    public PowerUpCardClient selectPowerUpToKeep(List<PowerUpCardClient> cards) {
        showMessage("Choose your ");
        List<String[][]> list = new ArrayList<>();
        List<String> powerUpName = new ArrayList<>();
        for(PowerUpCardClient iterator:cards){
            list.add(iterator.drawCard());
            powerUpName.add(iterator.toStringLight());
        }
        showMessage(CLIUtils.alignSquare(list));
        showMessage(CLIUtils.serializeList(powerUpName));
        int choice = nextInt(cards.size());
        return cards.get(choice);
    }

    @Override
    public PowerUpCardClient selectPowerUp(List<PowerUpCardClient> cards) {
        showMessage("You can select :");
        List<String[][]> listMatrix = new ArrayList<>();
        List<String> cardsName = new ArrayList<>();
        if(cards.size() == 0)
            return null;
        for(PowerUpCardClient powerUpCardClient : cards) {
            listMatrix.add(powerUpCardClient.drawCard(true));
            cardsName.add(Color.ammoToColor(powerUpCardClient.getEquivalentAmmo()).toString() + powerUpCardClient.getPowerUpType() + Color.RESET);
        }
        showMessage(CLIUtils.alignSquare(listMatrix));
        cardsName.add("Nothing");
        showMessage(CLIUtils.serializeList(cardsName));
        int selected = nextInt(cardsName.size());
        if(selected<cards.size())
            return cards.get(selected);
        return null;
    }

    @Override
    public Coordinates selectCoordinates(List<Coordinates> selectable) {
        showMessage("Select a boardSquare: ");
        int count = 1;
        for (Coordinates coordinates : selectable) {
            BoardSquareClient boardSquareClient = localModel.getGameBoard().getBoardSquareByCoordinates(coordinates);
            if(boardSquareClient != null) {
                showMessage(count + ") " + boardSquareClient.getRoom() + " with coordinates: " + coordinates);
                count++;
            }
        }
        int choice = nextInt(selectable.size());
        if(choice == -1)
            return null;
        Coordinates square = selectable.get(choice);
        return square;
    }

    @Override
    public String selectPlayer(List<String> players) {
        if(players.isEmpty()) {
            showMessage("No player to select.");
            return null;
        }
        showMessage(CLIUtils.serializeList(players));
        return players.get(nextInt(players.size()));
    }

    @Override
    public WeaponCardClient selectWeaponCardFromSpawnSquare(Coordinates coordinates, List<WeaponCardClient> selectableWeapons) {
        if (selectableWeapons.isEmpty()) {
            showMessage("No weapons to select.");
            return null;
        }
        showMessage("Select a weapon from the spawn at the coordinates: " + coordinates);
        List<String[][]> weaponsMatrix = new ArrayList<>();
        List<String> weaponName = new ArrayList<>();
        for(WeaponCardClient weapon : selectableWeapons) {
            weaponsMatrix.add(weapon.drawCard(true));
            weaponName.add(Color.ammoToColor(weapon.getWeaponColor()).toString() + weapon.getWeaponName() + Color.RESET.toString());
        }
        String alignSquare = CLIUtils.alignSquare(weaponsMatrix);
        showMessage(alignSquare);
        showMessage(CLIUtils.serializeList(weaponName));
        return selectableWeapons.get(nextInt(selectableWeapons.size()));
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
    public WeaponCardClient selectWeaponCardFromHand(List<WeaponCardClient> selectableWeapons) {
        if(selectableWeapons == null) {
            showMessage("ops, something didn't work\n");
            return null;
        }
        if(selectableWeapons.isEmpty()) {
            showMessage("No weapon to select.\n");
            return null;
        }
        List<String> weaponsName = new ArrayList<>();
        for(WeaponCardClient selectableWeapon : selectableWeapons) {
            weaponsName.add(Color.ammoToColor(selectableWeapon.getWeaponColor()).toString() + selectableWeapon.getWeaponName() + Color.RESET);
        }
        showMessage("You can select :\n");
        showMessage(CLIUtils.serializeList(weaponsName));
        showMessage("Enter: ");
        return selectableWeapons.get(nextInt(selectableWeapons.size()));
    }

    @Override
    public WeaponEffectClient selectWeaponEffect(WeaponCardClient weapon, List<WeaponEffectClient> callableEffects) {
        showMessage("You can select this effects from " + weapon.getWeaponName());
        showMessage(CLIUtils.serializeList(callableEffects));
        showMessage(callableEffects.size()+1 + ") Nothing");
        showMessage("Enter: ");
        int select = nextInt(callableEffects.size()+1);
        if(select == -1)
            return null;
        if(select == callableEffects.size())
            return null;
        return callableEffects.get(select);
    }

    @Override
    public boolean selectYesOrNot() {
        showMessage("Yes or not?");
        String nextLine = input.nextLine();
        if(nextLine.equalsIgnoreCase("no") ||
            nextLine.equalsIgnoreCase("not") ||
                nextLine.equalsIgnoreCase("n")) {
            return false;
        }
        return true;
    }

    /** End methods remote view **/

    private int nextInt() {
        int nextInt = input.nextInt();
        input.nextLine();

        return nextInt;
    }

    private int nextInt(int size) {

        if(size <= 0)
            return -1;

        int nextInt;
        String inputString;
        do {
            try {
                inputString = input.nextLine();
                nextInt = Integer.parseInt(inputString);
                //input.nextLine();
            } catch(Exception e) {
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

    public void showWeaponOnSpawn() {
        List<WeaponCardClient> weaponCardClients;
        for (SpawnBoardSquareClient spawn : localModel.getListSpawn()) {
            weaponCardClients = spawn.getWeaponCards();
            if(weaponCardClients != null) {
                List<String[][]> weaponMatrix = new ArrayList<>();
                showMessage("in " + spawn +" there are the following weapons");
                for(WeaponCardClient weapon : weaponCardClients){
                    weaponMatrix.add(weapon.drawCard());
                }
                showMessage(CLIUtils.alignSquare(weaponMatrix));
            }

        }
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

    /*@Override
    public void updateWeaponOnMap(WeaponCardClient weaponCardClient, Coordinates coordinates) {
        showMessage("You have collected this Weapon card");
        showMessage(weaponCardClient);
        if(localModel.getGameBoard().getBoardSquareByCoordinates(coordinates).addWeaponCard(weaponCardClient)) {
            showMessage(weaponCardClient);
            showMessage("is drawed on coordinates: " + coordinates);
        } else
            showMessage("ops, something didn't work");

    }*/

    //todo implement

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
    public void run() {

    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }
}