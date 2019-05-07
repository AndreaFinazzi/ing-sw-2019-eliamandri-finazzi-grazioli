package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.model.PlayerUpdateEvent;

public class View implements EventListenerInterface {
    //TODO: should move to another View
    private String player;

    public void handleEvent(PlayerUpdateEvent playerUpdateEvent) {

    }

    @Override
    public String getPlayer() {
        return player;
    }
}
