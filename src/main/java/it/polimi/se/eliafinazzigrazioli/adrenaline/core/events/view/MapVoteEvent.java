package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;

/**
 * The type Map vote event.
 */
public class MapVoteEvent extends AbstractViewEvent {

    private MapType votedMap;

    /**
     * Instantiates a new Map vote event.
     *
     * @param clientID the client id
     * @param player the player
     * @param mapType the map type
     */
    public MapVoteEvent(int clientID, String player, MapType mapType) {
        super(clientID, player);

        this.votedMap = mapType;
    }

    /**
     * Gets voted map.
     *
     * @return the voted map
     */
    public MapType getVotedMap() {
        return votedMap;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
