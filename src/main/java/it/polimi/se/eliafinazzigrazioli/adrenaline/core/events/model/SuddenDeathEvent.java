package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class SuddenDeathEvent extends AbstractModelEvent {

    private String deadPlayer;

    public SuddenDeathEvent(String player, String deadPlayer) {
        super(player);
        this.deadPlayer = deadPlayer;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
