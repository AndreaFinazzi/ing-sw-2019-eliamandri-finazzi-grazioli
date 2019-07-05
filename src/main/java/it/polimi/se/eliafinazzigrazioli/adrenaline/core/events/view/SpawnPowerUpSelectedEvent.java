package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

/**
 * The type Spawn power up selected event.
 */
public class SpawnPowerUpSelectedEvent extends AbstractViewEvent {

    private PowerUpCardClient toKeep;
    private PowerUpCardClient spawnCard;
    private boolean firstSpawn;

    /**
     * Instantiates a new Spawn power up selected event.
     *
     * @param clientID the client id
     * @param player the player
     * @param toKeep the to keep
     * @param spawnCard the spawn card
     * @param firstSpawn the first spawn
     */
    public SpawnPowerUpSelectedEvent(int clientID, String player, PowerUpCardClient toKeep, PowerUpCardClient spawnCard, boolean firstSpawn) {
        super(clientID, player);
        this.toKeep = toKeep;
        this.spawnCard = spawnCard;
        this.firstSpawn = firstSpawn;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets to keep.
     *
     * @return the to keep
     */
    public PowerUpCard getToKeep() {
        return PowerUpCard.clientCopyToServer(toKeep);
    }

    /**
     * Gets spawn card.
     *
     * @return the spawn card
     */
    public PowerUpCard getSpawnCard() {
        return PowerUpCard.clientCopyToServer(spawnCard);
    }

    /**
     * Is first spawn boolean.
     *
     * @return the boolean
     */
    public boolean isFirstSpawn() {
        return firstSpawn;
    }
}
