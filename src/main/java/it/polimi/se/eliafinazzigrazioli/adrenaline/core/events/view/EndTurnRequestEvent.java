package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

/**
 * The type End turn request event.
 */
public class EndTurnRequestEvent extends AbstractViewEvent {

    /**
     * Instantiates a new End turn request event.
     *
     * @param clientID the client id
     * @param player the player
     */
    public EndTurnRequestEvent(int clientID, String player) {
        super(clientID, player);
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
