package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;

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
