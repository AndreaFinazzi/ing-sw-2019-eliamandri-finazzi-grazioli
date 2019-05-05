package it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events;

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Messages;

public class ListenerNotFoundException extends Exception {

    public ListenerNotFoundException() {
        super(Messages.MESSAGE_EXCEPTIONS_SERVER_LISTENER_NOT_FOUND);
    }

    public ListenerNotFoundException(String message) {
        super(message);
    }
}
