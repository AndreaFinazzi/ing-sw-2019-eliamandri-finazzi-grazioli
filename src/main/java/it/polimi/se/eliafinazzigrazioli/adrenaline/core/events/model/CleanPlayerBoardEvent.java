package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

/**
 * The type Clean player board event.
 */
public class CleanPlayerBoardEvent extends AbstractModelEvent {

    /**
     * Instantiates a new Clean player board event.
     *
     * @param player the player
     */
    public CleanPlayerBoardEvent(Player player) {
        super(player);
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
