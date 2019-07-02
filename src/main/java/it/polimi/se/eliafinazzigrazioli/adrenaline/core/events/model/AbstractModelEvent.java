package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

public abstract class AbstractModelEvent extends AbstractEvent {

    private int matchID;

    protected boolean privateEvent = false;

    protected boolean request = false;

    protected String player;

    protected int clientID = -1;

    public AbstractModelEvent() {
        super(Messages.MESSAGE_EVENTS_MODEL_DEFAULT);
    }

    public AbstractModelEvent(int clientID) {
        super(Messages.MESSAGE_EVENTS_MODEL_DEFAULT);
        this.clientID = clientID;
    }

    public AbstractModelEvent(String player) {
        this();
        this.player = player;
    }

    public AbstractModelEvent(boolean privateEvent, String player) {
        this(player);
        this.privateEvent = privateEvent;
    }

    public AbstractModelEvent(String player, String message) {
        super(message);
        this.player = player;
    }

    public AbstractModelEvent(String player, int clientID) {
        this(player);
        this.clientID = clientID;
    }

    public AbstractModelEvent(boolean privateEvent, String player, int clientID) {
        this(player, clientID);
        this.privateEvent = privateEvent;
    }

    public AbstractModelEvent(Player player) {
        this(player.getPlayerNickname(), player.getClientID());
    }

    public AbstractModelEvent(boolean privateEvent, Player player) {
        this(privateEvent, player.getPlayerNickname(), player.getClientID());
    }

    public AbstractModelEvent(boolean privateEvent, boolean request, Player player) {
        this(privateEvent, player.getPlayerNickname(), player.getClientID());
        this.request = request;
    }

    public String getPlayer() {
        return player;
    }

    public int getClientID() {
        return clientID;
    }

    public int getMatchID() {
        return matchID;
    }

    public boolean isPrivateEvent() {
        return privateEvent;
    }

    public boolean isRequest() {
        return request;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    //    public void setPrivateEvent(boolean privateEvent) {
//        this.privateEvent = privateEvent;
//    }

    /*
     *   Due to Java method overloading static behaviour, is necessary to implement handle(listener:EventListenerInterface) for each concrete event type.
     *
     *   This way, the effective event handler is called by the event itself, allowing runtime overloading.
     */
    public abstract void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException;

}
