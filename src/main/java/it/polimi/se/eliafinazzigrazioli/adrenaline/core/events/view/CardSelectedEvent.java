package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.Card;

public class CardSelectedEvent extends AbstractViewEvent {

    private String card;

    public CardSelectedEvent(String player, String card) {
        super(player);
        this.card = card;
    }

    public String getCard() {
        return card;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
