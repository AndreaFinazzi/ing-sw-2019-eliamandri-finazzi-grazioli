package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

import java.util.List;

/**
 * The type Players selected event.
 */
public class PlayersSelectedEvent extends AbstractViewEvent {

    /**
     * The Players.
     */
    List<String> players;

    /**
     * Instantiates a new Players selected event.
     *
     * @param clientID the client id
     * @param player the player
     * @param players the players
     */
    public PlayersSelectedEvent(int clientID, String player, List<String> players) {
        super(clientID, player);
        this.players = players;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets players.
     *
     * @return the players
     */
    public List<String> getPlayers() {
        return players;
    }
}
