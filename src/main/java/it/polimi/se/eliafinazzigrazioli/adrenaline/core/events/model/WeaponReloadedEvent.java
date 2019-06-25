package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

public class WeaponReloadedEvent extends AbstractModelEvent{

    private String weaponReloaded;

    public WeaponReloadedEvent(Player player, String weaponReloaded) {
        super(player);
        this.weaponReloaded = weaponReloaded;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public String getWeaponReloaded() {
        return weaponReloaded;
    }
}
