package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PowerUpsDeck extends Deck<PowerUpCard> {

    private List<PowerUpCard> discardedPowerUps;

    public PowerUpsDeck(List<PowerUpCard> cards) {
        this.cards = cards;
        discardedPowerUps = new ArrayList<>();
    }

    public PowerUpsDeck() {
        cards = new ArrayList<>();
        for (Ammo color: Ammo.values()) {
            for (int i = 0; i < Rules.POWER_UPS_PER_COLOR; i++){
                cards.add(new PowerUpCard("Targeting Scope", color));
                cards.add(new PowerUpCard("Newton", color));
                cards.add(new PowerUpCard("Tagback Grenade", color));
                cards.add(new PowerUpCard("Teleporter", color));
            }
        }
    }

    public PowerUpCard drawCard() {
        PowerUpCard powerUpCard = cards.remove(new Random().nextInt(cards.size()));
        if (cards.size() == 0)
            resetDeck();
        return powerUpCard;
    }


    private void resetDeck() {
        cards = discardedPowerUps;
        discardedPowerUps = new ArrayList<>();
    }

    public void discardPowerUp(PowerUpCard powerUpCard){
        discardedPowerUps.add(powerUpCard);
    }

}
