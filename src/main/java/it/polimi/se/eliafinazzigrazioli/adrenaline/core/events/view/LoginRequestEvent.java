package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;


import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class LoginRequestEvent extends AbstractViewEvent {

    public LoginRequestEvent(int clientID, String player) {
        super(player);
        this.clientID = clientID;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
