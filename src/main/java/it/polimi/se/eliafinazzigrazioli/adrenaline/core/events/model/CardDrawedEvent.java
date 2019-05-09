package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.Card;

import java.util.List;

public class CardDrawedEvent extends AbstractModelEvent {

    private List<Card> cards;

    public CardDrawedEvent(String player) {
        super(player);
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
