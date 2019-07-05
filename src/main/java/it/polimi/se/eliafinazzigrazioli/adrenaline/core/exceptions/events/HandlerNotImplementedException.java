package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

/**
 * The type Handler not implemented exception.
 */
public class HandlerNotImplementedException extends Exception {

    /**
     * Instantiates a new Handler not implemented exception.
     */
    public HandlerNotImplementedException() {
        super(Messages.MESSAGE_EXCEPTIONS_SERVER_HANDLER_NOT_IMPLEMENTED);
    }

    /**
     * Instantiates a new Handler not implemented exception.
     *
     * @param message the message
     */
    public HandlerNotImplementedException(String message) {
        super(Messages.MESSAGE_EXCEPTIONS_SERVER_HANDLER_NOT_IMPLEMENTED + ":\t" + message);
    }
}
