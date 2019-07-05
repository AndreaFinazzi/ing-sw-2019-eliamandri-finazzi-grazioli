package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

/**
 * The type Player already present exception.
 */
public class PlayerAlreadyPresentException extends Exception {

    /**
     * Instantiates a new Player already present exception.
     *
     * @param message the message
     */
    public PlayerAlreadyPresentException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Player already present exception.
     */
    public PlayerAlreadyPresentException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_MATCH_PLAYER_ALREADY_PRESENT);
    }
}