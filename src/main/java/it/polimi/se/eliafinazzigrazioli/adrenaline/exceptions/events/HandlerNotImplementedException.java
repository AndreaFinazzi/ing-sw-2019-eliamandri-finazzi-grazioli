package it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events;

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Messages;

public class HandlerNotImplementedException extends Exception {

    public HandlerNotImplementedException() {
        super(Messages.get("app.exceptions.server.handler_not_implemented"));
    }

    public HandlerNotImplementedException(String message) {
        super(message);
    }
}
