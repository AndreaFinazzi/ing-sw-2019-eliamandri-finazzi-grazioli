package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

/**
 * The interface Event listener interface.
 */
public interface EventListenerInterface {

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(AbstractEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(GenericEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }

    /**
     * Handle event.
     *
     * @param event the event
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    default void handleEvent(SkippedTurnEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException();
    }
}