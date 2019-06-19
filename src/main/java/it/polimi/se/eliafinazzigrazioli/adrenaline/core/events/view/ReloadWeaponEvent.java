package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

import java.util.List;

public class ReloadWeaponEvent extends AbstractViewEvent {

    private String weapon;

    private List<PowerUpCardClient> powerUpsToPay;

    public ReloadWeaponEvent(int clientID, String player, WeaponCardClient weapon, List<PowerUpCardClient> powerUpsToPay) {
        super(clientID, player);
        if (weapon != null)
            this.weapon = weapon.getWeaponName();
        else
            this.weapon = null;
        this.powerUpsToPay = powerUpsToPay;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public String getWeapon() {
        return weapon;
    }

    public List<PowerUpCardClient> getPowerUpsToPay() {
        return powerUpsToPay;
    }
}
