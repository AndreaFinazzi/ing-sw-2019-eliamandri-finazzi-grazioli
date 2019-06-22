package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.ActionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.NotAllowedPlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.ReloadWeaponsRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.SpawnSelectionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.AmmoCollectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.BeginMatchEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.PlayerMovementEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.PlayerSpawnedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observable;

import java.util.*;

public interface RemoteView extends ModelEventsListenerInterface, Observable {

    LocalModel getLocalModel();

    Client getClient();

    @Override
    default void handleEvent(ConnectionResponseEvent event) throws HandlerNotImplementedException {
        getClient().setClientID(event.getClientID());

        showMessage(event.getMessage());
        showLogin(event.getAvailableAvatars());
    }

    @Override
    default void handleEvent(LoginResponseEvent event) throws HandlerNotImplementedException {
        showMessage(event.getMessage());
        if (!event.isSuccessful()) {
            showLogin(event.getAvailableAvatars());
        } else {
            updatePlayerInfo(event.getPlayer());
            showLoginSuccessful();
        }
    }

    @Override
    default void handleEvent(MatchStartedEvent event) throws HandlerNotImplementedException {
        updateMatchPlayers(event.getPlayerToAvatarMap());
        List<String> players = new ArrayList<>(event.getPlayerToAvatarMap().keySet());
        initPlayersBoard(players);
        showMapVote(event.getAvailableMaps());
    }

    @Override
    default void handleEvent(BeginMatchEvent event) throws HandlerNotImplementedException {
        buildLocalMap(event.getMapType());

        //Placing ammo cards on map
        getLocalModel().getGameBoard().resetAmmoCards(event.getAmmoCardsSetup());
        showAmmoCardResetting(event.getAmmoCardsSetup());

        //Placing weapon cards on spawn points
        getLocalModel().getGameBoard().resetWeapons(event.getWeaponCardsSetup());
        showWeaponCardResetting(event.getWeaponCardsSetup());


        //todo to complete with info about the match and relative visualization
        showBeginMatch();
    }

    @Override
    default void handleEvent(BeginTurnEvent event) throws HandlerNotImplementedException {
        showBeginTurn(event.getPlayer());
    }

