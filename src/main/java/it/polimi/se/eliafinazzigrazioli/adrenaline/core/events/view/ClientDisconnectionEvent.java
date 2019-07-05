package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.AbstractClientHandler;

/**
 * The type Client disconnection event.
 */
public class ClientDisconnectionEvent extends AbstractViewEvent {


    /**
     * Instantiates a new Client disconnection event.
     *
     * @param clientID the client id
     * @param player the player
     */
    public ClientDisconnectionEvent(int clientID, String player) {
        super(clientID, player);
    }

    /**
     * Instantiates a new Client disconnection event.
     *
     * @param clientHandler the client handler
     */
    public ClientDisconnectionEvent(AbstractClientHandler clientHandler) {
        super(clientHandler.getClientID());
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}
