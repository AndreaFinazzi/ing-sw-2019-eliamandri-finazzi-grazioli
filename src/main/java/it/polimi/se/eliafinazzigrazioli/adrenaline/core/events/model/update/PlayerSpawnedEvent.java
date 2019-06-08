package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

public class PlayerSpawnedEvent extends AbstractModelEvent {

    private Coordinates spawnPoint;
    private PowerUpCard discardedPowerUp;

    public PlayerSpawnedEvent(String player, Coordinates spawnPoint, PowerUpCard discardedPowerUp) {
        super(player);
        this.spawnPoint = spawnPoint;
        this.discardedPowerUp = discardedPowerUp;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public Coordinates getSpawnPoint() {
        return spawnPoint;
    }

    public PowerUpCard getDiscardedPowerUp() {
        return discardedPowerUp;
    }
}
