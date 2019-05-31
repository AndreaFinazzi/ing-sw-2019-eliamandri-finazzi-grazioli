package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

public class OutOfBoundException extends Exception {

    public OutOfBoundException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_OUT_OF_BOUND);
    }

    public OutOfBoundException(String message) {
        super(message);
    }
}
