package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.MoveDirection;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.CardinalDirection;

public class DirectionSelectedEvent extends AbstractViewEvent {

    CardinalDirection direction;

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

    public CardinalDirection getDirection() {
        return direction;
    }
}
