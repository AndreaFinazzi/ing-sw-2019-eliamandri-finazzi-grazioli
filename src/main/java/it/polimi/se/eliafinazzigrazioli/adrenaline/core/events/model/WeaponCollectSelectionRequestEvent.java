package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class WeaponCollectSelectionRequestEvent extends AbstractModelEvent {

    public WeaponCollectSelectionRequestEvent(String player) {
        super(player);
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        if (this.getPlayer() == listener.getPlayer())
            listener.handleEvent(this);
    }
}
