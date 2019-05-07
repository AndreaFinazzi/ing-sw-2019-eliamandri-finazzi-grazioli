package it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.Card;

import java.util.List;

public class CardDrawedEvent extends AbstractControllerEvent {

    private List<Card> cards;

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
