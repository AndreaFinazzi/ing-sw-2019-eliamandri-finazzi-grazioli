package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;

import java.util.ArrayList;

/**
 * The type Connection response event.
 */
public class ConnectionResponseEvent extends AbstractModelEvent {

    private ArrayList<Avatar> availableAvatars;

    /**
     * Instantiates a new Connection response event.
     *
     * @param clientID the client id
     * @param message the message
     * @param availableAvatars the available avatars
     */
    public ConnectionResponseEvent(int clientID, String message, ArrayList<Avatar> availableAvatars) {
        super(null, message);
        this.clientID = clientID;
        privateEvent = true;
        this.availableAvatars = availableAvatars;
    }

    /**
     * Gets available avatars.
     *
     * @return the available avatars
     */
    public ArrayList<Avatar> getAvailableAvatars() {
        return availableAvatars;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}