package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;


/**
 * The type Begin turn event.
 */
public class BeginTurnEvent extends AbstractModelEvent {

    /**
     * Instantiates a new Begin turn event.
     *
     * @param player the player
     */
    public BeginTurnEvent(String player) {
        super(player);
    }

    /**
     * Instantiates a new Begin turn event.
     *
     * @param player the player
     */
    public BeginTurnEvent(Player player) {
        super(player);
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}
