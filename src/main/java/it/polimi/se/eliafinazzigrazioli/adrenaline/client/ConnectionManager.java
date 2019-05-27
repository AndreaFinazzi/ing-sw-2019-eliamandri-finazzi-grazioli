package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;

public abstract class ConnectionManager implements Observer {

    protected String playerName;
    private RemoteView view;

    public ConnectionManager() {
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


    // Outgoing events
    @Override
    public void update(AbstractViewEvent event) {
        send(event);
    }


    // Incoming events
    @Override
    public void update(AbstractModelEvent event) {
        //Visitor pattern
        try {
            event.handle(view);
        } catch (HandlerNotImplementedException e) {
            e.printStackTrace();
        }
    }
}
