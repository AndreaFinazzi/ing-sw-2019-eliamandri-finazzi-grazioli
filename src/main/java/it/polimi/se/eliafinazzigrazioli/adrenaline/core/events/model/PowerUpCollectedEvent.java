package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

public class PowerUpCollectedEvent extends AbstractModelEvent {

    private PowerUpCard collectedCard;
    private boolean actuallyCollected;

    public PowerUpCollectedEvent(String player, PowerUpCard collectedCard, boolean actuallyCollected) {
        super(player);
        this.collectedCard = collectedCard;
        this.actuallyCollected = actuallyCollected; // serve per sapere se è realmente stato raccolto; (3+ powerUp)
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public PowerUpCard getCollectedCard() {
        return collectedCard;
    }

    public boolean isActuallyCollected() {
        return actuallyCollected;
    }
}