package it.polimi.se.eliafinazzigrazioli.adrenaline.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

public interface EventListenerInterface {

    default void handleEvent(AbstractEvent event, MatchController matchController) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(CardSelectedEvent event, MatchController matchController) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(CollectPlayEvent event, MatchController matchController) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(EffectSelectedEvent event, MatchController matchController) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(MovePlayEvent event, MatchController matchController) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(PowerUpPlayEvent event, MatchController matchController) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(ReloadWeaponEvent event, MatchController matchController) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(TargetSelectedEvent event, MatchController matchController) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(WeaponSelectedEvent event, MatchController matchController) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(PlayerConnectedEvent event, MatchController matchController) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }
}