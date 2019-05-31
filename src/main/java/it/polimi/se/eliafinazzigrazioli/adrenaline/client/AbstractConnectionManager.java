package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;

import java.util.logging.Logger;

public abstract class AbstractConnectionManager implements Observer {

    protected static final Logger LOGGER = Logger.getLogger(AbstractConnectionManager.class.getName());

    protected int clientID;
    protected String playerName;
    protected RemoteView view;

    public AbstractConnectionManager(RemoteView view) {
        this.view = view;
        this.playerName = playerName;
    }

    public abstract void send(AbstractViewEvent event);

    public abstract void listen();

    public void received(AbstractModelEvent event) {
        update(event);
    }

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
