package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;

public class PlayerMovementEvent extends AbstractModelEvent {

    private List<Coordinates> path;

    public PlayerMovementEvent(String player, List<Coordinates> path) {
        super(player);
        this.path = path;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public List<Coordinates> getPath() {
        return path;
    }
}
