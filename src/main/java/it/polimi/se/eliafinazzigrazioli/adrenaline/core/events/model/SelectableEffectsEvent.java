package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

import java.util.List;

public class SelectableEffectsEvent extends AbstractModelEvent {

    List<String> callableEffects;

    public SelectableEffectsEvent(String player, List<String> callableEffects) {
        super(player);
        this.callableEffects = callableEffects;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        if (this.getPlayer() == listener.getPlayer())
            listener.handleEvent(this);
    }

}
