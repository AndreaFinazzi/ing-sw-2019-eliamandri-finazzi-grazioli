package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class NotAllowedPlayEvent extends AbstractModelEvent {

    private AbstractViewEvent event;

    public NotAllowedPlayEvent(String player) {
        super(player);
    }

    public AbstractViewEvent getEvent() {
        return event;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
