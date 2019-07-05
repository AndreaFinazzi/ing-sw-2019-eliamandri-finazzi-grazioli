package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

/**
 * The type Power up play event.
 */
public class PowerUpPlayEvent extends AbstractViewEvent {

    private PowerUpCardClient card;

    /**
     * Instantiates a new Power up play event.
     *
     * @param clientID the client id
     * @param player the player
     * @param card the card
     */
    public PowerUpPlayEvent(int clientID, String player, PowerUpCard card) {
        super(clientID, player);
        this.card = new PowerUpCardClient(card);
    }

    /**
     * Gets card.
     *
     * @return the card
     */
    public PowerUpCardClient getCard() {
        return card;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
