package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;

public class ReloadWeaponEvent implements ViewEventInterface{
    private String player;
    private WeaponCard weapon;

    @Override
    public String getPlayer() {
        return player;
    }

    public WeaponCard getWeapon() {
        return weapon;
    }
}
