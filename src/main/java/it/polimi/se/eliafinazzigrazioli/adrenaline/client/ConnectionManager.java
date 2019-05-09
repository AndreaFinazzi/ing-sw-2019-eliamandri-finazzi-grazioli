package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;

public abstract class ConnectionManager {

    protected String playerName;

    public ConnectionManager(String playerName) {
        this.playerName = playerName;
    }

    public abstract void send(AbstractViewEvent event);

    public abstract AbstractEvent receive();


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
