package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Selectable;

import java.util.List;

public class TargetSelectedEvent extends AbstractViewEvent {
    private String player;
    private List<Selectable> targets;

    public TargetSelectedEvent(String player) {
        this.player = player;
    }

    public List<Selectable> getTargets() {
        return targets;
    }

    ;

    @Override
    public String getPlayer() {
        return player;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
