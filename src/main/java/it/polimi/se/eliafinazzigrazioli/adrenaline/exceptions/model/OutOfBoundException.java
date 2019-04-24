package it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Messages;

public class OutOfBoundException extends Exception {

    public OutOfBoundException() {
        super(Messages.get("app.exceptions.game.out_of_bound"));
    }

    public OutOfBoundException(String message) {
        super (message);
    }
}
