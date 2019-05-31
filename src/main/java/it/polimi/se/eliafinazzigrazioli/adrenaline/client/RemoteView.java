package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.LoginRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.PlayersSelectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
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
//        showBeginTurn(event);
//        showPlayerSelection();
    }

    //TODO to implement
    @Override
    default void handleEvent(CardDrawedEvent event) throws HandlerNotImplementedException {

        throw new HandlerNotImplementedException();
    }

    @Override
    default void handleEvent(CardReloadedEvent event) throws HandlerNotImplementedException {

    }

    @Override
    default void handleEvent(ConnectionResponseEvent event) throws HandlerNotImplementedException {
        System.out.println(event.getMessage());
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
        throw new HandlerNotImplementedException();
    }

    //TODO to implement
    @Override
    default void handleEvent(PlayerDamagedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    //TODO to implement
    @Override
    default void handleEvent(PlayerMovementEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
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
        showSelectableEvent(event.getSelectableBoardSquares());
        throw new HandlerNotImplementedException();
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
        throw new HandlerNotImplementedException();
    }

    void login();

    //OUTGOING communications
    default void notifyPlayerSelectedEvent(ArrayList<String> selectedPlayers) {
        try {
            notifyObservers(new PlayersSelectedEvent(getPlayer(), selectedPlayers));
        } catch (HandlerNotImplementedException e) {
            e.printStackTrace();
        }
    }


    default void notifyLoginRequestEvent(String nickname) {
        notifyObservers(new LoginRequestEvent(getClientID(), nickname));
    }

    void showBeginTurn(BeginTurnEvent event);

    void showSelectableEvent(List<Coordinates> selectable);
}
