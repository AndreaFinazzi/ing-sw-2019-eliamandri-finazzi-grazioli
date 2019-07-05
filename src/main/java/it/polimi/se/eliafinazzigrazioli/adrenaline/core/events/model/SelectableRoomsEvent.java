package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;

import java.util.List;

/**
 * The type Selectable rooms event.
 */
public class SelectableRoomsEvent extends AbstractModelEvent {

    private List<Room> selectableRooms;

    /**
     * Instantiates a new Selectable rooms event.
     *
     * @param player the player
     * @param selectableRooms the selectable rooms
     */
    public SelectableRoomsEvent(Player player, List<Room> selectableRooms) {
        super(true, true, player);
        this.selectableRooms = selectableRooms;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets selectable rooms.
     *
     * @return the selectable rooms
     */
    public List<Room> getSelectableRooms() {
        return selectableRooms;
    }
}
