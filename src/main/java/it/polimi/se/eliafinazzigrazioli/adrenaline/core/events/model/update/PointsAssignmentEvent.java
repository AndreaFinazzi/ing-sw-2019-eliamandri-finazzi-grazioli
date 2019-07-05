package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

import java.util.Map;

/**
 * The type Points assignment event.
 */
public class PointsAssignmentEvent extends AbstractModelEvent {

    private Map<String, Integer> playerPointsMap;

    /**
     * Instantiates a new Points assignment event.
     *
     * @param playerPointsMap the player points map
     */
    public PointsAssignmentEvent(Map<String, Integer> playerPointsMap) {
        this.playerPointsMap = playerPointsMap;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets player points map.
     *
     * @return the player points map
     */
    public Map<String, Integer> getPlayerPointsMap() {
        return playerPointsMap;
    }
}
