package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class FurtherActionEvent extends AbstractModelEvent {

    private int actionsRemained;

    public FurtherActionEvent(String player, int actionsRemained) {
        super(player, true);
        this.actionsRemained = actionsRemained;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public int getActionsRemained() {
        return actionsRemained;
    }
}
