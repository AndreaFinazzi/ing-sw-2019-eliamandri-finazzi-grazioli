package it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.MatchController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

public class ConnectionTimeoutEvent extends AbstractControllerEvent {
    private String message;

    public String getMessage() {
        return message;
    }

    @Override
    public void handle(EventListenerInterface listener, MatchController matchController) throws HandlerNotImplementedException {
        listener.handleEvent(this, matchController);
    }
}
