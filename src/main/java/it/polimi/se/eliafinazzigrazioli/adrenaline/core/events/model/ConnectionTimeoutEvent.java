package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

/**
 * The type Connection timeout event.
 */
public class ConnectionTimeoutEvent extends AbstractModelEvent {
    private String message;

    /**
     * Instantiates a new Connection timeout event.
     *
     * @param player the player
     */
    public ConnectionTimeoutEvent(String player) {
        super(player);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
