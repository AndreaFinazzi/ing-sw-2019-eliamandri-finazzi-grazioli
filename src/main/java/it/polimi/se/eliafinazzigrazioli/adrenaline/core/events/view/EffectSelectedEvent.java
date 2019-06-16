package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class EffectSelectedEvent extends AbstractViewEvent {

    private String effect;

    public EffectSelectedEvent(int clientID, String player, String effect) {
        super(clientID, player);
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
