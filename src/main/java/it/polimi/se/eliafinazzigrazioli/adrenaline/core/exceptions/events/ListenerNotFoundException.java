package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

/**
 * The type Listener not found exception.
 */
public class ListenerNotFoundException extends Exception {

    /**
     * Instantiates a new Listener not found exception.
     */
    public ListenerNotFoundException() {
        super(Messages.MESSAGE_EXCEPTIONS_SERVER_LISTENER_NOT_FOUND);
    }

    /**
     * Instantiates a new Listener not found exception.
     *
     * @param message the message
     */
    public ListenerNotFoundException(String message) {
        super(message);
    }
}
