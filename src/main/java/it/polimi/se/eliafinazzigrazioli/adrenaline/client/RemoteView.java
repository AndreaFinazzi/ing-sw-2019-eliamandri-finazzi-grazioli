package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observable;

import java.util.ArrayList;
import java.util.List;

public interface RemoteView extends ModelEventsListenerInterface, Observable, Runnable {

//    void showPlayerSelection();

    @Override
    default void handleEvent(LoginResponseEvent event) throws HandlerNotImplementedException {
        System.out.println(event.getMessage());
        if (!event.isSuccessful()) {
            login();
        }
    }

    @Override
    default void handleEvent(AbstractModelEvent event) throws HandlerNotImplementedException {

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
        choseAction();
//        showBeginTurn(event);
//        showPlayerSelection();
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
        login();
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
    @Override
    default void handleEvent(PlayerUpdateEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

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
        notifyObservers(new SpawnPowerUpSelected(getPlayer(), toKeep, cards.get(0)));
    }

    //OUTGOING communications
    default void notifyPlayerSelectedEvent(ArrayList<String> selectedPlayers) {
        try {
            notifyObservers(new PlayersSelectedEvent(getPlayer(), selectedPlayers));
        } catch (HandlerNotImplementedException e) {
            e.printStackTrace();
        }
    }

    default void notifyCardSelected(String card) {
        try {
            notifyObservers(new CardSelectedEvent(getPlayer(), card));
        } catch(HandlerNotImplementedException e) {
            e.printStackTrace();
        }
    }


    default void notifyLoginRequestEvent(String nickname) {
        notifyObservers(new LoginRequestEvent(getClientID(), nickname));
    }

    default void notifySelectedSquare(Coordinates coordinates) {
        try {
            notifyObservers(new SquareSelectedEvent(getPlayer(), coordinates));
        } catch(HandlerNotImplementedException e) {
            e.printStackTrace();
        }
    }

    default void notifySelectedEffects(String effect) {
        try {
            notifyObservers(new EffectSelectedEvent(getPlayer(), effect));
        } catch(HandlerNotImplementedException e) {
            e.printStackTrace();
        }
    }

    default void notifySelectedWeaponCard(String weapon) {
        try {
            notifyObservers(new CardSelectedEvent(getPlayer(), weapon));
        }catch(HandlerNotImplementedException e) {
            e.printStackTrace();
        }
    }

    default void notifyRequestMove(int clientID, String playerName) {
            notifyObservers(new RequestMovePlayEvent(clientID, playerName));
    }

    void updateWeaponOnMap(WeaponCardClient weaponCardClient, Coordinates coordinates);

    void buildLocalMap(MapType mapType);

    void showMap();

    void choseAction();

    void updatePlayerPosition(String nickname, Coordinates coordinates);

    void selectWeaponCard();

    void collectWeapon(String collectedWeapon, String dropOfWeapon);

    void showBeginTurn(BeginTurnEvent event);

    void showSelectableSquare(List<Coordinates> selectable);

    void selectSelectableSquare(List<Coordinates> selectable);

    void selectSelectableEffect(List<String> callableEffects);

    void showPlayerMovement(String playerName, List<Coordinates> path);

    void login();

    void showMessage(String message);

    PowerUpCard selectPowerUpToKeep(List<PowerUpCard> cards);


}
