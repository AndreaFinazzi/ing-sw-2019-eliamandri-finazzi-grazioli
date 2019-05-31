package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class WeaponToUseSelectedEvent extends AbstractViewEvent {

    private String weaponName;

    public WeaponToUseSelectedEvent(String player, String weaponName) {
        super(player);
        this.weaponName = weaponName;
    }

    public String getWeaponName() {
        return weaponName;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
