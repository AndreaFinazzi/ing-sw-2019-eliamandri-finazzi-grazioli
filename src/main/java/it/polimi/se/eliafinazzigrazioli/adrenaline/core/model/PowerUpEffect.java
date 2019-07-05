package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;

import java.util.ArrayList;

public abstract class PowerUpEffect {

    private ArrayList<EffectState> effectStates;
    private WeaponEffect effect;

    public PowerUpEffect(WeaponEffect effect) {
        this.effect = effect;
    }

    public abstract void activate();

}
