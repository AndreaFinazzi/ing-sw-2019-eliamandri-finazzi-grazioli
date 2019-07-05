package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

import java.util.List;

/**
 * The type Reload weapon event.
 */
public class ReloadWeaponEvent extends AbstractViewEvent {

    private String weapon;

    private List<PowerUpCardClient> powerUpsToPay;

    /**
     * Instantiates a new Reload weapon event.
     *
     * @param clientID the client id
     * @param player the player
     * @param weapon the weapon
     * @param powerUpsToPay the power ups to pay
     */
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

    /**
     * Gets weapon.
     *
     * @return the weapon
     */
    public String getWeapon() {
        return weapon;
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
