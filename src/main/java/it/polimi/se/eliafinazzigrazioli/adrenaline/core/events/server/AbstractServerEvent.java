package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;

public class AbstractServerEvent extends AbstractEvent {

    protected int clientID;

    public AbstractServerEvent(String message) {
        super(message);
        clientID = 0;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        if(this.clientID == 0) {
            this.clientID = clientID;
        }
    }
}
