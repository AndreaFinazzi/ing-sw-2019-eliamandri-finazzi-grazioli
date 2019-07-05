package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

/**
 * The type Max player exception.
 */
public class MaxPlayerException extends Exception {
    /**
     * Instantiates a new Max player exception.
     *
     * @param message the message
     */
    public MaxPlayerException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Max player exception.
     */
    public MaxPlayerException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_MATCH_PLAYERS_OUT_OF_BOUND);
    }
}
