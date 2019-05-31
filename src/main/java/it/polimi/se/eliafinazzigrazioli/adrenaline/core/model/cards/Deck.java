package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards;

import java.util.List;
import java.util.Random;

public class Deck<CardType> {

    List<CardType> cards;

    public CardType drawCard() {
        return cards.remove(new Random().nextInt(cards.size()));
    }

}
