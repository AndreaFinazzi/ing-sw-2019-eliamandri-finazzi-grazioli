package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.NotAllowedPlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.SpawnSelectionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.PlayerMovementEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface RemoteView extends ModelEventsListenerInterface, Observable, Runnable {

    @Override
    default void handleEvent(LoginResponseEvent event) throws HandlerNotImplementedException {
        System.out.println(event.getMessage());
        if (!event.isSuccessful()) {
            login(event.getAvailableAvatars());
        } else {
            updatePlayerInfo(event.getPlayer());
        }
    }

    @Override
    default void handleEvent(MatchStartedEvent event) throws HandlerNotImplementedException {
        updateMatchPlayers(event.getPlayerToAvatarMap());

        mapVote(event.getAvailableMaps());
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
    default void handleEvent(BeginTurnEvent event) throws HandlerNotImplementedException {
        if (event.getPlayer().equals(getPlayer()))
            choseAction();
        else
            showBeginTurn(event.getPlayer());
    }

    @Override
    default void handleEvent(WeaponCardDrawedEvent event) throws HandlerNotImplementedException {
        WeaponCardClient weaponCardClient = new WeaponCardClient(event.getWeaponName(), event.getEffectsDescription());
        updateWeaponOnMap(weaponCardClient, event.getCoordinates());
    }

    @Override
    default void handleEvent(CardReloadedEvent event) throws HandlerNotImplementedException {

    }

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

    @Override
    default void handleEvent(EndTurnEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }
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
    default void handleEvent(PlayerMovementEvent event) throws HandlerNotImplementedException {
        int last = event.getPath().size() - 1;
        Coordinates finalPosition = event.getPath().get(last);
        updatePlayerPosition(event.getPlayer(), finalPosition);
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

    default void handleEvent(SpawnSelectionRequestEvent event) throws HandlerNotImplementedException {
        List<PowerUpCard> cards = event.getSelectableCards();
        PowerUpCard toKeep = selectPowerUpToKeep(cards);
        cards.remove(toKeep);
        PowerUpCard spawnCard = cards.get(0);
        notifyObservers(new SpawnPowerUpSelected(getPlayer(), toKeep, spawnCard));
    }


    @Override
    default void handleEvent(PlayerUpdateEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    void error(Exception e);

    String getPlayer();

    int getClientID();

    void login(ArrayList<Avatar> availableAvatars);

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
    void updatePlayerInfo(String player);

    void updateMatchPlayers(HashMap<String, Avatar> playerToAvatarMap);

    void updateWeaponOnMap(WeaponCardClient weaponCardClient, Coordinates coordinates);

    void buildLocalMap(MapType mapType);

    void showMap();

    void choseAction();

    void updatePlayerPosition(String nickname, Coordinates coordinates);

    void selectWeaponCard();

    void collectWeapon(String collectedWeapon, String dropOfWeapon);

    void showBeginTurn(String currentPlayer);

    void showSelectableSquare(List<Coordinates> selectable);

    void selectSelectableSquare(List<Coordinates> selectable);

    void selectSelectableEffect(List<String> callableEffects);

    void showPlayerMovement(String playerName, List<Coordinates> path);

    void showMessage(String message);

    PowerUpCard selectPowerUpToKeep(List<PowerUpCard> cards);


}
