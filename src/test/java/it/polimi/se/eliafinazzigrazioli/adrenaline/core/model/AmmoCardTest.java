package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The type Ammo card test.
 */
public class AmmoCardTest{

    /**
     * The Ammo card.
     */
    AmmoCard ammoCard;
    /**
     * The Ammo list.
     */
    List<Ammo> ammoList;

    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        ammoList = new ArrayList<>();
        for(Ammo value : Ammo.values()) {
            ammoList.add(value);
        }
        ammoCard = new AmmoCard(ammoList, true, "Test");
    }

    /**
     * Gets id.
     */
    @Test
    public void getId() {
        assertEquals("Test", ammoCard.getId());
    }

    /**
     * Gets ammos.
     */
    @Test
    public void getAmmos() {
        assertTrue(ammoList.containsAll(ammoCard.getAmmos()));
        assertTrue(ammoCard.getAmmos().containsAll(ammoList));
    }

    /**
     * Contains power up card.
     */
    @Test
    public void containsPowerUpCard() {
        assertTrue(ammoCard.containsPowerUpCard());
    }
}