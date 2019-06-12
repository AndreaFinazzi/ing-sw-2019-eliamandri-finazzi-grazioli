package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;

public class ReloadWeaponEvent extends AbstractViewEvent {

    private String weapon;

    public ReloadWeaponEvent(String player, WeaponCardClient weapon) {
        super(player);
        if (weapon != null)
            this.weapon = weapon.getWeaponName();
        else
            this.weapon = null;
    }

    public String getWeapon() {
        return weapon;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
