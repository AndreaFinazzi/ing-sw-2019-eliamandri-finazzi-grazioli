package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

/**
 * The type Weapon file not found exception.
 */
public class WeaponFileNotFoundException extends Exception {

    /**
     * Instantiates a new Weapon file not found exception.
     */
    public WeaponFileNotFoundException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_WEAPON_CARD_WEAPON_FILE_NOT_FOUND);
    }

    /**
     * Instantiates a new Weapon file not found exception.
     *
     * @param message the message
     */
    public WeaponFileNotFoundException(String message) {
        super(message);
    }
}
