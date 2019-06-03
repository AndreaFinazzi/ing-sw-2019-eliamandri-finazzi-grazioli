package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class LoginResponseEvent extends AbstractModelEvent {

    int targetClientID;
    boolean successful = false;

    public LoginResponseEvent(int targetClientID) {
        super(null);
        this.targetClientID = targetClientID;
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
        if (listener.getClientID() == targetClientID) {
            listener.handleEvent(this);
        }
    }
}
