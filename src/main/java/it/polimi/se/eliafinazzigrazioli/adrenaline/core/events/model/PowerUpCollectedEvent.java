package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

import java.util.List;

public class PowerUpCollectedEvent extends AbstractModelEvent {

    PowerUpCard collectedCard;

    public PowerUpCollectedEvent(String player, PowerUpCard collectedCard) {
        super(player);
        this.collectedCard = collectedCard;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
