package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;


import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class LoginRequestEvent extends AbstractViewEvent {

    int sourceClientID;

    public LoginRequestEvent(int clientID, String player) {
        super(player);
        this.sourceClientID = clientID;
    }

    public int getSourceClientID() {
        return sourceClientID;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
