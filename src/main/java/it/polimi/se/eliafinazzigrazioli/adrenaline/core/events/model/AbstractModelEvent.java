package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;

public abstract class AbstractModelEvent extends AbstractEvent {
    private String player;

    public AbstractModelEvent(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }
}
