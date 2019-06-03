package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class ConnectionResponseEvent extends AbstractModelEvent {

    public ConnectionResponseEvent(int clientID, String message) {
        super(null, message);
        this.clientID = clientID;
        privateEvent = true;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}