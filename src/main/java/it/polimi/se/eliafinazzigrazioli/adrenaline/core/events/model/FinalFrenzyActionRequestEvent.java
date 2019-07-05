package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

public class FinalFrenzyActionRequestEvent extends AbstractModelEvent {

    private int simpleMovesMax;
    private int collectingMovesMax;
    private int shootingMovesMax;

    private int actionsRemained;

    private boolean singleAction;

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

    public int getSimpleMovesMax() {
        return simpleMovesMax;
    }

    public int getCollectingMovesMax() {
        return collectingMovesMax;
    }

    public int getShootingMovesMax() {
        return shootingMovesMax;
    }

    public int getActionsRemained() {
        return actionsRemained;
    }

    public boolean isSingleAction() {
        return singleAction;
    }
}
