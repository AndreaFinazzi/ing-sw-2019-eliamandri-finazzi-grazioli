package it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Messages;

public class PlayerAlreadyPresentException extends Exception {

    public PlayerAlreadyPresentException(String message) {
        super (message);
    }

    public PlayerAlreadyPresentException() {
        super(Messages.MESSAGE_EXCEPTIONS_GAME_MATCH_PLAYER_ALREADY_PRESENT);
    }
}