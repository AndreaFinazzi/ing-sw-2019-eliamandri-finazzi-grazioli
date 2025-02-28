package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.AmmoNotAvailableException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * The type Player board test.
 */
public class PlayerBoardTest {
    private static final Logger LOGGER = Logger.getLogger(PlayerBoardTest.class.getName());

    /**
     * Test add one damage.
     */
    @Test
    public void testAddOneDamage() {
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.addDamage(DamageMark.BLUE);
        assertEquals(1, playerBoard.getScores().size());
        assertEquals(DamageMark.BLUE, playerBoard.getScores().get(0));
        assertFalse("not dead", playerBoard.isDeath());
        assertFalse("not Overkilled", playerBoard.isOverkill());
    }

    /**
     * Test add all damage.
     */
    @Test
    public void testAddAllDamage() {
        PlayerBoard playerBoard = new PlayerBoard();
        ArrayList<DamageMark> tempList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            playerBoard.addDamage(DamageMark.GREY);
            tempList.add(DamageMark.GREY);
            assertEquals(i + 1, playerBoard.getScores().size());
        }
        assertEquals(10, playerBoard.getScores().size());
        assertFalse(playerBoard.isDeath());
        assertFalse(playerBoard.isOverkill());

        playerBoard.addDamage(DamageMark.GREEN);
        tempList.add(DamageMark.GREEN);

        assertEquals(11, playerBoard.getScores().size());
        assertTrue("Is death", playerBoard.isDeath());
        assertFalse(playerBoard.isOverkill());

        playerBoard.addDamage(DamageMark.GREEN);
        tempList.add(DamageMark.GREEN);


