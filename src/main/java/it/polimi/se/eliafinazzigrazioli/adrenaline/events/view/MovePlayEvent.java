package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Coordinates;

import java.util.List;

public class MovePlayEvent extends AbstractViewEvent {

    private List<Coordinates> path;

    public MovePlayEvent(String player, List<Coordinates> path) {
        super(player);
        this.path = path;
    }

    public List<Coordinates> getPath() {
        return path;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}