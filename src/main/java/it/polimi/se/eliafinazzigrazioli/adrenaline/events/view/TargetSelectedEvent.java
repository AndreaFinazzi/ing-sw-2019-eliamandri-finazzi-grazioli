package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Selectable;

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