        assertEquals(12, playerBoard.getScores().size());
        for (int i = 0; i < tempList.size(); i++) {
            assertEquals(tempList.get(i), playerBoard.getScores().get(i));
        }
        assertTrue("Is death", playerBoard.isDeath());
        assertTrue("is overkill", playerBoard.isOverkill());
    }

    /**
     * Test add one skull.
     */
    @Test
    public void testAddOneSkull() {
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.addSkull();
        assertTrue(playerBoard.getSkulls() == 1);
    }

    /**
     * Test add all skulls.
     */
    @Test
    public void testAddAllSkulls() {
        PlayerBoard playerBoard = new PlayerBoard();
        for (int i = 0; i < 6; i++) {
            playerBoard.addSkull();
            assertEquals(i + 1, playerBoard.getSkulls());
        }
        assertEquals(6, playerBoard.getSkulls());
    }

    /**
     * Test decrease death score.
     */
    @Test
    public void testDecreaseDeathScore() {
        PlayerBoard playerBoard = new PlayerBoard();
        assertEquals(playerBoard.getDeathScore(), new Integer(8));
        playerBoard.decreaseDeathScore();
        assertEquals(playerBoard.getDeathScore(), new Integer(6));
        playerBoard.decreaseDeathScore();
        assertEquals(playerBoard.getDeathScore(), new Integer(4));
        playerBoard.decreaseDeathScore();
        assertEquals(playerBoard.getDeathScore(), new Integer(2));
        playerBoard.decreaseDeathScore();
        assertEquals(playerBoard.getDeathScore(), new Integer(1));
        playerBoard.decreaseDeathScore();
        assertEquals(playerBoard.getDeathScore(), new Integer(1));
        playerBoard.decreaseDeathScore();
        assertEquals(playerBoard.getDeathScore(), new Integer(1));
    }

    /**
     * Test add one mark.
     */
    @Test
    public void testAddOneMark() {
        PlayerBoard playerBoard = new PlayerBoard();
        playerBoard.addMark(DamageMark.YELLOW);

        assertEquals(1, playerBoard.numMarkType(DamageMark.YELLOW));
        assertEquals(0, playerBoard.numMarkType(DamageMark.PURPLE));
        assertEquals(0, playerBoard.numMarkType(DamageMark.GREEN));
        assertEquals(0, playerBoard.numMarkType(DamageMark.BLUE));
        assertEquals(0, playerBoard.numMarkType(DamageMark.GREY));

    }

    /**
     * Test add all marks.
     */
    @Test
    public void testAddAllMarks() {
        PlayerBoard playerBoard = new PlayerBoard();
        ArrayList<DamageMark> tempList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if (playerBoard.addMark(DamageMark.PURPLE) != null)
                tempList.add(DamageMark.PURPLE);
        }
        for (int i = 0; i < tempList.size(); i++) {
            assertEquals(tempList.get(i), playerBoard.getMarks().get(i));
        }
    }

    /**
     * Test add ammos.
     */
    @Test
    public void testAddAmmos() {
        PlayerBoard playerBoard = new PlayerBoard();
        ArrayList<Ammo> toAdd = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i >= 0 && i < 10)
                toAdd.add(Ammo.BLUE);
            else if (i >= 10 && i < 20)
                toAdd.add(Ammo.RED);
            else if (i >= 20 && i < 30) ;
            toAdd.add(Ammo.YELLOW);
        }
        playerBoard.addAmmos(toAdd);
        assertEquals(3, playerBoard.numAmmoType(Ammo.BLUE));
        assertEquals(3, playerBoard.numAmmoType(Ammo.RED));
        assertEquals(3, playerBoard.numAmmoType(Ammo.YELLOW));
        assertEquals(9, playerBoard.getAmmos().size());
    }

    /**
     * Test spend ammo.
     */
    @Test
    public void testSpendAmmo() {
        PlayerBoard playerBoard = new PlayerBoard();
        ArrayList<Ammo> toAdd = new ArrayList<>();
        ArrayList<Ammo> toSpend = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (i >= 0 && i < 3)
                toAdd.add(Ammo.BLUE);
            else if (i >= 3 && i < 6)
                toAdd.add(Ammo.RED);
            else if (i >= 6 && i < 9) ;
            toAdd.add(Ammo.YELLOW);
        }
        playerBoard.addAmmos(toAdd);
        toSpend.add(Ammo.YELLOW);
        assertEquals(3, playerBoard.numAmmoType(Ammo.YELLOW));
        try {
            playerBoard.spendAmmo(toSpend);
        } catch (AmmoNotAvailableException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            fail();
        }
        assertEquals(2, playerBoard.numAmmoType(Ammo.YELLOW));
        toSpend.add(Ammo.RED);
        try {
            playerBoard.spendAmmo(toSpend);
        } catch (AmmoNotAvailableException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            fail();
        }
        assertEquals(2, playerBoard.numAmmoType(Ammo.RED));
        assertEquals(1, playerBoard.numAmmoType(Ammo.YELLOW));
    }

    /**
     * Test spend all ammo.
     */
    @Test
    public void testSpendAllAmmo() {
        PlayerBoard playerBoard = new PlayerBoard();
        ArrayList<Ammo> toAdd = new ArrayList<>();
        ArrayList<Ammo> toSpend = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (i >= 0 && i < 3) {
                toAdd.add(Ammo.BLUE);
                toSpend.add(Ammo.BLUE);
            } else if (i >= 3 && i < 6) {
                toAdd.add(Ammo.RED);
                toSpend.add(Ammo.RED);
            } else if (i >= 6 && i < 9) {
                toAdd.add(Ammo.YELLOW);
                toSpend.add(Ammo.YELLOW);
            }
        }
        playerBoard.addAmmos(toAdd);
        assertEquals(9, playerBoard.getAmmos().size());
        try {
            playerBoard.spendAmmo(toSpend);
        } catch (AmmoNotAvailableException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            fail();
        }
        assertEquals(0, playerBoard.getAmmos().size());
        assertEquals(0, playerBoard.numAmmoType(Ammo.YELLOW));
        assertEquals(0, playerBoard.numAmmoType(Ammo.BLUE));
        assertEquals(0, playerBoard.numAmmoType(Ammo.RED));
    }

    /**
     * Test clean board.
     */
    @Test
    public void testCleanBoard() {
        PlayerBoard playerBoard = new PlayerBoard();
        ArrayList<Ammo> toAdd = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if (i < Rules.PLAYER_BOARD_DEATH_SCORES.size()) {
                playerBoard.addSkull();
            }
            playerBoard.addDamage(DamageMark.GREEN);
            playerBoard.addMark(DamageMark.YELLOW);

        }
        toAdd.add(Ammo.YELLOW);
        toAdd.add(Ammo.RED);
        toAdd.add(Ammo.BLUE);
        playerBoard.addAmmos(toAdd);

        assertEquals(3, playerBoard.getAmmos().size());
        assertEquals(3, playerBoard.getMarks().size());
        assertEquals(12, playerBoard.getScores().size());

        playerBoard.cleanPlayerBoard();
        assertEquals(0, playerBoard.getScores().size());
        assertFalse(playerBoard.isDeath());
        assertFalse(playerBoard.isOverkill());
        assertEquals(Rules.PLAYER_BOARD_DEATH_SCORES.size(), playerBoard.getSkulls());
        assertEquals(3, playerBoard.getAmmos().size());
        assertEquals(3, playerBoard.getMarks().size());

    }

    /**
     * Test clean board marks.
     */
    @Test
    public void testCleanBoardMarks() {
        PlayerBoard playerBoard = new PlayerBoard();
        ArrayList<Ammo> toAdd = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if (i < 6)
                playerBoard.addSkull();
            playerBoard.addDamage(DamageMark.GREEN);
            playerBoard.addMark(DamageMark.YELLOW);

        }
        toAdd.add(Ammo.YELLOW);
        toAdd.add(Ammo.RED);
        toAdd.add(Ammo.BLUE);
        playerBoard.addAmmos(toAdd);

        assertEquals(3, playerBoard.getAmmos().size());
        assertEquals(3, playerBoard.getMarks().size());
        assertEquals(12, playerBoard.getScores().size());

        playerBoard.cleanPlayerBoard(true);
        assertEquals(0, playerBoard.getScores().size());
        assertFalse(playerBoard.isDeath());
        assertFalse(playerBoard.isOverkill());
        assertEquals(6, playerBoard.getSkulls());
        assertEquals(3, playerBoard.getAmmos().size());
        assertEquals(0, playerBoard.getMarks().size());
    }
}