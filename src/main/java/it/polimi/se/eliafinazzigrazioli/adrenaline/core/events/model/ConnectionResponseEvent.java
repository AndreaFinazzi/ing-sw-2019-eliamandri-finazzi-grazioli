package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;

import java.util.ArrayList;

public class ConnectionResponseEvent extends AbstractModelEvent {

    private ArrayList<Avatar> availableAvatars;

    public ConnectionResponseEvent(int clientID, String message, ArrayList<Avatar> availableAvatars) {
        super(null, message);
        this.clientID = clientID;
        privateEvent = true;
        this.availableAvatars = availableAvatars;
    }

    public ArrayList<Avatar> getAvailableAvatars() {
        return availableAvatars;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}