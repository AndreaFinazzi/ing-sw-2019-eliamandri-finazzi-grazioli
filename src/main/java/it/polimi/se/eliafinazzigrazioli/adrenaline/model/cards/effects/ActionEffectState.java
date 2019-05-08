package it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.EffectState;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponEffect;

import java.util.List;

public abstract class ActionEffectState extends EffectState {

    protected WeaponEffect playerToAffectSource;
    protected int toAffectPlayerSelectionOrder;

    public ActionEffectState(WeaponEffect playerToAffectSource, int toAffectPlayerSelectionOrder) {
        this.playerToAffectSource = playerToAffectSource;
        this.toAffectPlayerSelectionOrder = toAffectPlayerSelectionOrder;
    }
}
