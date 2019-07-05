package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.EffectState;

/**
 * The type Action effect state.
 */
public abstract class ActionEffectState extends EffectState {

    /**
     * The Player to affect source.
     */
    protected String playerToAffectSource;
    /**
     * The To affect player selection order.
     */
    protected int toAffectPlayerSelectionOrder;

    /**
     * Instantiates a new Action effect state.
     *
     * @param playerToAffectSource the player to affect source
     * @param toAffectPlayerSelectionOrder the to affect player selection order
     */
    public ActionEffectState(String playerToAffectSource, int toAffectPlayerSelectionOrder) {
        this.playerToAffectSource = playerToAffectSource;
        this.toAffectPlayerSelectionOrder = toAffectPlayerSelectionOrder;
    }
}
