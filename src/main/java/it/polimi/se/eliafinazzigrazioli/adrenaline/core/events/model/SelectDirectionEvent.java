package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

/**
 * The type Select direction event.
 */
public class SelectDirectionEvent extends AbstractModelEvent {

    /**
     * Instantiates a new Select direction event.
     *
     * @param player the player
     */
    public SelectDirectionEvent(Player player) {
        super(true, true, player);
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
