package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.ActionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.NotAllowedPlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.ReloadWeaponsRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.SpawnSelectionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.BeginMatchEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.PlayerMovementEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.PlayerSpawnedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface RemoteView extends ModelEventsListenerInterface, Observable {

    @Override
    default void handleEvent(LoginResponseEvent event) throws HandlerNotImplementedException {
        showMessage(event.getMessage());
        if (!event.isSuccessful()) {
            login(event.getAvailableAvatars());
        } else {
            updatePlayerInfo(event.getPlayer());
            loginSuccessful();
        }
    }

    @Override
    default void handleEvent(MatchStartedEvent event) throws HandlerNotImplementedException {
        updateMatchPlayers(event.getPlayerToAvatarMap());

        mapVote(event.getAvailableMaps());
    }

    @Override
    default void handleEvent(BeginMatchEvent event) throws HandlerNotImplementedException {
        buildLocalMap(event.getMapType());
        //todo to complete with info about the match and relative visualization
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
        notifyObservers(new SpawnPowerUpSelected(getPlayer(), toKeep, spawnCard));
    }

    @Override
    default void handleEvent(PlayerSpawnedEvent event) throws HandlerNotImplementedException {
        LocalModel localModel = getLocalModel();
        String player = event.getPlayer();
        localModel.getGameBoard().setPlayerPosition(player, event.getSpawnPoint());
        showSpawn(player, event.getSpawnPoint(), event.getDiscardedPowerUp(), !player.equals(getPlayer()));
    }

    @Override
    default void handleEvent(ActionRequestEvent event) throws HandlerNotImplementedException {
        //TODO useless
//        getLocalModel().getGameBoard().setPlayerPosition(getPlayer(), new Coordinates(2, 2));

        if (event.getPlayer().equals(getPlayer())) {
            List<Coordinates> path = new ArrayList<>();
            new Thread(() -> {
                AbstractViewEvent generatedEvent = null;
                while (generatedEvent == null) {
                    int choice = 1;//choseAction();
                    switch(choice) {
                        case 1:
                            for (Coordinates coordinates: getPathFromUser(event.getSimpleMovesMax()))
                                path.add(coordinates);
                            generatedEvent = new MovePlayEvent(getPlayer(), path);
                            break;

                        case 2:
                            //selectWeaponCard();
                            //wait response with selectable effect
                            break;

                        case 3:
                            //todo
                            break;
                    }
                }
                notifyObservers(generatedEvent);
            }).start();
        }
    }

    @Override
    default void handleEvent(PlayerMovementEvent event) throws HandlerNotImplementedException {
        ClientGameBoard gameBoard = getLocalModel().getGameBoard();
        List<Coordinates> path = event.getPath();
        Coordinates finalPositionCoordinates = path.get(path.size()-1);
        gameBoard.setPlayerPosition(event.getPlayer(), finalPositionCoordinates);
        showPlayerMovement(event.getPlayer(), path);
    }

    @Override
    default void handleEvent(PowerUpCollectedEvent event) throws HandlerNotImplementedException {
        LocalModel localModel = getLocalModel();
        if (event.getPlayer().equals(getPlayer())){
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

        notifyObservers(new ReloadWeaponEvent(getPlayer(), selectedWeapon));

    }

    @Override
    default void handleEvent(EndTurnEvent event) throws HandlerNotImplementedException {
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
        throw new HandlerNotImplementedException();
    }

    //TODO to implement

    @Override
    default void handleEvent(ConnectionResponseEvent event) throws HandlerNotImplementedException {
        showMessage(event.getMessage());
        login(event.getAvailableAvatars());
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
        choseAction();

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

    String getPlayer();

    int getClientID();

    void login(ArrayList<Avatar> availableAvatars);

    void loginSuccessful();

    void mapVote(ArrayList<MapType> availableMaps);


    //OUTGOING communications
    default void notifyLoginRequestEvent(String nickname, Avatar avatar) {
        notifyObservers(new LoginRequestEvent(getClientID(), nickname, avatar));
    }

    default void notifyMapVoteEvent(MapType mapType) {
        notifyObservers(new MapVoteEvent(getClientID(), getPlayer(), mapType));
    }

    default void notifyPlayerSelectedEvent(ArrayList<String> selectedPlayers) {
        notifyObservers(new PlayersSelectedEvent(getPlayer(), selectedPlayers));
    }

    default void notifyCardSelected(String card) {
        notifyObservers(new CardSelectedEvent(getPlayer(), card));
    }

    default void notifySelectedSquare(Coordinates coordinates) {
        notifyObservers(new SquareSelectedEvent(getPlayer(), coordinates));
    }

    default void notifySelectedEffects(String effect) {
        notifyObservers(new EffectSelectedEvent(getPlayer(), effect));
    }

    default void notifySelectedWeaponCard(String weapon) {
        notifyObservers(new CardSelectedEvent(getPlayer(), weapon));
    }

    default void notifyRequestMove(int clientID, String playerName) {
        notifyObservers(new RequestMovePlayEvent(clientID, playerName));
    }

    //    Local model

    LocalModel getLocalModel();

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

    void updatePlayerInfo(String player);

    void updateMatchPlayers(HashMap<String, Avatar> playerToAvatarMap);

    void updateWeaponOnMap(WeaponCardClient weaponCardClient, Coordinates coordinates);

    void buildLocalMap(MapType mapType);

    void showMap();

    void updatePlayerPosition(String nickname, Coordinates coordinates);

    void selectWeaponCard();

    void collectWeapon(String collectedWeapon, String dropOfWeapon);

    void showSelectableSquare(List<Coordinates> selectable);

    void selectSelectableSquare(List<Coordinates> selectable);

    void selectSelectableEffect(List<String> callableEffects);

    void showMessage(String message);








    //SHOW METHODS
    default void showPlayerMovement(String player, List<Coordinates> path) {
        if (path != null && path.size() > 0) {
            if (player.equals(getPlayer()))
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
        if (player.equals(getPlayer())) {
            System.out.println("Your turn began!!!");
        }
        else
            System.out.println(player + "'s turn began!");
    }

    default void showEndTurn(String player) {
        if (player.equals(getPlayer()))
            System.out.println("YEAH!! You concluded your turn!");
        else
            System.out.println(player + " concluded his turn!");
    }



    //INTERACTION (INPUT) METHODS   NB: THEY MUST NOT BE VOID FUNCTIONS

    List<Coordinates> getPathFromUser(int maxSteps);

    int choseAction();

    PowerUpCard selectPowerUpToKeep(List<PowerUpCard> cards);


    /** DEVE DARE LA POSSIBILITA DI NON RICARICARE ALCUNA ARMA E RITONARE NULL IN QUEL CASO, OPPURE AVVISARE CHE NESSUNA ARMA PUO ESSERE RICARICATA */
    default WeaponCardClient selectWeaponToReload(List<WeaponCardClient> reloadableWeapons) {
        System.out.println("No weapons can be reloaded.");
        return null;
    }





}
