package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.GenericEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public interface ViewEventsListenerInterface extends EventListenerInterface {

    default void handleEvent(CardSelectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(CollectPlayEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(EffectSelectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(GenericEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(MovePlayEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(PlayerConnectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(PlayersSelectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(PowerUpPlayEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(ReloadWeaponEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(RoomSelectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(SquareSelectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(TargetSelectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(WeaponToUseSelectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

}
