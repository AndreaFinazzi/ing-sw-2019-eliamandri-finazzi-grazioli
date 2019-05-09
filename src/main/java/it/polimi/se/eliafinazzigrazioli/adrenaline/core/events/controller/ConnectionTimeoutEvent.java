package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class ConnectionTimeoutEvent extends AbstractControllerEvent {
    private String message;

    public String getMessage() {
        return message;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
