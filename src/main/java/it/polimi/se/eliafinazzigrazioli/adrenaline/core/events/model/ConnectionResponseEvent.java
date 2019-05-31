package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class ConnectionResponseEvent extends AbstractModelEvent {

    private int clientId;

    public int getClientId() {
        return clientId;
    }


    public ConnectionResponseEvent(String message) {
        super(message);
    }

    public ConnectionResponseEvent(int clientId, String message) {
        super(message);
        this.clientId = clientId;
    }


    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}