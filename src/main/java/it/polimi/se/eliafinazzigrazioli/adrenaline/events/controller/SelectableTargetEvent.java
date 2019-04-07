package it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Selectable;

import java.util.List;

public class SelectableTargetEvent implements ControllerEventInterface{
    private String player;
    private List<Selectable> selectables;

    @Override
    public String getPlayer() {
        return player;
    }

    public List<Selectable> getSelectables() {
        return selectables;
    }
}
