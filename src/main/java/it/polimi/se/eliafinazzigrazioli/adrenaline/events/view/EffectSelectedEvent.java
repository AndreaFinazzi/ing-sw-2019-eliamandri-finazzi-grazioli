package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponEffect;

public class EffectSelectedEvent implements ViewEventInterface {
    private String player;
    private WeaponEffect effect;

    @Override
    public String getPlayer() {
        return player;
    }

    public WeaponEffect getEffect() {
        return effect;
    }
}
