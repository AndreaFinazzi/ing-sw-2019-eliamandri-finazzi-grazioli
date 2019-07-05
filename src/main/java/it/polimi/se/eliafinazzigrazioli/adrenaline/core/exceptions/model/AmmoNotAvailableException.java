package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

/**
 * The type Ammo not available exception.
 */
public class AmmoNotAvailableException extends Exception {
    /**
     * Instantiates a new Ammo not available exception.
     */
    public AmmoNotAvailableException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_AMMO_NOT_AVAILABLE);
    }

    /**
     * Instantiates a new Ammo not available exception.
     *
     * @param message the message
     */
    public AmmoNotAvailableException(String message) {
        super(message);
    }
}