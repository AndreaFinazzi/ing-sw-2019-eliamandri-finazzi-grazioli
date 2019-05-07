package it.polimi.se.eliafinazzigrazioli.adrenaline.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

public interface EventListenerInterface {

    default void handleEvent(AbstractEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(CardSelectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(CollectPlayEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(EffectSelectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(MovePlayEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(PowerUpPlayEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(ReloadWeaponEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(TargetSelectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(WeaponSelectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(PlayerConnectedEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    // Exclusively for Model-generated events
    default String getPlayer() throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }
}