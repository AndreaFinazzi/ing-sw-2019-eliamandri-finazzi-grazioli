package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;

import java.util.List;

public class SelectableRoomsEvent extends AbstractModelEvent {

    private List<Room> selectableRooms;

    public SelectableRoomsEvent(String player, List<Room> selectableRooms) {
        super(player);
        this.selectableRooms = selectableRooms;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        if (this.getPlayer() == listener.getPlayer())
            listener.handleEvent(this);
    }
}
