package it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events;

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Messages;

public class HandlerNotImplementedException extends Exception {

    public HandlerNotImplementedException() {
        super(Messages.MESSAGE_EXCEPTIONS_SERVER_HANDLER_NOT_IMPLEMENTED);
    }

    public HandlerNotImplementedException(String message) {
        super(Messages.MESSAGE_EXCEPTIONS_SERVER_HANDLER_NOT_IMPLEMENTED + ":\t" + message);
    }
}
