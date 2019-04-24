package it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Messages;

public class AmmoNotAvailableException extends Exception {
    public AmmoNotAvailableException() {
        super(Messages.get("app.exceptions.game.ammo.not_available"));
    }

    public AmmoNotAvailableException(String message) {
        super (message);
    }
}