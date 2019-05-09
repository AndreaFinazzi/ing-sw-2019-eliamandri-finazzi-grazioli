package it.polimi.se.eliafinazzigrazioli.adrenaline.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Observable;

// TODO should be treated as EventListener for MV events?
public class View extends Observable implements EventListenerInterface {

    private String playerName;

    protected View(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String getPlayer() {
        return playerName;
    }
}
