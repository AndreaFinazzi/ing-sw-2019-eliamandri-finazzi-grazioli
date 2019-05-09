package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;

public abstract class ActionEffectState extends EffectState {

    protected WeaponEffect playerToAffectSource;
    protected int toAffectPlayerSelectionOrder;

    public ActionEffectState(WeaponEffect playerToAffectSource, int toAffectPlayerSelectionOrder) {
        this.playerToAffectSource = playerToAffectSource;
        this.toAffectPlayerSelectionOrder = toAffectPlayerSelectionOrder;
    }
}
