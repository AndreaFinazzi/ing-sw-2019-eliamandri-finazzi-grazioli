package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;

public class EffectSelectedEvent extends AbstractViewEvent {

    private WeaponEffect effect;

    public EffectSelectedEvent(String player, WeaponEffect effect) {
        super(player);
        this.effect = effect;
    }

    public WeaponEffect getEffect() {
        return effect;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
