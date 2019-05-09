package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class CardReloadedEvent extends AbstractModelEvent {

    private String WeaponReloaded;

    public CardReloadedEvent(String player, String weaponReloaded) {
        super(player);
        WeaponReloaded = weaponReloaded;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}