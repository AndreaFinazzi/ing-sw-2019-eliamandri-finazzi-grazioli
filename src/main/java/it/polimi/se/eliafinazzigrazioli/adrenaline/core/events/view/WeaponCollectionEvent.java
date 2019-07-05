package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;

/**
 * The type Weapon collection event.
 */
public class WeaponCollectionEvent extends AbstractViewEvent {

    private List<Coordinates> path;
    private String weaponCollected;
    private String weaponDropped;
    private List<PowerUpCardClient> powerUpsToPay;

    /**
     * Instantiates a new Weapon collection event.
     *
     * @param clientID the client id
     * @param player the player
     * @param path the path
     * @param weaponCollected the weapon collected
     * @param weaponDropped the weapon dropped
     * @param powerUpsToPay the power ups to pay
     */
    public WeaponCollectionEvent(int clientID, String player, List<Coordinates> path, WeaponCardClient weaponCollected, WeaponCardClient weaponDropped, List<PowerUpCardClient> powerUpsToPay) {
        super(clientID, player);
        this.path = path;
        this.weaponCollected = weaponCollected.getWeaponName();
        if (weaponDropped != null)
            this.weaponDropped = weaponDropped.getWeaponName();
        else
            this.weaponDropped = null;
        this.powerUpsToPay = powerUpsToPay;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public List<Coordinates> getPath() {
        return path;
    }

    /**
     * Gets weapon collected.
     *
     * @return the weapon collected
     */
    public String getWeaponCollected() {
        return weaponCollected;
    }

    /**
     * Gets weapon dropped.
     *
     * @return the weapon dropped
     */
    public String getWeaponDropped() {
        return weaponDropped;
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
