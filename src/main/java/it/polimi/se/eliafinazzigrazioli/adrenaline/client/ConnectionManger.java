package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.AbstractViewEvent;

public abstract class ConnectionManger {

    protected String playerName;

    public ConnectionManger(String playerName) {
        this.playerName = playerName;
    }

    public abstract void send(AbstractViewEvent event);

    public abstract AbstractEvent receive();
}
