package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponEffect;

public class EffectSelectedEvent extends AbstractViewEvent {

    private WeaponEffect effect;

    public EffectSelectedEvent() {
        effect = new WeaponEffect();
    }

    public WeaponEffect getEffect() {
        return effect;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
