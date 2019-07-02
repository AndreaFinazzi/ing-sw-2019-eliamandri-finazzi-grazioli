package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

public class PlayerSpawnedEvent extends AbstractModelEvent {

    private Coordinates spawnPoint;
    private PowerUpCardClient discardedPowerUp;
    private boolean firsSpawn;
    private boolean ownedDiscardedPowerUp;

    public PlayerSpawnedEvent(Player player, Coordinates spawnPoint, PowerUpCard discardedPowerUp, boolean firsSpawn, boolean ownedDiscardedPowerUp) {
        super(player.getPlayerNickname(), player.getClientID());
        this.spawnPoint = spawnPoint;
        this.discardedPowerUp = new PowerUpCardClient(discardedPowerUp);
        this.firsSpawn = firsSpawn;
        this.ownedDiscardedPowerUp = ownedDiscardedPowerUp;
    }

    public PlayerSpawnedEvent(Player player, Coordinates spawnPoint, PowerUpCard discardedPowerUp, boolean firsSpawn) {
        super(player.getPlayerNickname(), player.getClientID());
        this.spawnPoint = spawnPoint;
        this.discardedPowerUp = new PowerUpCardClient(discardedPowerUp);
        this.firsSpawn = firsSpawn;
        ownedDiscardedPowerUp = false;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public Coordinates getSpawnPoint() {
        return spawnPoint;
    }

    public PowerUpCardClient getDiscardedPowerUp() {
        return discardedPowerUp;
    }

    public boolean isFirsSpawn() {
        return firsSpawn;
    }

    public boolean ownedDiscardedPowerUp() {
        return ownedDiscardedPowerUp;
    }
}
