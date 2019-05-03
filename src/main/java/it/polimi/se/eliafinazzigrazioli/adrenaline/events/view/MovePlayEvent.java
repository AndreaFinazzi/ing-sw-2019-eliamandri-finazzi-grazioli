package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Coordinates;

import java.util.List;

public class MovePlayEvent extends AbstractViewEvent {
    private String player;
    private List<Coordinates> path;

    @Override
    public String getPlayer() {
        return player;
    }

    public List<Coordinates> getPath() {
        return path;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}