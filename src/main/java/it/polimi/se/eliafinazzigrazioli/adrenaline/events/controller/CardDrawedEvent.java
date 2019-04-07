package it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.Card;

import java.util.List;

public class CardDrawedEvent implements ControllerEventInterface{
    private String player;
    private List<Card> cards;

    @Override
    public String getPlayer() {
        return player;
    }

    public List<Card> getCards() {
        return cards;
    }
}
