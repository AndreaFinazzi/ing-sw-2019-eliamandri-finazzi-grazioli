package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

/**
 * The type Not allowed play event.
 */
public class NotAllowedPlayEvent extends AbstractModelEvent {

    private AbstractViewEvent event;

    /**
     * Instantiates a new Not allowed play event.
     *
     * @param player the player
     */
    public NotAllowedPlayEvent(String player) {
        super(true, player);
    }

    /**
     * Instantiates a new Not allowed play event.
     *
     * @param player the player
     */
    public NotAllowedPlayEvent(Player player) {
        super(true, player.getPlayerNickname(), player.getClientID());
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets event.
     *
     * @return the event
     */
    public AbstractViewEvent getEvent() {
        return event;
    }
}
