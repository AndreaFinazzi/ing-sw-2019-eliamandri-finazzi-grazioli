package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

public abstract class AbstractModelEvent extends AbstractEvent {
    protected String player;
    protected int clientID;

    public AbstractModelEvent(String player) {
        super(Messages.MESSAGE_EVENTS_MODEL_DEFAULT);
        this.player = player;
    }

    public AbstractModelEvent(String player, String message) {
        super(message);
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }

    /*
     *   Due to Java method overloading static behaviour, is necessary to implement handle(listener:EventListenerInterface) for each concrete event type.
     *
     *   This way, the effective event handler is called by the event itself, allowing runtime overloading.
     */
    public abstract void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException;

}
