package it.polimi.se.eliafinazzigrazioli.adrenaline.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

public class PlayerUpdateEvent extends AbstractModelEvent {

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        if (this.getPlayer() == listener.getPlayer())
            listener.handleEvent(this);
    }
}
