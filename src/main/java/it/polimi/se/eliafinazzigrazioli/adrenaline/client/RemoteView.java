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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        //todo to complete with info about the match and relative visualization
        showBeginMatch();
    }

    @Override
    default void handleEvent(BeginTurnEvent event) throws HandlerNotImplementedException {
        showBeginTurn(event.getPlayer());
    }

    @Override
    default void handleEvent(SpawnSelectionRequestEvent event) throws HandlerNotImplementedException {
        List<PowerUpCard> cards = new ArrayList<>(event.getSelectableCards());
        PowerUpCard toKeep = selectPowerUpToKeep(cards);
        cards.remove(toKeep);
        PowerUpCard spawnCard = cards.get(0);
        notifyObservers(new SpawnPowerUpSelected(getClient().getClientID(), getClient().getPlayerName(), toKeep, spawnCard));
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
                        generatedEvent = path == null ? null : new MovePlayEvent(getClient().getClientID(), getClient().getPlayerName(), path);
                        break;

                    case SHOOT:
                        //todo shooting logic
                        break;

                    case COLLECT:
                        path = getPathFromUser(event.getCollectingMovesMax());
                        GameBoardClient gameBoard = getLocalModel().getGameBoard();
                        BoardSquareClient finalPosition = path == null ?
                                gameBoard.getPlayerPositionByName(getClient().getPlayerName()) : gameBoard.getBoardSquareByCoordinates(path.get(path.size() - 1));
                        if (finalPosition.isSpawnBoard()) {
                            //todo weapon to collect procedure
                            generatedEvent = null;
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
                    System.out.println("This action can't be performed... choose again.");
            }
            notifyObservers(generatedEvent);
            }
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
        if (event.getPlayer().equals(getClient().getPlayerName())) {
            localModel.addPowerUp(event.getCollectedCard());
            showPowerUpCollection(event.getPlayer(), event.getCollectedCard(), false);
        }
        else {
            //todo add to local model info about other players' hand, here it's goinna be powerUps++
            showPowerUpCollection(event.getPlayer(), event.getCollectedCard(), true);
        }
    }

    @Override
    default void handleEvent(ReloadWeaponsRequestEvent event) throws HandlerNotImplementedException {
        List<WeaponCardClient> weaponsList = getLocalModel().getWeaponCards();
        List<WeaponCardClient> reloadableWeapons = new ArrayList<>();

        for (WeaponCardClient weaponCard: weaponsList) {
            List<Ammo> totalPrice = new ArrayList<>(weaponCard.getPrice());
            totalPrice.add(weaponCard.getWeaponColor());
            if (!weaponCard.isLoaded() && canPay(totalPrice))
                reloadableWeapons.add(weaponCard);
        }

        WeaponCardClient selectedWeapon = selectWeaponToReload(reloadableWeapons);

        //todo define payment logic

        notifyObservers(new ReloadWeaponEvent(getClient().getClientID(), getClient().getPlayerName(), selectedWeapon));
    }

    @Override
    default void handleEvent(AmmoCollectedEvent event) throws HandlerNotImplementedException {
        if (event.isActuallyCollected())
            getLocalModel().addAmmo(event.getAmmoCollected());
        showAmmoCollected(event.getPlayer(), event.getAmmoCollected(), event.isActuallyCollected());
    }

    @Override
    default void handleEvent(EndTurnEvent event) throws HandlerNotImplementedException {
        getLocalModel().getGameBoard().resetAmmoCards();
        showAmmoCardResetting();
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
        System.out.println("ACTION FAILED!");

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


    //TODO to implement
    @Override
    default void handleEvent(WeaponCollectedEvent event) throws HandlerNotImplementedException {
        collectWeapon(event.getCollectedWeapon(), event.getDropOfWeapon());
    }

    default void handleEvent(SelectableEffectsEvent event) throws HandlerNotImplementedException {
        selectSelectableEffect(event.getCallableEffects());
    }

    default void handleEvent(SelectedMapEvent event) throws HandlerNotImplementedException {
        buildLocalMap(event.getMapType());

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
        notifyObservers(new EffectSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), effect));
    }

    default void notifySelectedWeaponCard(String weapon) {
        notifyObservers(new CardSelectedEvent(getClient().getClientID(), getClient().getPlayerName(), weapon));
    }

    default void notifyRequestMove(int clientID, String playerName) {
        notifyObservers(new RequestMovePlayEvent(clientID, playerName));
    }

    //    Local model



    default boolean canPay(List<Ammo> price) {
        List<Ammo> priceCopy = new ArrayList<>(price);
        LocalModel localModel = getLocalModel();

        for (Ammo ammo: localModel.getAmmos()) {
            if (priceCopy.contains(ammo))
                priceCopy.remove(ammo);
        }
        for (PowerUpCardClient powerUpCard: localModel.getPowerUpCards()) {
            if (priceCopy.contains(powerUpCard.getEquivalentAmmo()))
                priceCopy.remove(powerUpCard.getEquivalentAmmo());
        }
        if (priceCopy.size() == 0)
            return true;
        else
            return false;
    }

    default void updatePlayerInfo(String player) {
        getClient().setPlayerName(player);
    }

    default void updateMatchPlayers(HashMap<String, Avatar> playerToAvatarMap){
        getLocalModel().setPlayersAvatarMap(playerToAvatarMap);
    }

    default void initPlayersBoard(List<String> players) {
        for(String player : players) {
            getLocalModel().getPlayerBoards().put(player, new PlayerBoard());
        }
    }

    void updateWeaponOnMap(WeaponCardClient weaponCardClient, Coordinates coordinates);

    default void buildLocalMap(MapType mapType) {
        getLocalModel().generatesGameBoard(mapType);
        System.out.println("Map is chosen is: " + mapType);
        //todo showmap();
    }

    ;

    void showMap();

    void updatePlayerPosition(String nickname, Coordinates coordinates);

    void selectWeaponCard();

    void collectWeapon(String collectedWeapon, String dropOfWeapon);

    void showSelectableSquare(List<Coordinates> selectable);

    void selectSelectableSquare(List<Coordinates> selectable);

    void selectSelectableEffect(List<String> callableEffects);

    void showMessage(Object message);



    //SHOW METHODS
    default void showPlayerMovement(String player, List<Coordinates> path) {
        if (path != null && path.size() > 0) {
            if (player.equals(getClient().getPlayerName()))
                System.out.println("You moved to square" + getLocalModel().getGameBoard().getBoardSquareByCoordinates(path.get(path.size()-1)) + " through the path: ");
            else
                System.out.println("Player " + player + " moved to square" + getLocalModel().getGameBoard().getBoardSquareByCoordinates(path.get(path.size()-1)) + " through the path: ");
            for (Coordinates coordinates: path)
                System.out.println(getLocalModel().getGameBoard().getBoardSquareByCoordinates(coordinates));
        }
    }

    default void showSpawn(String player, Coordinates spawnPoint, PowerUpCard spawnCard, boolean isOpponent) {
        if (isOpponent)
            System.out.println("Player " + player + " spawned on " + spawnPoint.toString() + " square using power up " + spawnCard.toString() + "!\n");
        else
            System.out.println("You spawned on " + spawnPoint.toString() + " square using power up " + spawnCard.toString() + "!\n");
    }

    default void showPowerUpCollection(String player, PowerUpCard cardCollected, boolean isOpponent) {
        if (isOpponent)
            System.out.println("Player " + player + " collected a powerup\n");
        else
            System.out.println("You collected " + cardCollected.toString() + "!\n");
    }

    default void showBeginTurn(String player) {
        if (player.equals(getClient().getPlayerName())) {
            System.out.println("Your turn began!!!");
        }
        else
            System.out.println(player + "'s turn began!");
    }

    default void showEndTurn(String player) {
        if (player.equals(getClient().getPlayerName()))
            System.out.println("YEAH!! You concluded your turn!");
        else
            System.out.println(player + " concluded his turn!");
    }

    default void showAmmoCollected(String player, Ammo ammo, boolean actuallyCollected) {
        if (player.equals(getClient().getPlayerName()))
            System.out.println("You collected a " + ammo.toString() + " munition " + (actuallyCollected ? "!" : " but your playerBoard was full!"));
        else
            System.out.println(player + " collected a " + ammo.toString() + " munition " + (actuallyCollected ? "!" : " but his playerBoard was full!"));
    }

    default void showAmmoCardCollected(String player, AmmoCard ammoCard, Coordinates coordinates) {
        String message = " gathered the ammo card " + ammoCard.toString() + " from square " + coordinates + "!";
        if (player.equals(getClient().getPlayerName()))
            System.out.println("You " + message);
        else
            System.out.println(player + message);
    }

    default void showAmmoCardResetting() {
        System.out.println("Gathered ammo cards are being replaced...");
    }


    //INTERACTION (INPUT) METHODS   NB: THEY MUST NOT BE VOID FUNCTIONS

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

        if (choice == MoveDirection.STOP && path.isEmpty())
            return null;
        else
            showMessage("Path successfully generated.");

        return path;
    }

    static Coordinates calculateCoordinates(Coordinates initialCoordinates, MoveDirection moveDirection) {
        return new Coordinates(initialCoordinates.getXCoordinate() + moveDirection.getDeltaX(), initialCoordinates.getYCoordinate() + moveDirection.getDeltaY());
    }

    MoveDirection getMoveFromUser(BoardSquareClient currentPose, ArrayList<MoveDirection> availableMoves);

    PlayerAction choseAction();

    PowerUpCard selectPowerUpToKeep(List<PowerUpCard> cards);


    /** DEVE DARE LA POSSIBILITA DI NON RICARICARE ALCUNA ARMA E RITONARE NULL IN QUEL CASO, OPPURE AVVISARE CHE NESSUNA ARMA PUO ESSERE RICARICATA */
    default WeaponCardClient selectWeaponToReload(List<WeaponCardClient> reloadableWeapons) {
        System.out.println("No weapons can be reloaded.");
        return null;
    }





}