    @Override
    default void handleEvent(SpawnSelectionRequestEvent event) throws HandlerNotImplementedException {
        List<PowerUpCardClient> cards = new ArrayList<>(event.getSelectableCards());
        PowerUpCardClient toKeep = selectPowerUpToKeep(cards);
        cards.remove(toKeep);
        PowerUpCardClient spawnCard = cards.get(0);
        notifyObservers(new SpawnPowerUpSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), toKeep, spawnCard));
    }

    @Override
    default void handleEvent(PlayerSpawnedEvent event) throws HandlerNotImplementedException {
        LocalModel localModel = getLocalModel();
        String player = event.getPlayer();
        localModel.getGameBoard().setPlayerPosition(player, event.getSpawnPoint());
        showSpawn(player, event.getSpawnPoint(), event.getDiscardedPowerUp(), !player.equals(getClient().getPlayerName()));
    }

    @Override
    default void handleEvent(ActionRequestEvent event) throws HandlerNotImplementedException {
        if (event.getPlayer().equals(getClient().getPlayerName())) {
            AbstractViewEvent generatedEvent = null;
            while (generatedEvent == null) {
                PlayerAction choice = choseAction();
                List<Coordinates> path;
                switch(choice) {
                    case MOVE:
                        path = getPathFromUser(event.getSimpleMovesMax());
                        generatedEvent = path.isEmpty() ? null : new MovePlayEvent(getClient().getClientID(), getClient().getPlayerName(), path);
                        break;

                    case SHOOT:
                        List<WeaponCardClient> loadedWeapons  = new ArrayList<>();
                        path = getPathFromUser(event.getSimpleMovesMax());
                        for (WeaponCardClient weaponCardClient: getLocalModel().getWeaponCards()) {
                            if (weaponCardClient.isLoaded())
                                loadedWeapons.add(weaponCardClient);
                        }
                        WeaponCardClient shootingWeapon = selectWeaponCardFromHand(loadedWeapons);
                        if (shootingWeapon != null)
                            generatedEvent = new WeaponToUseSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), shootingWeapon.getWeaponName(), path);

                        break;

                    case COLLECT:
                        path = getPathFromUser(event.getCollectingMovesMax());
                        GameBoardClient gameBoard = getLocalModel().getGameBoard();
                        BoardSquareClient finalPosition = path.isEmpty() ?
                                gameBoard.getPlayerPositionByName(getClient().getPlayerName()) : gameBoard.getBoardSquareByCoordinates(path.get(path.size() - 1));
                        if (finalPosition.isSpawnBoard()) {
                            List<WeaponCardClient> collectibleWeapons = calculatePayableWeapons(finalPosition.getWeaponCards(), getLocalModel(), false);

                            WeaponCardClient selectedWeapon = selectWeaponCardFromSpawnSquare(collectibleWeapons);  /** SELECTION HERE */

                            if (selectedWeapon != null) { //Weapon has been selected
                                //Selection of the power ups the user wants to pay with
                                List<PowerUpCardClient> powerUpsSelected = getPowerUpsToPay(selectedWeapon.getLoader());

                                //Confirmation of the feasibility of the payment, if the payment isn't feasible generated event remains null and the procedure is repeated
                                if (getLocalModel().canPay(selectedWeapon.getLoader(), powerUpsSelected)) {
                                    if (getLocalModel().isWeaponHandFull()) {
                                        showMessage("You have too may weapons, drop one:");
                                        WeaponCardClient weaponCardToDiscard = selectWeaponCardFromHand(getLocalModel().getWeaponCards());    /**SELECTION HERE*/
                                        if (weaponCardToDiscard != null)
                                            generatedEvent = new WeaponCollectionEvent(
                                                    getClient().getClientID(),
                                                    getClient().getPlayerName(),
                                                    path == null ? new ArrayList<>() : path,
                                                    selectedWeapon,
                                                    weaponCardToDiscard,
                                                    powerUpsSelected);
                                    }
                                    else
                                        generatedEvent = new WeaponCollectionEvent(
                                                getClient().getClientID(),
                                                getClient().getPlayerName(),
                                                path == null ? new ArrayList<>() : path,
                                                selectedWeapon,
                                                null,
                                                powerUpsSelected);
                                }
                            }
                            else
                                showMessage("Looks like you changed your mind... or maybe you're just too poor.");
                        }
                        else if (finalPosition.hasAmmoCard()) {
                            generatedEvent = new CollectPlayEvent(getClient().getClientID(), getClient().getPlayerName(), path == null ? new ArrayList<>() : path);
                        }
                        else {
                            generatedEvent = null;
                        }
                        break;
                }
                if (generatedEvent == null)
                    showMessage("This action can't be performed... choose again.");
            }
            notifyObservers(generatedEvent);
            }
    }

    @Override
    default void handleEvent(WeaponCollectedEvent event) throws HandlerNotImplementedException {
        //TODO add card position settings
        LocalModel localModel = getLocalModel();
        SpawnBoardSquareClient collectionSpawnPoint =(SpawnBoardSquareClient) getLocalModel().getGameBoard().getBoardSquareByCoordinates(event.getCollectionSpawnPoint());
        WeaponCardClient weaponCard = collectionSpawnPoint.remove(event.getCollectedWeapon());
        WeaponCardClient droppedWeapon = null;

        List<PowerUpCardClient> powerUpsActuallySpent = new ArrayList<>();


        if (event.getPlayer().equals(getClient().getPlayerName())) {
            for (PowerUpCardClient powerUpCardClient: localModel.getPowerUpCards())
                if (event.getPowerUpsSpent().contains(powerUpCardClient)) {
                    powerUpsActuallySpent.add(powerUpCardClient);
                }

            for (PowerUpCardClient powerUpCardClient: powerUpsActuallySpent)
                localModel.removePowerUp(powerUpCardClient);

            for (Ammo ammo: event.getAmmosSpent())
                localModel.removeAmmo(ammo);

            if (event.getDropOffWeapon() != null) {
                droppedWeapon =  localModel.removeWeapon(event.getDropOffWeapon());
                collectionSpawnPoint.addWeapon(droppedWeapon);
            }

            localModel.addWeapon(weaponCard);
            if (event.isHandFull())
                localModel.setWeaponHandFull();
        }
        else {
            String player = event.getPlayer();
            for (PowerUpCardClient powerUpCardClient: localModel.getPowerUpCards())
                localModel.getOpponentInfo(player).removePowerUp();

            for (Ammo ammo: event.getAmmosSpent())
                localModel.getOpponentInfo(player).removeAmmo(ammo);

            if (event.getDropOffWeapon() != null) {
                droppedWeapon =  localModel.getOpponentInfo(player).removeWeapon(event.getDropOffWeapon());
                collectionSpawnPoint.addWeapon(droppedWeapon);
            }

            localModel.getOpponentInfo(player).addWeapon(weaponCard);
        }

        showWeaponCollectionUpdate(event.getPlayer(), weaponCard, droppedWeapon, collectionSpawnPoint.getRoom());
        showPaymentUpdate(event.getPlayer(), powerUpsActuallySpent, event.getAmmosSpent());
    }

    default void handleEvent(SelectableEffectsEvent event) throws HandlerNotImplementedException {
        showMessage("Do you want to play an effect?[Y=1/n=0]");
        if (selectYesOrNot()) {
            WeaponEffectClient selectedEffect = null;
            List<PowerUpCardClient> powerUpCardsToPay = new ArrayList<>();
            boolean repeatSelection = true;
            boolean usageConfirmation = true;
            while (repeatSelection) {
                WeaponCardClient weaponCard = null;
                List<WeaponEffectClient> toSelect = new ArrayList<>();
                for (WeaponCardClient weaponCardClient: getLocalModel().getWeaponCards()) {
                    if (event.getWeapon().equals(weaponCardClient.getWeaponName()))
                        weaponCard = weaponCardClient;
                }
                for (WeaponEffectClient weaponEffectClient: weaponCard.getEffects()) {
                    if (event.getCallableEffects().contains(weaponEffectClient.getEffectName()))
                        toSelect.add(weaponEffectClient);
                }

                selectedEffect = selectWeaponEffect(weaponCard, toSelect);

                powerUpCardsToPay = getPowerUpsToPay(selectedEffect.getPrice());
                if (!getLocalModel().canPay(selectedEffect.getPrice(), powerUpCardsToPay)) {
                    showMessage("The power ups you selected are not enough to pay, are you sure you want to play this effect?[Y=1/n=0]");
                    usageConfirmation = selectYesOrNot();
                    repeatSelection = usageConfirmation;
                }
                else
                    repeatSelection = false;
            }
            if (usageConfirmation)
                notifyObservers(new EffectSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), selectedEffect.getEffectName(), powerUpCardsToPay));
            else
                notifyObservers(new EffectSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), null, null));

        }
        else
            notifyObservers(new EffectSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), null, null));
    }

    @Override
    default void handleEvent(PlayerMovementEvent event) throws HandlerNotImplementedException {
        GameBoardClient gameBoard = getLocalModel().getGameBoard();
        List<Coordinates> path = event.getPath();
        Coordinates finalPositionCoordinates = path.get(path.size()-1);
        gameBoard.setPlayerPosition(event.getPlayer(), finalPositionCoordinates);
        showPlayerMovement(event.getPlayer(), path);
    }

    @Override
    default void handleEvent(PowerUpCollectedEvent event) throws HandlerNotImplementedException {
        LocalModel localModel = getLocalModel();
        if (event.isActuallyCollected()) {
            if (event.getPlayer().equals(getClient().getPlayerName())) {
                localModel.addPowerUp(event.getCollectedCard());
                showPowerUpCollection(event.getPlayer(), event.getCollectedCard(), false);
            }
            else {
                localModel.getOpponentInfo(event.getPlayer()).addPowerUp();
                showPowerUpCollection(event.getPlayer(), event.getCollectedCard(), true);
            }
        }
    }

    @Override
    default void handleEvent(ReloadWeaponsRequestEvent event) throws HandlerNotImplementedException {
        AbstractViewEvent generatedEvent = null;

        List<WeaponCardClient> weaponsList = getLocalModel().getWeaponCards();
        List<WeaponCardClient> reloadableWeapons;

        while (generatedEvent == null) {

            reloadableWeapons = calculatePayableWeapons(weaponsList, getLocalModel(), true);
            for (WeaponCardClient weaponCardClient: new ArrayList<>(reloadableWeapons)) {
                if (weaponCardClient.isLoaded())
                    reloadableWeapons.remove(weaponCardClient);
            }

            WeaponCardClient selectedWeapon = selectWeaponToReload(reloadableWeapons);  /** SELECTION HERE */

            //todo define payment logic

            if (selectedWeapon != null) { //Weapon has been selected
                //Selection of the power ups the user wants to pay with
                List<PowerUpCardClient> powerUpsSelected = getPowerUpsToPay(selectedWeapon.getPrice());

                //Confirmation of the feasibility of the payment, if the payment isn't feasible generated event remains null and the procedure is repeated
                if (getLocalModel().canPay(selectedWeapon.getPrice(), powerUpsSelected))
                    generatedEvent = new ReloadWeaponEvent(getClient().getClientID(), getClient().getPlayerName(), selectedWeapon, powerUpsSelected);
            }
            else
                generatedEvent = new ReloadWeaponEvent(getClient().getClientID(), getClient().getPlayerName(), null, null);
        }

        notifyObservers(generatedEvent);
    }

    @Override
    default void handleEvent(AmmoCollectedEvent event) throws HandlerNotImplementedException {
        if (event.isActuallyCollected())
            if (event.getPlayer().equals(getClient().getPlayerName()))
                getLocalModel().addAmmo(event.getAmmoCollected());
            else
                getLocalModel().getOpponentInfo(event.getPlayer()).addAmmo(event.getAmmoCollected());
        showAmmoCollected(event.getPlayer(), event.getAmmoCollected(), event.isActuallyCollected());
    }
    @Override
    default void handleEvent(EndTurnEvent event) throws HandlerNotImplementedException {
        getLocalModel().getGameBoard().resetAmmoCards(event.getAmmoCardsReplaced());
        showAmmoCardResetting(event.getAmmoCardsReplaced());

        getLocalModel().getGameBoard().resetWeapons(event.getWeaponCardsReplaced());
        showWeaponCardResetting(event.getWeaponCardsReplaced());

        showEndTurn(event.getPlayer());
    }

    @Override
    default void handleEvent(AbstractModelEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    //TODO to implement

    @Override
    default void handleEvent(AllowedMovesEvent event) throws HandlerNotImplementedException {

        throw new HandlerNotImplementedException();
    }

    //TODO to implement

    @Override
    default void handleEvent(AmmoCardCollectedEvent event) throws HandlerNotImplementedException {
        GameBoardClient gameBoard = getLocalModel().getGameBoard();

        gameBoard.getBoardSquareByCoordinates(event.getBoardSquare()).removeAmmoCard();
        showAmmoCardCollected(event.getPlayer(), event.getAmmoCardCollected(), event.getBoardSquare());
    }
    //TODO to implement

    @Override
    default void handleEvent(ConnectionTimeoutEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }
    //TODO to implement

    //TODO to implement
    @Override
    default void handleEvent(FinalFrenzyBeginEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    //TODO to implement

    @Override
    default void handleEvent(NotAllowedPlayEvent event) throws HandlerNotImplementedException {
        showMessage(event.getMessage());
        showMessage("ACTION FAILED!");

    }
    //TODO to implement

    @Override
    default void handleEvent(PlayerDamagedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    //TODO to implement

    @Override
    default void handleEvent(PlayerShotEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }
    //TODO to implement
    //TODO to implement

    @Override
    default void handleEvent(SelectableBoardSquaresEvent event) throws HandlerNotImplementedException {
        selectSelectableSquare(event.getSelectableBoardSquares());
    }
    //TODO to implement

    @Override
    default void handleEvent(SelectablePlayersEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }
    //TODO to implement

    @Override
    default void handleEvent(SelectableRoomsEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }
    //TODO to implement

    @Override
    default void handleEvent(SelectableTargetEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }
    //TODO to implement

    @Override
    default void handleEvent(SuddenDeathEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(SelectedMapEvent event) throws HandlerNotImplementedException {
        buildLocalMap(event.getMapType());
        showMap();
    }


    @Override
    default void handleEvent(PlayerUpdateEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    void error(Exception e);

    void showLogin(ArrayList<Avatar> availableAvatars);

    void showLoginSuccessful();

    void showMapVote(ArrayList<MapType> availableMaps);

    void showBeginMatch();

    //Per quella bellezza unica di Grazioli, questo metodo è stato implementato sulla cli
    default void showWeaponCollectionUpdate(String player, WeaponCardClient collectedCard, WeaponCardClient droppedCard, Room roomColor) {
        String message;
        if (player.equals(getClient().getPlayerName()))
            message = "You ";
        else
            message = "Player " + player;
        message = message + "collected the weapon\n" + collectedCard + "\n";
        if (droppedCard != null)
            message = message + "from " + roomColor + " room and dropped the weapon\n" + droppedCard + "\nback on board";
        showMessage(message);
    }

    default void showPaymentUpdate(String player, List<PowerUpCardClient> powerUpCardClients, List<Ammo> ammos) {
        String message;
        if (player.equals(getClient().getPlayerName()))
            message = "You ";
        else
            message = "Player " + player;
        message = message + "payed the fallowing power ups:";
        for (PowerUpCardClient powerUpCardClient: powerUpCardClients)
            message = message + "\n" + powerUpCardClient;
        message = message + "\nand the fallowing ammos: " + ammos;

        showMessage(message);

    }

    //OUTGOING communications

    default void notifyLoginRequestEvent(String nickname, Avatar avatar) {
        notifyObservers(new LoginRequestEvent(getClient().getClientID(), nickname, avatar));
    }

    default void notifyMapVoteEvent(MapType mapType) {
        notifyObservers(new MapVoteEvent(getClient().getClientID(), getClient().getPlayerName(), mapType));
    }

    default void notifyPlayerSelectedEvent(ArrayList<String> selectedPlayers) {
        notifyObservers(new PlayersSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), selectedPlayers));
    }

    default void notifyCardSelected(String card) {
        notifyObservers(new CardSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), card));
    }

    default void notifySelectedSquare(Coordinates coordinates) {
        notifyObservers(new SquareSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), coordinates));
    }

    default void notifySelectedEffects(String effect) {

    }

    default void notifySelectedWeaponCard(String weapon) {
        notifyObservers(new CardSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), weapon));
    }

    default void notifyRequestMove(int clientID, String playerName) {
        notifyObservers(new RequestMovePlayEvent(clientID, playerName));
    }

    //    Local model

    default void updatePlayerInfo(String player) {
        getClient().setPlayerName(player);
    }

    default void updateMatchPlayers(HashMap<String, Avatar> playerToAvatarMap){
        getLocalModel().setPlayerToAvatarMap(playerToAvatarMap);
    }

    default void initPlayersBoard(List<String> players) {
        players.remove(getClient().getPlayerName());
        for(String player : players) {
            getLocalModel().addOpponent(player);
        }
    }

    // TODO should be removed
    void updateWeaponOnMap(WeaponCardClient weaponCardClient, Coordinates coordinates);

    default void buildLocalMap(MapType mapType) {
        getLocalModel().generatesGameBoard(mapType);
        System.out.println("Map is chosen is: " + mapType);
        showMap();
    }

    void showMap();

    void updatePlayerPosition(String nickname, Coordinates coordinates);

    void selectWeaponCard();

    void showSelectableSquare(List<Coordinates> selectable);

    void selectSelectableSquare(List<Coordinates> selectable);

    void selectSelectableEffect(List<String> callableEffects);



    //SHOW METHODS

    void showMessage(Object message);

    default void showPlayerMovement(String player, List<Coordinates> path) {
        if (path != null && path.size() > 0) {
            if (player.equals(getClient().getPlayerName()))
                showMessage("You moved to square" + getLocalModel().getGameBoard().getBoardSquareByCoordinates(path.get(path.size()-1)) + " through the path: ");
            else
                showMessage("Player " + player + " moved to square" + getLocalModel().getGameBoard().getBoardSquareByCoordinates(path.get(path.size()-1)) + " through the path: ");
            for (Coordinates coordinates: path)
                showMessage(getLocalModel().getGameBoard().getBoardSquareByCoordinates(coordinates));
        }
    }

    default void showSpawn(String player, Coordinates spawnPoint, PowerUpCardClient spawnCard, boolean isOpponent) {
        if (isOpponent)
            showMessage("Player " + player + " spawned on " + spawnPoint.toString() + " square using power up " + spawnCard.toString() + "!\n");
        else
            showMessage("You spawned on " + spawnPoint.toString() + " square using power up " + spawnCard.toString() + "!\n");
    }

    default void showPowerUpCollection(String player, PowerUpCardClient cardCollected, boolean isOpponent) {
        if (isOpponent)
            showMessage("Player " + player + " collected a powerup\n");
        else
            showMessage("You collected \n" + cardCollected.toString() + "!\n");
    }

    default void showBeginTurn(String player) {
        if (player.equals(getClient().getPlayerName())) {
            showMessage("Your turn began!!!");
        }
        else
            showMessage(player + "'s turn began!");
    }

    default void showEndTurn(String player) {
        if (player.equals(getClient().getPlayerName()))
            showMessage("YEAH!! You concluded your turn!");
        else
            showMessage(player + " concluded his turn!");
    }

    default void showAmmoCollected(String player, Ammo ammo, boolean actuallyCollected) {
        if (player.equals(getClient().getPlayerName()))
            showMessage("You collected a " + ammo.toString() + " munition " + (actuallyCollected ? "!" : " but your playerBoard was full!"));
        else
            showMessage(player + " collected a " + ammo.toString() + " munition " + (actuallyCollected ? "!" : " but his playerBoard was full!"));
    }

    default void showAmmoCardCollected(String player, AmmoCardClient ammoCard, Coordinates coordinates) {
        String message = " gathered the ammo card " + ammoCard.toString() + " from square " + coordinates + "!";
        if (player.equals(getClient().getPlayerName()))
            showMessage("You " + message);
        else
            showMessage(player + message);
    }

    default void showAmmoCardResetting(Map<Coordinates, AmmoCardClient> coordinatesAmmoCardMap) {
        showMessage("Gathered ammo cards are being replaced...");
    }

    default void showWeaponCardResetting(Map<Coordinates, List<WeaponCardClient>> coordinatesWeaponsMap) {
        showMessage("Weapons are being placed on the spawn points...");
    }



    //INTERACTION (INPUT) METHODS   NB: THEY MUST NOT BE VOID FUNCTIONS

    MoveDirection getMoveFromUser(BoardSquareClient currentPose, ArrayList<MoveDirection> availableMoves);

    PlayerAction choseAction();

    PowerUpCardClient selectPowerUpToKeep(List<PowerUpCardClient> cards);

    PowerUpCardClient selectPowerUp(List<PowerUpCardClient> cards);

    /** DEVE DARE LA POSSIBILITA DI NON RICARICARE ALCUNA ARMA E RITONARE NULL IN QUEL CASO, OPPURE AVVISARE CHE NESSUNA ARMA PUO ESSERE RICARICATA */





    default WeaponEffectClient selectWeaponEffect(WeaponCardClient weapon, List<WeaponEffectClient> callableEffects) {
        int count = 0;
        showMessage("Select an effect to activate: ");
        for (WeaponEffectClient weaponEffectClient: callableEffects) {
            count++;
            showMessage(count + ") " + weaponEffectClient.getEffectName());
        }

        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            showMessage("enter:");
            choice = scanner.nextInt();
        } while (choice < 1 || count > callableEffects.size());
        return callableEffects.get(choice - 1);
    }

    default boolean selectYesOrNot() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        choice = scanner.nextInt();
        return choice == 1;
    }






    //Composite selection methods

    default List<Coordinates> getPathFromUser(int maxSteps) {
        BoardSquareClient currentPose = getLocalModel().getGameBoard().getPlayerPositionByName(getClient().getPlayerName());
        List<Coordinates> path = new ArrayList<>();
        ArrayList<MoveDirection> availableMoves = new ArrayList<>();

        MoveDirection choice;

        do {
            availableMoves.clear();
            if (currentPose == null) {
                return null;
            }

            if (currentPose.getNorth().equals(InterSquareLink.SAMEROOM) || currentPose.getNorth().equals(InterSquareLink.DOOR)) {
                availableMoves.add(MoveDirection.UP);
            }
            if (currentPose.getEast().equals(InterSquareLink.SAMEROOM) || currentPose.getEast().equals(InterSquareLink.DOOR)) {
                availableMoves.add(MoveDirection.RIGHT);
            }
            if (currentPose.getSouth().equals(InterSquareLink.SAMEROOM) || currentPose.getSouth().equals(InterSquareLink.DOOR)) {
                availableMoves.add(MoveDirection.DOWN);
            }
            if (currentPose.getWest().equals(InterSquareLink.SAMEROOM) || currentPose.getWest().equals(InterSquareLink.DOOR)) {
                availableMoves.add(MoveDirection.LEFT);
            }
            availableMoves.add(MoveDirection.STOP);

            choice = getMoveFromUser(currentPose, availableMoves);

            currentPose = getLocalModel().getGameBoard().getBoardSquareByCoordinates(calculateCoordinates(currentPose.getCoordinates(), choice));

            if (choice != MoveDirection.STOP)
                path.add(currentPose.getCoordinates());

        } while (path.size() < maxSteps && choice != MoveDirection.STOP);

        showMessage("Path successfully generated.");

        return path;
    }

    default List<PowerUpCardClient> getPowerUpsToPay(List<Ammo> price) {
        List<PowerUpCardClient> powerUpsSelected = new ArrayList<>();
        PowerUpCardClient powerUpCardUsedToPay;
        List<PowerUpCardClient> spendablePowerUps;


        spendablePowerUps = calculateSpendablePowerUps(price, getLocalModel().getPowerUpCards());
        do {
            if (!spendablePowerUps.isEmpty()) {
                showMessage("Select a power up you want to use to pay:");
                powerUpCardUsedToPay = selectPowerUp(spendablePowerUps);   /** SELECTION HERE */
            }
            else
                powerUpCardUsedToPay = null;

            if (powerUpCardUsedToPay != null) {
                powerUpsSelected.add(powerUpCardUsedToPay);
                price.remove(powerUpCardUsedToPay.getEquivalentAmmo());
                spendablePowerUps = calculateSpendablePowerUps(price, spendablePowerUps);
            }
        } while (powerUpCardUsedToPay != null);
        return powerUpsSelected;
    }





        /** DEVE DARE LA POSSIBILITA DI NON RICARICARE ALCUNA ARMA E RITONARE NULL IN QUEL CASO, OPPURE AVVISARE CHE NESSUNA ARMA PUO ESSERE RICARICATA */
        default WeaponCardClient selectWeaponToReload(List<WeaponCardClient> reloadableWeapons) {
        if (reloadableWeapons.isEmpty()) {
            showMessage("No weapons can be reloaded.");
            return null;
        }
        else {
            showMessage("Select a weapon to collect:");
            int count = 0;
            for (WeaponCardClient weaponCardClient: reloadableWeapons) {
                count++;
                showMessage(count + ") " + weaponCardClient);
            }
            int choice = 0;
            Scanner scanner = new Scanner(System.in);
            do {
                showMessage("enter:");
                choice = scanner.nextInt();
            } while (choice < 1 || count > reloadableWeapons.size());
            return reloadableWeapons.get(choice - 1);
        }
    }

    default WeaponCardClient selectWeaponCardFromHand(List<WeaponCardClient> selectableWeapons) {
        int count = 0;
        for (WeaponCardClient weaponCardClient : selectableWeapons) {
            count++;
            showMessage(count + ") " + weaponCardClient);
        }
        showMessage((count + 1) + ") Select nothing");
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        do {
            showMessage("enter:");
            choice = scanner.nextInt();
        } while (choice < 1 || count > selectableWeapons.size() + 1);
        if (choice != count+1)
            return selectableWeapons.get(choice - 1);
        else
            return null;
    }

    default WeaponCardClient selectWeaponCardFromSpawnSquare(List<WeaponCardClient> selectableWeapons) {
        if (selectableWeapons.isEmpty()) {
            showMessage("No weapons to select.");
            return null;
        }
        else {
            showMessage("Select a weapon from the map:");
            int count = 0;
            for (WeaponCardClient weaponCardClient : selectableWeapons) {
                count++;
                showMessage(count + ") " + weaponCardClient);
            }
            int choice = 0;
            Scanner scanner = new Scanner(System.in);
            do {
                showMessage("enter:");
                choice = scanner.nextInt();
            } while (choice < 1 || count > selectableWeapons.size());
            return selectableWeapons.get(choice - 1);
        }
    }

    static Coordinates calculateCoordinates(Coordinates initialCoordinates, MoveDirection moveDirection) {
        return new Coordinates(initialCoordinates.getXCoordinate() + moveDirection.getDeltaX(), initialCoordinates.getYCoordinate() + moveDirection.getDeltaY());
    }

    static List<PowerUpCardClient> calculateSpendablePowerUps(List<Ammo> price, List<PowerUpCardClient> powerUpCards){
        List<Ammo> priceCopy = new ArrayList<>(price);
        List<PowerUpCardClient> spendablePowerUps = new ArrayList<>();
        for (PowerUpCardClient powerUpCardClient: powerUpCards) {
            if (priceCopy.contains(powerUpCardClient.getEquivalentAmmo())) {
                priceCopy.remove(powerUpCardClient.getEquivalentAmmo());
                spendablePowerUps.add(powerUpCardClient);
            }
        }
        return spendablePowerUps;
    }

    static List<WeaponCardClient> calculatePayableWeapons(List<WeaponCardClient> weaponsList, LocalModel localModel, boolean toReload) {
        List<WeaponCardClient> payableWeapons = new ArrayList<>();
        for (WeaponCardClient weaponCard: weaponsList) {
            List<Ammo> totalPrice = new ArrayList<>(toReload ? weaponCard.getPrice() : weaponCard.getLoader());
            if (localModel.canPay(totalPrice)) {
                payableWeapons.add(weaponCard);
            }
        }
        return payableWeapons;
    }



}
