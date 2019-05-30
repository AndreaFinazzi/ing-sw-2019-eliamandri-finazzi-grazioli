package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class WeaponCollectedEvent extends AbstractModelEvent {

    private String collectedWeapon;
    private String dropOfWeapon;

    public WeaponCollectedEvent(String player, String collectedWeapon, String dropOfWeapon) {
        super(player);
        this.collectedWeapon = collectedWeapon;
        this.dropOfWeapon = dropOfWeapon;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public String getCollectedWeapon() {
        return collectedWeapon;
    }

    public String getDropOfWeapon() {
        return dropOfWeapon;
    }
}
