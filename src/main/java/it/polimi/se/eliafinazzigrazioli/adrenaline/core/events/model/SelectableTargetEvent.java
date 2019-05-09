package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Selectable;

import java.util.List;

public class SelectableTargetEvent extends AbstractModelEvent {

    private List<Selectable> selectables;

    public SelectableTargetEvent(String player) {
        super(player);
    }

    public List<Selectable> getSelectables() {
        return selectables;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
