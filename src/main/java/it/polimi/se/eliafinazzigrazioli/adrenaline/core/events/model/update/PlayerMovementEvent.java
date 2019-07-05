package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;

/**
 * The type Player movement event.
 */
public class PlayerMovementEvent extends AbstractModelEvent {

    private List<Coordinates> path;

    /**
     * Instantiates a new Player movement event.
     *
     * @param player the player
     * @param path the path
     */
    public PlayerMovementEvent(String player, List<Coordinates> path) {
        super(player);
        this.path = path;
    }

    /**
     * Instantiates a new Player movement event.
     *
     * @param player the player
     * @param path the path
     */
    public PlayerMovementEvent(Player player, List<Coordinates> path) {
        super(player.getPlayerNickname(), player.getClientID());
        this.path = path;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public List<Coordinates> getPath() {
        return path;
    }
}
