package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

public class OutOfBoundBoardException extends Exception {

    public OutOfBoundBoardException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_MATCH_BOARD_OUT_OF_BOUND);
    }
}
