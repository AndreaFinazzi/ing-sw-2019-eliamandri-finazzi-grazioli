package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class NotAllowedPlayEvent extends AbstractControllerEvent {

    private AbstractViewEvent event;

    public AbstractViewEvent getEvent() {
        return event;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
