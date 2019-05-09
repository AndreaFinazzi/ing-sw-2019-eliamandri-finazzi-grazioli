package it.polimi.se.eliafinazzigrazioli.adrenaline.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Coordinates;

import java.util.List;

public class PlayerMovementEvent extends AbstractModelEvent {

    private List<Coordinates> path;

    public PlayerMovementEvent(String player, List<Coordinates> path) {
        super(player);
        this.path = path;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
