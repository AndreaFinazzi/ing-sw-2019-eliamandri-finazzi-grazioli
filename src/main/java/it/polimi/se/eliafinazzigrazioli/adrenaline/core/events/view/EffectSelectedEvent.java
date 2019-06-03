package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;

public class EffectSelectedEvent extends AbstractViewEvent {

    private String effect;

    public EffectSelectedEvent(String player, String effect) {
        super(player);
        this.effect = effect;
    }

    public String getEffect() {
        return effect;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
