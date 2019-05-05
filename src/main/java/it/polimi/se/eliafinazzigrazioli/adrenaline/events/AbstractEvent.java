package it.polimi.se.eliafinazzigrazioli.adrenaline.events;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.MatchController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

public abstract class AbstractEvent {

    /*
     *   Due to Java method overloading static behaviour, is necessary to implement handle(listener:EventListenerInterface) for each concrete event type.
     *
     *   This way, the effective event handler is called by the event itself, allowing runtime overloading.
     */
    public abstract void handle(EventListenerInterface listener, MatchController matchController) throws HandlerNotImplementedException;

}
