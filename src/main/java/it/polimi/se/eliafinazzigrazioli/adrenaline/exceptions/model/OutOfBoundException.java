package it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Messages;

public class OutOfBoundException extends Exception {

    public OutOfBoundException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_OUT_OF_BOUND);
    }

    public OutOfBoundException(String message) {
        super (message);
    }
}
