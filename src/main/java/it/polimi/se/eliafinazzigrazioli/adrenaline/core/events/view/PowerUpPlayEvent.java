package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

public class PowerUpPlayEvent extends AbstractViewEvent {

    private PowerUpCard card;

    public PowerUpPlayEvent(String player, PowerUpCard card) {
        super(player);
        this.card = card;
    }

    public PowerUpCard getCard() {
        return card;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
