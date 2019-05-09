package it.polimi.se.eliafinazzigrazioli.adrenaline.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

public class WeaponCollectedEvent extends AbstractModelEvent {

    private String collectedWeapon;
    private String dropOfWeapon;

    public WeaponCollectedEvent(String player, String collectedWeapon, String dropOfWeapon) {
        super(player);
        this.collectedWeapon = collectedWeapon;
        this.dropOfWeapon = dropOfWeapon;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}
