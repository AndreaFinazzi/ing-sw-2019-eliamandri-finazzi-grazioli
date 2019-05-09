package it.polimi.se.eliafinazzigrazioli.adrenaline.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

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
