package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

import java.util.List;

/**
 * The type Effect selected event.
 */
public class EffectSelectedEvent extends AbstractViewEvent {

    private String effect;
    private List<PowerUpCardClient> powerUpsToPay;

    /**
     * Instantiates a new Effect selected event.
     *
     * @param clientID the client id
     * @param player the player
     * @param effect the effect
     * @param powerUpsToPay the power ups to pay
     */
    public EffectSelectedEvent(int clientID, String player, String effect, List<PowerUpCardClient> powerUpsToPay) {
        super(clientID, player);
        this.effect = effect;
        this.powerUpsToPay = powerUpsToPay;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets effect.
     *
     * @return the effect
     */
    public String getEffect() {
        return effect;
    }

    /**
     * Gets power ups to pay.
     *
     * @return the power ups to pay
     */
    public List<PowerUpCardClient> getPowerUpsToPay() {
        return powerUpsToPay;
    }
}
