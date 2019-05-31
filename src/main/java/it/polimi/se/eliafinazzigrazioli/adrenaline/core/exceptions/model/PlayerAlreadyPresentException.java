package it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

public class PlayerAlreadyPresentException extends Exception {

    public PlayerAlreadyPresentException(String message) {
        super(message);
    }

    public PlayerAlreadyPresentException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_MATCH_PLAYER_ALREADY_PRESENT);
    }
}