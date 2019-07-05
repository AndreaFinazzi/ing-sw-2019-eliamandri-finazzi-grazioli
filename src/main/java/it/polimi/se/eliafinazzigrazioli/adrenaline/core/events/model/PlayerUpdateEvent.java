package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

/**
 * The type Player update event.
 */
public class PlayerUpdateEvent extends AbstractModelEvent {

    /**
     * Instantiates a new Player update event.
     *
     * @param player the player
     */
    public PlayerUpdateEvent(String player) {
        super(player);
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
