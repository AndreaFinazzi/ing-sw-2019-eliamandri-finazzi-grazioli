package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.MoveDirection;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.CardinalDirection;

/**
 * The type Direction selected event.
 */
public class DirectionSelectedEvent extends AbstractViewEvent {

    /**
     * The Direction.
     */
    CardinalDirection direction;

    /**
     * Instantiates a new Direction selected event.
     *
     * @param clientID the client id
     * @param player the player
     * @param direction the direction
     */
    public DirectionSelectedEvent(int clientID, String player, MoveDirection direction) {
        super(clientID, player);
        switch (direction) {
            case UP:
                this.direction = CardinalDirection.NORTH;
                break;
            case DOWN:
                this.direction = CardinalDirection.SOUTH;
                break;
            case LEFT:
                this.direction = CardinalDirection.WEST;
                break;
            case RIGHT:
                this.direction = CardinalDirection.EAST;
                break;
        }
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets direction.
     *
     * @return the direction
     */
    public CardinalDirection getDirection() {
        return direction;
    }
}
