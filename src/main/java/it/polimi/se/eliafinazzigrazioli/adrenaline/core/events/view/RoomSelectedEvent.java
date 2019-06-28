package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;

public class RoomSelectedEvent extends AbstractViewEvent {

    private Room room;

    public RoomSelectedEvent(int clientID, String player, Room room) {
        super(clientID, player);
        this.room = room;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public Room getRoom() {
        return room;
    }
}
