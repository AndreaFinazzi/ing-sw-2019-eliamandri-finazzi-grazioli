package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

/**
 * The type Action request event.
 */
public class ActionRequestEvent extends AbstractModelEvent {

    private int simpleMovesMax;
    private int collectingMovesMax;
    private int shootingMovesMax;

    /**
     * Instantiates a new Action request event.
     *
     * @param player the player
     * @param simpleMovesMax the simple moves max
     * @param collectingMovesMax the collecting moves max
     * @param shootingMovesMax the shooting moves max
     */
    public ActionRequestEvent(Player player, int simpleMovesMax, int collectingMovesMax, int shootingMovesMax) {
        super(true, true, player);
        this.simpleMovesMax = simpleMovesMax;
        this.collectingMovesMax = collectingMovesMax;
        this.shootingMovesMax = shootingMovesMax;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets simple moves max.
     *
     * @return the simple moves max
     */
    public int getSimpleMovesMax() {
        return simpleMovesMax;
    }

    /**
     * Gets collecting moves max.
     *
     * @return the collecting moves max
     */
    public int getCollectingMovesMax() {
        return collectingMovesMax;
    }

    /**
     * Gets shooting moves max.
     *
     * @return the shooting moves max
     */
    public int getShootingMovesMax() {
        return shootingMovesMax;
    }
}
