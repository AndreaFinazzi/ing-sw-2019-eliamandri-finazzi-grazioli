package it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;

public abstract class AbstractControllerEvent extends AbstractEvent {
    private String player;

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }
}
