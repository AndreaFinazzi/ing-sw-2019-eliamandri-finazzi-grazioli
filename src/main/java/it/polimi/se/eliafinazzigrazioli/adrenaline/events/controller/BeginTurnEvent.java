package it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

public class BeginTurnEvent extends AbstractControllerEvent {

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
