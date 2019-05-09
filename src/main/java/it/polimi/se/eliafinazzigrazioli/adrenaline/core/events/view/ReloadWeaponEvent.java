package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;

public class ReloadWeaponEvent extends AbstractViewEvent {

    private WeaponCard weapon;

    public ReloadWeaponEvent(String player, WeaponCard weapon) {
        super(player);
        this.weapon = weapon;
    }

    public WeaponCard getWeapon() {
        return weapon;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
