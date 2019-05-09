package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Selectable;

import java.util.List;

public class TargetSelectedEvent extends AbstractViewEvent {

    private List<Selectable> targets;

    public TargetSelectedEvent(String player, List<Selectable> targets) {
        super(player);
        this.targets = targets;
    }

    public List<Selectable> getTargets() {
        return targets;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
