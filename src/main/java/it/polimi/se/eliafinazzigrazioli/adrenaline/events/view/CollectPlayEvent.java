package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

public class CollectPlayEvent extends AbstractViewEvent {
    private String player;

    public CollectPlayEvent(String player) {
        this.player = player;
    }

    @Override
    public String getPlayer() {
        return player;
    }

    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
