package it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

public class NotAllowedPlayEvent extends AbstractControllerEvent {
    private String player;
    private AbstractViewEvent event;

    @Override
    public String getPlayer() {
        return player;
    }

    public AbstractViewEvent getEvent() {
        return event;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
