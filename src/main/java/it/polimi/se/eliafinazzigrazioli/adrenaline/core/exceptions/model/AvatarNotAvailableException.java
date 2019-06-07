package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

public class AvatarNotAvailableException extends Exception {
    public AvatarNotAvailableException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_MATCH_AVATAR_NOT_AVAILABLE);
    }

    public AvatarNotAvailableException(String message) {
        super(message);
    }


}
