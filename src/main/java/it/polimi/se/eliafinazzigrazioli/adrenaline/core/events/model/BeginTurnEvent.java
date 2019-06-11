package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class BeginTurnEvent extends AbstractModelEvent {

    private int simpleMovesMax;
    private int collectingMovesMax;
    private int shootingMovesMax;

    public BeginTurnEvent(String player) {
        super(player);
    }

    public BeginTurnEvent(String player, int simpleMovesMax, int collectingMovesMax, int shootingMovesMax) {
        super(player);
        this.simpleMovesMax = simpleMovesMax;
        this.collectingMovesMax = collectingMovesMax;
        this.shootingMovesMax = shootingMovesMax;
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
}
