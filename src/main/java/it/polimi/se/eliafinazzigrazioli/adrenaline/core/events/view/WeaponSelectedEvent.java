package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;

public class WeaponSelectedEvent extends AbstractViewEvent {

    private String player;
    private WeaponCard weapon;

    public WeaponSelectedEvent(String player, String player1, WeaponCard weapon) {
        super(player);
        this.player = player1;
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
