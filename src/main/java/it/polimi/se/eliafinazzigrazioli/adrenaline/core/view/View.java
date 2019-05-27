package it.polimi.se.eliafinazzigrazioli.adrenaline.core.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observable;

// TODO should be treated as EventListener for MV events?
public class View implements Observable, ModelEventsListenerInterface {

    private String playerName;

    public View(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String getPlayer() {
        return playerName;
    }

}
