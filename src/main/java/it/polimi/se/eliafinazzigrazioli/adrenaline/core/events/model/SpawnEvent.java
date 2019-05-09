package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

public class SpawnEvent extends AbstractModelEvent {

    private Coordinates spawnBoardSquare;

    public SpawnEvent(String player, Coordinates spawnBoardSquare) {
        super(player);
        this.spawnBoardSquare = spawnBoardSquare;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}

