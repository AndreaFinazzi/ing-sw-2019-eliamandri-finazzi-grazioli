package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;

/**
 * The type Square selected event.
 */
public class SquareSelectedEvent extends AbstractViewEvent {

    private List<Coordinates> squares;

    /**
     * Instantiates a new Square selected event.
     *
     * @param clientID the client id
     * @param player the player
     * @param squares the squares
     */
    public SquareSelectedEvent(int clientID, String player, List<Coordinates> squares) {
        super(clientID, player);
        this.squares = squares;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets squares.
     *
     * @return the squares
     */
    public List<Coordinates> getSquares() {
        return squares;
    }
}
