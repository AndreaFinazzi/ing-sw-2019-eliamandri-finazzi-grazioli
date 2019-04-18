package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;

public class ReloadWeaponEvent extends AbstractViewEvent {
    private String player;
    private WeaponCard weapon;

    @Override
    public String getPlayer() {
        return player;
    }

    public WeaponCard getWeapon() {
        return weapon;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
