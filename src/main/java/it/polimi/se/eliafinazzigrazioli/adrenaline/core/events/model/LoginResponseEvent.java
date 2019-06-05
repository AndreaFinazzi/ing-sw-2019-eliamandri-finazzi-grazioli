package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class LoginResponseEvent extends AbstractModelEvent {

    boolean successful = false;

    public LoginResponseEvent(int clientID) {
        super(clientID);
        this.privateEvent = true;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccess(boolean successful) {
        this.successful = successful;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        if (listener.getClientID() == clientID) {
            listener.handleEvent(this);
        }
    }
}
