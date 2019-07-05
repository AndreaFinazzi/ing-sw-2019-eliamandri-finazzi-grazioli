package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

/**
 * The type Final frenzy action request event.
 */
public class FinalFrenzyActionRequestEvent extends AbstractModelEvent {

    private int simpleMovesMax;
    private int collectingMovesMax;
    private int shootingMovesMax;

    private int actionsRemained;

    private boolean singleAction;

    /**
     * Instantiates a new Final frenzy action request event.
     *
     * @param player the player
     * @param actionsRemained the actions remained
     * @param simpleMovesMax the simple moves max
     * @param collectingMovesMax the collecting moves max
     * @param shootingMovesMax the shooting moves max
     * @param singleAction the single action
     */
    public FinalFrenzyActionRequestEvent(Player player, int actionsRemained, int simpleMovesMax, int collectingMovesMax, int shootingMovesMax, boolean singleAction) {
        super(true, true, player);
        this.simpleMovesMax = simpleMovesMax;
        this.collectingMovesMax = collectingMovesMax;
        this.shootingMovesMax = shootingMovesMax;
        this.actionsRemained = actionsRemained;
        this.singleAction = singleAction;
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

    /**
     * Gets actions remained.
     *
     * @return the actions remained
     */
    public int getActionsRemained() {
        return actionsRemained;
    }

    /**
     * Is single action boolean.
     *
     * @return the boolean
     */
    public boolean isSingleAction() {
        return singleAction;
    }
}
