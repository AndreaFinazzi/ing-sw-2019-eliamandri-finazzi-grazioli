package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

public class MaxPlayerException extends Exception {
    public MaxPlayerException(String message) {
        super(message);
    }

    public MaxPlayerException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_MATCH_PLAYERS_OUT_OF_BOUND);
    }
}
