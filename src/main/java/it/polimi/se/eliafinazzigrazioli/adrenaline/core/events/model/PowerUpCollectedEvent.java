package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

/**
 * The type Power up collected event.
 */
public class PowerUpCollectedEvent extends AbstractModelEvent {

    private PowerUpCardClient collectedCard;
    private boolean actuallyCollected;

    /**
     * Instantiates a new Power up collected event.
     *
     * @param player the player
     * @param collectedCard the collected card
     * @param actuallyCollected the actually collected
     */
    public PowerUpCollectedEvent(String player, PowerUpCard collectedCard, boolean actuallyCollected) {
        super(player);
        this.collectedCard = new PowerUpCardClient(collectedCard);
        this.actuallyCollected = actuallyCollected; // serve per sapere se è realmente stato raccolto; (3+ powerUp)
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets collected card.
     *
     * @return the collected card
     */
    public PowerUpCardClient getCollectedCard() {
        return collectedCard;
    }

    /**
     * Is actually collected boolean.
     *
     * @return the boolean
     */
    public boolean isActuallyCollected() {
        return actuallyCollected;
    }
}