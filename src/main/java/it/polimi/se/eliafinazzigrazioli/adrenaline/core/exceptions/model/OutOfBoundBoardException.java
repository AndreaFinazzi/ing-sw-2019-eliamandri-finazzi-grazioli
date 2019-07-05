package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

/**
 * The type Out of bound board exception.
 */
public class OutOfBoundBoardException extends Exception {

    /**
     * Instantiates a new Out of bound board exception.
     */
    public OutOfBoundBoardException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_MATCH_BOARD_OUT_OF_BOUND);
    }
}
