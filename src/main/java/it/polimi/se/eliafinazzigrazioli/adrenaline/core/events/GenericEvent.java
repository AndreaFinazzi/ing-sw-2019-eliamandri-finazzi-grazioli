package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

/**
 * The type Generic event.
 */
public class GenericEvent extends AbstractModelEvent {

    /**
     * Instantiates a new Generic event.
     *
     * @param player the player
     * @param message the message
     */
    public GenericEvent(String player, String message) {
        super(player, message);
        setMessage(message);
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
