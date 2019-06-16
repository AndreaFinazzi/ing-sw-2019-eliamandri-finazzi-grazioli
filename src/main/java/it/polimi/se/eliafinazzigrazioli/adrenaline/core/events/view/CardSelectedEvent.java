package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class CardSelectedEvent extends AbstractViewEvent {

    private String card;

    public CardSelectedEvent(int clientID, String player, String card) {
        super(clientID, player);
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
