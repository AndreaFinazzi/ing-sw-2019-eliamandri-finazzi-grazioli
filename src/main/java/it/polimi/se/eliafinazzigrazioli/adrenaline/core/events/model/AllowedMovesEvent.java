package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;
import java.util.Map;

public class AllowedMovesEvent extends AbstractModelEvent {

    private Map<Coordinates, List<Coordinates>> path;

    public AllowedMovesEvent(String player) {
        super(player);
    }

    public Map<Coordinates, List<Coordinates>> getPath() {
        return path;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
