package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

/**
 * The type Weapon reloaded event.
 */
public class WeaponReloadedEvent extends AbstractModelEvent{

    private String weaponReloaded;

    /**
     * Instantiates a new Weapon reloaded event.
     *
     * @param player the player
     * @param weaponReloaded the weapon reloaded
     */
    public WeaponReloadedEvent(Player player, String weaponReloaded) {
        super(player);
        this.weaponReloaded = weaponReloaded;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets weapon reloaded.
     *
     * @return the weapon reloaded
     */
    public String getWeaponReloaded() {
        return weaponReloaded;
    }
}
