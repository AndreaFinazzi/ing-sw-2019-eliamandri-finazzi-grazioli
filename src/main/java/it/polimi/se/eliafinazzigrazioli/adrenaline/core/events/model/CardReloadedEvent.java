package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class CardReloadedEvent extends AbstractModelEvent {

    private String WeaponReloaded;

    public CardReloadedEvent(String player, String weaponReloaded) {
        super(player);
        WeaponReloaded = weaponReloaded;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}
