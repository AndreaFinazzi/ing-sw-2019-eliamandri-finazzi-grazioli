package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class ConnectionTimeoutEvent extends AbstractModelEvent {
    private String message;

    public ConnectionTimeoutEvent(String player) {
        super(player);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
