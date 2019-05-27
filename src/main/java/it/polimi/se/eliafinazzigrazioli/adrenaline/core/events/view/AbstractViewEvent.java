package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

public abstract class AbstractViewEvent extends AbstractEvent {

    protected String player;
    int clientID;
    
    public AbstractViewEvent(String player) {
        super(Messages.MESSAGE_EVENTS_VIEW_DEFAULT);
        this.player = player;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    /*
     *   Due to Java method overloading static behaviour, is necessary to implement handle(listener:EventListenerInterface) for each concrete event type.
     *
     *   This way, the effective event handler is called by the event itself, allowing runtime overloading.
     */
    public abstract void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException;

}
