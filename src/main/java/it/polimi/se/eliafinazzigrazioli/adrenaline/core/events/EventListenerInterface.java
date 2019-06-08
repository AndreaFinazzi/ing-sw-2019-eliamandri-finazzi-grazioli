package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public interface EventListenerInterface {

    default void handleEvent(AbstractEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    default void handleEvent(GenericEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }
}