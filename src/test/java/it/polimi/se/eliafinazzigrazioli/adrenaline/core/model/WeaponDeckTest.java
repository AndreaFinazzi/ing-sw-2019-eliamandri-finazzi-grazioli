package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.WeaponFileNotFoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponsDeck;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WeaponDeckTest {

    @Test
    public void deckInstantiationTest() {
        int count = 0;
        WeaponsDeck deck = new WeaponsDeck();
        while (!deck.isEmpty()) {
            try {
                WeaponCard weaponCard = WeaponCard.jsonParser(deck.drawCard());
                System.out.println(weaponCard + "\n");
                count++;
            } catch (WeaponFileNotFoundException e) {
                e.printStackTrace();
            }
        }
        assertEquals(10, count);
    }
}
