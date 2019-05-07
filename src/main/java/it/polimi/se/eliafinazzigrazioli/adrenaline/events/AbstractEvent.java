package it.polimi.se.eliafinazzigrazioli.adrenaline.events;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

public abstract class AbstractEvent {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /*
     *   Due to Java method overloading static behaviour, is necessary to implement handle(listener:EventListenerInterface) for each concrete event type.
     *
     *   This way, the effective event handler is called by the event itself, allowing runtime overloading.
     */
    public abstract void handle(EventListenerInterface listener) throws HandlerNotImplementedException;

}
