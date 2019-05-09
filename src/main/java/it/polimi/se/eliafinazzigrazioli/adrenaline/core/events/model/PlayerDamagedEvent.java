package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class PlayerDamagedEvent extends AbstractModelEvent {

    private String shooter;

    public PlayerDamagedEvent(String player) {
        super(player);
    }

    public String getShooter() {
        return shooter;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
