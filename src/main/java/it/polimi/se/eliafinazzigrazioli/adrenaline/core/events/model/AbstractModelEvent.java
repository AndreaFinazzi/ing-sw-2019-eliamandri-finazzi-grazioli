package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public abstract class AbstractModelEvent extends AbstractEvent {
    private String player;

    public AbstractModelEvent(String player) {
        this.player = player;
    }

    public AbstractModelEvent(String player, String message) {
        super();
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
