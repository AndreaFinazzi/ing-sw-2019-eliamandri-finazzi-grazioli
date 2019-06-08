package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;

import java.util.ArrayList;
import java.util.HashMap;

public class MatchStartedEvent extends AbstractModelEvent {

    private HashMap<String, Avatar> playerToAvatarMap;

    private ArrayList<MapType> availableMaps;

    public MatchStartedEvent(HashMap<String, Avatar> playerToAvatarMap, ArrayList<MapType> availableMaps) {
        this.playerToAvatarMap = playerToAvatarMap;
        this.availableMaps = availableMaps;
    }

    public HashMap<String, Avatar> getPlayerToAvatarMap() {
        return playerToAvatarMap;
    }

    public ArrayList<MapType> getAvailableMaps() {
        return availableMaps;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
