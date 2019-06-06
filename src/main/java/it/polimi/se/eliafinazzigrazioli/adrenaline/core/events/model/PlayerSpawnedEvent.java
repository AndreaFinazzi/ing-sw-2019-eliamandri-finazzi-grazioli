package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

public class PlayerSpawnedEvent extends AbstractModelEvent{

    private Coordinates spawnPoint;

    public PlayerSpawnedEvent(String player, Coordinates spawnPoint) {
        super(player);
        this.spawnPoint = spawnPoint;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public Coordinates getSpawnPoint() {
        return spawnPoint;
    }
}
