package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

import java.util.List;

public class PowerUpsDeck extends Deck<PowerUpCard> {

    public PowerUpsDeck(List<PowerUpCard> cards) {
        this.cards = cards;
    }

}
