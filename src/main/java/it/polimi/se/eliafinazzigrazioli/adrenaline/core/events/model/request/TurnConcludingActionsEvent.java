package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class TurnConcludingActionsEvent extends AbstractModelEvent {

    public TurnConcludingActionsEvent(String player) {
        super(player, true);
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
