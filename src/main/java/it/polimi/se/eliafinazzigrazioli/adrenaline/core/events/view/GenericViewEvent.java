package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

/**
 * The type Generic view event.
 */
public class GenericViewEvent extends AbstractViewEvent {

    /**
     * Instantiates a new Generic view event.
     *
     * @param clientID the client id
     * @param player the player
     * @param message the message
     */
    public GenericViewEvent(int clientID, String player, String message) {
        super(clientID, player);
        super.setMessage(message);
    }

    @Override
    public String getPlayer() {
        return super.getPlayer();
    }

    @Override
    public void setPlayer(String player) {
        super.setPlayer(player);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void setMessage(String message) {
        super.setMessage(message);
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
