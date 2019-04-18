package it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Coordinates;

import java.util.List;
import java.util.Map;

public class AllowedMovesEvent extends AbstractControllerEvent {
    private String player;
    private Map<Coordinates, List<Coordinates>> path;

    @Override
    public String getPlayer() {
        return player;
    }

    public Map<Coordinates, List<Coordinates>> getPath() {
        return path;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
