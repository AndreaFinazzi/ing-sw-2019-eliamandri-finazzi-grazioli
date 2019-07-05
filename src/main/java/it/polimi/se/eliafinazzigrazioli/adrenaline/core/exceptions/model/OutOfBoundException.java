package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

/**
 * The type Out of bound exception.
 */
public class OutOfBoundException extends Exception {

    /**
     * Instantiates a new Out of bound exception.
     */
    public OutOfBoundException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_OUT_OF_BOUND);
    }

    /**
     * Instantiates a new Out of bound exception.
     *
     * @param message the message
     */
    public OutOfBoundException(String message) {
        super(message);
    }
}
