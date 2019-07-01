package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

import java.util.Map;

public class PointsAssignmentEvent extends AbstractModelEvent {

    private Map<String, Integer> playerPointsMap;

    public PointsAssignmentEvent(Map<String, Integer> playerPointsMap) {
        this.playerPointsMap = playerPointsMap;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public Map<String, Integer> getPlayerPointsMap() {
        return playerPointsMap;
    }
}
