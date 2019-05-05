package it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Messages;

public class MaxPlayerException extends Exception {
    public MaxPlayerException(String message) {
        super(message);
    }

    public MaxPlayerException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_MATCH_PLAYERS_OUT_OF_BOUND);
    }
}
