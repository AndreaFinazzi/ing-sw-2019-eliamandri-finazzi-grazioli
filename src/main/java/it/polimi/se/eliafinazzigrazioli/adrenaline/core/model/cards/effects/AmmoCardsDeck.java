package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.AmmoCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**Laura<3*/

public class AmmoCardsDeck {

    List<AmmoCard> discardedCards;

    List<AmmoCard> deck;

    public AmmoCardsDeck() {
        deck = new ArrayList<>();
        discardedCards = new ArrayList<>();
        for (int i=0; i < 3; i++)
            for (Ammo doubleAmmo: Ammo.values())
                for (Ammo singleAmmo: Ammo.values())
                    if (doubleAmmo != singleAmmo)
                    deck.add(new AmmoCard(Arrays.asList(doubleAmmo, doubleAmmo, singleAmmo), false));
        for (int i=0; i < 3; i++)
            for (Ammo firstAmmo: Ammo.values())
                for (Ammo secondAmmo: Ammo.values())
                    deck.add(new AmmoCard(Arrays.asList(firstAmmo, secondAmmo), true));
    }

    public AmmoCard drawCard() {
        AmmoCard drawedCard;
        drawedCard = deck.get(new Random().nextInt(deck.size()));
        deck.remove(drawedCard);
        return drawedCard;
    }

    public void discard(AmmoCard card) {
        discardedCards.add(card);
    }
}
