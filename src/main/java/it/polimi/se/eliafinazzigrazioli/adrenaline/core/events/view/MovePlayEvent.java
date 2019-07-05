package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;

/**
 * The type Move play event.
 */
public class MovePlayEvent extends AbstractViewEvent {

    private List<Coordinates> path;

    /**
     * Instantiates a new Move play event.
     *
     * @param clientID the client id
     * @param player the player
     * @param path the path
     */
    public MovePlayEvent(int clientID, String player, List<Coordinates> path) {
        super(clientID, player);
        this.path = path;
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public List<Coordinates> getPath() {
        return path;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}