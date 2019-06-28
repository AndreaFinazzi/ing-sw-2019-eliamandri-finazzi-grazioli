package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;

import java.util.List;

public class SelectableRoomsEvent extends AbstractModelEvent {

    private List<Room> selectableRooms;

    public SelectableRoomsEvent(Player player, List<Room> selectableRooms) {
        super(true, player);
        this.selectableRooms = selectableRooms;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public List<Room> getSelectableRooms() {
        return selectableRooms;
    }
}
