package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;

public abstract class ActionEffectState extends EffectState {

    protected String playerToAffectSource;
    protected int toAffectPlayerSelectionOrder;

    public ActionEffectState(String playerToAffectSource, int toAffectPlayerSelectionOrder) {
        this.playerToAffectSource = playerToAffectSource;
        this.toAffectPlayerSelectionOrder = toAffectPlayerSelectionOrder;
    }
}
