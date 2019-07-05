package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;

import java.util.ArrayList;

/**
 * The type Power up effect.
 */
public abstract class PowerUpEffect {

    private ArrayList<EffectState> effectStates;
    private WeaponEffect effect;

    /**
     * Instantiates a new Power up effect.
     *
     * @param effect the effect
     */
    public PowerUpEffect(WeaponEffect effect) {
        this.effect = effect;
    }

    /**
     * Activate.
     */
    public abstract void activate();

}
