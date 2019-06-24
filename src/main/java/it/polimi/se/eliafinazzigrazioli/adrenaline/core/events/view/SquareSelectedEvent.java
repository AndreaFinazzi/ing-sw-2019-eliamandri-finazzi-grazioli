package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;

public class SquareSelectedEvent extends AbstractViewEvent {

    private List<Coordinates> squares;

    public SquareSelectedEvent(int clientID, String player, List<Coordinates> squares) {
        super(clientID, player);
        this.squares = squares;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public List<Coordinates> getSquares() {
        return squares;
    }
}
