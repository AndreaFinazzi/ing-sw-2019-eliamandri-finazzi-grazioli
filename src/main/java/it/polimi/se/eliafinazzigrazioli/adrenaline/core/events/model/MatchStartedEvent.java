package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Match started event.
 */
public class MatchStartedEvent extends AbstractModelEvent {

    private HashMap<String, Avatar> playerToAvatarMap;

    private ArrayList<MapType> availableMaps;

    /**
     * Instantiates a new Match started event.
     *
     * @param playerToAvatarMap the player to avatar map
     * @param availableMaps the available maps
     */
    public MatchStartedEvent(HashMap<String, Avatar> playerToAvatarMap, ArrayList<MapType> availableMaps) {
        this.playerToAvatarMap = playerToAvatarMap;
        this.availableMaps = availableMaps;
    }

    /**
     * Gets player to avatar map.
     *
     * @return the player to avatar map
     */
    public HashMap<String, Avatar> getPlayerToAvatarMap() {
        return playerToAvatarMap;
    }

    /**
     * Gets available maps.
     *
     * @return the available maps
     */
    public ArrayList<MapType> getAvailableMaps() {
        return availableMaps;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
