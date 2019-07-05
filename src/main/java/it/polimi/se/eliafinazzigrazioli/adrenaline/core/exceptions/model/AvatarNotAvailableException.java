package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

/**
 * The type Avatar not available exception.
 */
public class AvatarNotAvailableException extends Exception {
    /**
     * Instantiates a new Avatar not available exception.
     */
    public AvatarNotAvailableException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_MATCH_AVATAR_NOT_AVAILABLE);
    }

    /**
     * Instantiates a new Avatar not available exception.
     *
     * @param message the message
     */
    public AvatarNotAvailableException(String message) {
        super(message);
    }


}
