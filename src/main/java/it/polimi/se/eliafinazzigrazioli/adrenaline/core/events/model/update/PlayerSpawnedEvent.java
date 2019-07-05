package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

/**
 * The type Player spawned event.
 */
public class PlayerSpawnedEvent extends AbstractModelEvent {

    private Coordinates spawnPoint;
    private PowerUpCardClient discardedPowerUp;
    private boolean firsSpawn;
    private boolean ownedDiscardedPowerUp;

    /**
     * Instantiates a new Player spawned event.
     *
     * @param player the player
     * @param spawnPoint the spawn point
     * @param discardedPowerUp the discarded power up
     * @param firsSpawn the firs spawn
     * @param ownedDiscardedPowerUp the owned discarded power up
     */
    public PlayerSpawnedEvent(Player player, Coordinates spawnPoint, PowerUpCard discardedPowerUp, boolean firsSpawn, boolean ownedDiscardedPowerUp) {
        super(player.getPlayerNickname(), player.getClientID());
        this.spawnPoint = spawnPoint;
        this.discardedPowerUp = new PowerUpCardClient(discardedPowerUp);
        this.firsSpawn = firsSpawn;
        this.ownedDiscardedPowerUp = ownedDiscardedPowerUp;
    }

    /**
     * Instantiates a new Player spawned event.
     *
     * @param player the player
     * @param spawnPoint the spawn point
     * @param discardedPowerUp the discarded power up
     * @param firsSpawn the firs spawn
     */
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

    /**
     * Gets spawn point.
     *
     * @return the spawn point
     */
    public Coordinates getSpawnPoint() {
        return spawnPoint;
    }

    /**
     * Gets discarded power up.
     *
     * @return the discarded power up
     */
    public PowerUpCardClient getDiscardedPowerUp() {
        return discardedPowerUp;
    }

    /**
     * Is firs spawn boolean.
     *
     * @return the boolean
     */
    public boolean isFirsSpawn() {
        return firsSpawn;
    }

    /**
     * Owned discarded power up boolean.
     *
     * @return the boolean
     */
    public boolean ownedDiscardedPowerUp() {
        return ownedDiscardedPowerUp;
    }
}
