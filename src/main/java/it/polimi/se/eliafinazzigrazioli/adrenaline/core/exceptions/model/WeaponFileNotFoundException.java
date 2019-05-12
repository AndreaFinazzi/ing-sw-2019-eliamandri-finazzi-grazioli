package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

public class WeaponFileNotFoundException extends Exception {

    public WeaponFileNotFoundException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_WEAPON_CARD_WEAPON_FILE_NOT_FOUND);
    }

    public WeaponFileNotFoundException(String message) {
        super(message);
    }
}
