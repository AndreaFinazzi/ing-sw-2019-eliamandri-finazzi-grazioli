package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.PowerUpCard;

public class PowerUpPlayEvent extends AbstractViewEvent {
    private String player;
    private PowerUpCard card;

    @Override
    public String getPlayer() {
        return player;
    }

    public PowerUpCard getCard() {
        return card;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
