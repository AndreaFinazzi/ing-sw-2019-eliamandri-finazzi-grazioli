package it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Messages;

public class MaxPlayerException extends Exception {
    public MaxPlayerException(String message) {
        super(message);
    }

    public MaxPlayerException() {
        super(Messages.get ("app.exceptions.game.match.max_players"));
    }
}
