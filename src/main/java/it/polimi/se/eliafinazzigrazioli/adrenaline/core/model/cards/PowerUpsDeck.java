package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

import java.util.ArrayList;
import java.util.List;

public class PowerUpsDeck extends Deck<PowerUpCard> {

    List<PowerUpCard> discardedPowerUps;

    public PowerUpsDeck(List<PowerUpCard> cards) {
        this.cards = cards;
        discardedPowerUps = new ArrayList<>();
    }

    public void resetDeck() {
        cards = discardedPowerUps;
        discardedPowerUps = new ArrayList<>();
    }

}
