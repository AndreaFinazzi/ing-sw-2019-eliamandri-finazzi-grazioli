package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.server.AbstractClientHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class AmmoCardTest{

    AmmoCard ammoCard;
    List<Ammo> ammoList;

    @Before
    public void setUp() throws Exception {
        ammoList = new ArrayList<>();
        for(Ammo value : Ammo.values()) {
            ammoList.add(value);
        }
        ammoCard = new AmmoCard(ammoList, true, "Test");
    }

    @Test
    public void getId() {
        assertEquals("Test", ammoCard.getId());
    }

    @Test
    public void getAmmos() {
        assertTrue(ammoList.containsAll(ammoCard.getAmmos()));
        assertTrue(ammoCard.getAmmos().containsAll(ammoList));
    }

    @Test
    public void containsPowerUpCard() {
        assertTrue(ammoCard.containsPowerUpCard());
    }
}