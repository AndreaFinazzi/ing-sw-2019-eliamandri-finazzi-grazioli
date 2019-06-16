package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

public class SquareSelectedEvent extends AbstractViewEvent {

    private Coordinates square;

    public SquareSelectedEvent(int clientID, String player, Coordinates square) {
        super(clientID, player);
        this.square = square;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
