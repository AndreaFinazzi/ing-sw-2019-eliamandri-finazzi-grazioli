package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class ClientDisconnectionEvent extends AbstractViewEvent {


    public ClientDisconnectionEvent(String player) {
        super(player);
    }

    public ClientDisconnectionEvent(int clientID) {
        super(clientID);
    }

    public ClientDisconnectionEvent(int clientID, String player) {
        super(clientID, player);
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {

    }

}
