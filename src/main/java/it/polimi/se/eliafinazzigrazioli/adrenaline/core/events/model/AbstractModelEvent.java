package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

/**
 * The type Abstract model event.
 */
public abstract class AbstractModelEvent extends AbstractEvent {

    private int matchID;

    /**
     * The Private event.
     */
    protected boolean privateEvent = false;

    /**
     * The Request.
     */
    protected boolean request = false;

    /**
     * The Player.
     */
    protected String player;

    /**
     * The Client id.
     */
    protected int clientID = -1;

    /**
     * Instantiates a new Abstract model event.
     */
    public AbstractModelEvent() {
        super(Messages.MESSAGE_EVENTS_MODEL_DEFAULT);
    }

    /**
     * Instantiates a new Abstract model event.
     *
     * @param clientID the client id
     */
    public AbstractModelEvent(int clientID) {
        super(Messages.MESSAGE_EVENTS_MODEL_DEFAULT);
        this.clientID = clientID;
    }

    /**
     * Instantiates a new Abstract model event.
     *
     * @param player the player
     */
    public AbstractModelEvent(String player) {
        this();
        this.player = player;
    }

    /**
     * Instantiates a new Abstract model event.
     *
     * @param privateEvent the private event
     * @param player the player
     */
    public AbstractModelEvent(boolean privateEvent, String player) {
        this(player);
        this.privateEvent = privateEvent;
    }

    /**
     * Instantiates a new Abstract model event.
     *
     * @param player the player
     * @param message the message
     */
    public AbstractModelEvent(String player, String message) {
        super(message);
        this.player = player;
    }

    /**
     * Instantiates a new Abstract model event.
     *
     * @param player the player
     * @param clientID the client id
     */
    public AbstractModelEvent(String player, int clientID) {
        this(player);
        this.clientID = clientID;
    }

    /**
     * Instantiates a new Abstract model event.
     *
     * @param privateEvent the private event
     * @param player the player
     * @param clientID the client id
     */
    public AbstractModelEvent(boolean privateEvent, String player, int clientID) {
        this(player, clientID);
        this.privateEvent = privateEvent;
    }

    /**
     * Instantiates a new Abstract model event.
     *
     * @param player the player
     */
    public AbstractModelEvent(Player player) {
        this(player.getPlayerNickname(), player.getClientID());
    }

    /**
     * Instantiates a new Abstract model event.
     *
     * @param privateEvent the private event
     * @param player the player
     */
    public AbstractModelEvent(boolean privateEvent, Player player) {
        this(privateEvent, player.getPlayerNickname(), player.getClientID());
    }

    /**
     * Instantiates a new Abstract model event.
     *
     * @param privateEvent the private event
     * @param request the request
     * @param player the player
     */
    public AbstractModelEvent(boolean privateEvent, boolean request, Player player) {
        this(privateEvent, player.getPlayerNickname(), player.getClientID());
        this.request = request;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Gets client id.
     *
     * @return the client id
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * Gets match id.
     *
     * @return the match id
     */
    public int getMatchID() {
        return matchID;
    }

    /**
     * Is private event boolean.
     *
     * @return the boolean
     */
    public boolean isPrivateEvent() {
        return privateEvent;
    }

    /**
     * Is request boolean.
     *
     * @return the boolean
     */
    public boolean isRequest() {
        return request;
    }

    /**
     * Sets client id.
     *
     * @param clientID the client id
     */
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    /**
     * Sets match id.
     *
     * @param matchID the match id
     */
    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    //    public void setPrivateEvent(boolean privateEvent) {
//        this.privateEvent = privateEvent;
//    }

    /**
     * Handle.
     *
     * @param listener the listener
     * @throws HandlerNotImplementedException the handler not implemented exception
     */
    /*
     *   Due to Java method overloading static behaviour, is necessary to implement handle(listener:EventListenerInterface) for each concrete event type.
     *
     *   This way, the effective event handler is called by the event itself, allowing runtime overloading.
     */
    public abstract void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException;

}
