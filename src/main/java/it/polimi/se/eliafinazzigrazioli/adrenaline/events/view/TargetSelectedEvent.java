package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Selectable;

import java.util.List;

public class TargetSelectedEvent implements ViewEventInterface{
    private String player;
    private List<Selectable> targets;

    @Override
    public String getPlayer() {
        return player;
    }

    public List<Selectable> getTargets() {
        return targets;
    }
}
