package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

import java.util.List;

public class EffectSelectedEvent extends AbstractViewEvent {

    private String effect;
    private List<PowerUpCardClient> powerUpsToPay;

    public EffectSelectedEvent(int clientID, String player, String effect, List<PowerUpCardClient> powerUpsToPay) {
        super(clientID, player);
        this.effect = effect;
        this.powerUpsToPay = powerUpsToPay;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public String getEffect() {
        return effect;
    }

    public List<PowerUpCardClient> getPowerUpsToPay() {
        return powerUpsToPay;
    }
}
