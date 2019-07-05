package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

/**
 * The type Reload weapons request event.
 */
public class ReloadWeaponsRequestEvent extends AbstractModelEvent {

    /**
     * Instantiates a new Reload weapons request event.
     *
     * @param player the player
     */
    public ReloadWeaponsRequestEvent(Player player) {
        super(true, true, player);
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
