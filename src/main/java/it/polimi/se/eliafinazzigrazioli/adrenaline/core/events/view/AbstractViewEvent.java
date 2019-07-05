package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

/**
 * The type Abstract view event.
 */
public abstract class AbstractViewEvent extends AbstractEvent {

    /**
     * The Player.
     */
    protected String player;
    /**
     * The Client id.
     */
    protected int clientID;

    /**
     * Instantiates a new Abstract view event.
     *
     * @param player the player
     */
    public AbstractViewEvent(String player) {
        super(Messages.MESSAGE_EVENTS_VIEW_DEFAULT);
        this.player = player;
    }

    /**
     * Instantiates a new Abstract view event.
     *
     * @param clientID the client id
     */
    public AbstractViewEvent(int clientID) {
        super(Messages.MESSAGE_EVENTS_VIEW_DEFAULT);
        this.clientID = clientID;
    }

    /**
     * Instantiates a new Abstract view event.
     *
     * @param clientID the client id
     * @param player the player
     */
    public AbstractViewEvent(int clientID, String player) {
        super(Messages.MESSAGE_EVENTS_VIEW_DEFAULT);
        this.clientID = clientID;
        this.player = player;
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
     * Gets player.
     *
     * @return the player
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(String player) {
        this.player = player;
    }

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
    public abstract void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException;

}
