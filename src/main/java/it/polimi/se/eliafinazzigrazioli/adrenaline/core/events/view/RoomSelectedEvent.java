package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;

/**
 * The type Room selected event.
 */
public class RoomSelectedEvent extends AbstractViewEvent {

    private Room room;

    /**
     * Instantiates a new Room selected event.
     *
     * @param clientID the client id
     * @param player the player
     * @param room the room
     */
    public RoomSelectedEvent(int clientID, String player, Room room) {
        super(clientID, player);
        this.room = room;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets room.
     *
     * @return the room
     */
    public Room getRoom() {
        return room;
    }
}
