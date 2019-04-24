package it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events;

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Messages;

public class ListenerNotFoundException extends Exception {

    public ListenerNotFoundException() {
        super(Messages.get("app.exceptions.server.listener_not_not_found"));
    }

    public ListenerNotFoundException(String message) {
        super(message);
    }
}
