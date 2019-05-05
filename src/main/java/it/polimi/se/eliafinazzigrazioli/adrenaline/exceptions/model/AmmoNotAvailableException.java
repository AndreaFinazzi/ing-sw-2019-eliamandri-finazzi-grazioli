package it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Messages;

public class AmmoNotAvailableException extends Exception {
    public AmmoNotAvailableException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_AMMO_NOT_AVAILABLE);
    }

    public AmmoNotAvailableException(String message) {
        super (message);
    }
}