package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.Card;

public class CardSelectedEvent implements ViewEventInterface {
    private String player;
    private Card card;

    @Override
    public String getPlayer() {
        return player;
    }

    public Card getCard() {
        return card;
    }
}
