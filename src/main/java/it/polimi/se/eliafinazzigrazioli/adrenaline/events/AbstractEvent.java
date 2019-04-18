package it.polimi.se.eliafinazzigrazioli.adrenaline.events;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

public abstract class AbstractEvent {

    public abstract void handle(EventListenerInterface listener) throws HandlerNotImplementedException;

}
