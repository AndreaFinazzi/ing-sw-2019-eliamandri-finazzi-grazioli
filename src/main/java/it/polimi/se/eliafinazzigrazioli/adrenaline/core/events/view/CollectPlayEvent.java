package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;

public class CollectPlayEvent extends AbstractViewEvent {

    private List<Coordinates> path;

    public CollectPlayEvent(int clientID, String player, List<Coordinates> path) {
        super(clientID, player);
        this.path = path;
    }

    public List<Coordinates> getPath() {
        return path;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
