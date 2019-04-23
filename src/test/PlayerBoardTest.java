import it.polimi.se.eliafinazzigrazioli.adrenaline.exception.model.AmmoNotAvaibleException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exception.model.OutofBoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.DamageMark;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.PlayerBoard;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerBoardTest {

    @Test
    public void testAddOneDamage() {
        PlayerBoard playerBoard = new PlayerBoard ();
        try {
            playerBoard.addDamage (DamageMark.BLUE);
        }catch (OutofBoundException e){
            e.printStackTrace ();
            fail ();
        }
        assertEquals(1,playerBoard.getScores ().size ());
        assertEquals (DamageMark.BLUE, playerBoard.getScores ().get (0));
        assertTrue ("OK", playerBoard.isFirstBlood ());
        assertFalse("not dead", playerBoard.isDeath ());
        assertFalse ("not Overkilled", playerBoard.isOverkill ());
    }

    @Test
    public void testAddAllDamage() {
        PlayerBoard playerBoard = new PlayerBoard ();
        ArrayList<DamageMark> tempList = new ArrayList<> ();
        for (int i=0; i<10; i++) {
            try {
                playerBoard.addDamage (DamageMark.GRAY);
                tempList.add (DamageMark.GRAY);
                assertEquals(i+1, playerBoard.getScores ().size ());
            }catch (OutofBoundException e) {
                e.printStackTrace ();
                fail ();
            }
        }
        assertEquals(10, playerBoard.getScores ().size ());
        assertTrue (playerBoard.isFirstBlood ());
        assertFalse (playerBoard.isDeath ());
        assertFalse (playerBoard.isOverkill ());

        try {
            playerBoard.addDamage (DamageMark.GREEN);
            tempList.add (DamageMark.GREEN);
        }catch (OutofBoundException e) {
            e.printStackTrace ();
            fail ();
        }
        assertEquals(11, playerBoard.getScores ().size ());
        assertTrue (playerBoard.isFirstBlood ());
        assertTrue ("Is death",playerBoard.isDeath ());
        assertFalse (playerBoard.isOverkill ());

        try {
            playerBoard.addDamage (DamageMark.GREEN);
            tempList.add (DamageMark.GREEN);
        }catch (OutofBoundException e) {
            e.printStackTrace ();
            fail ();
        }

        assertEquals(12, playerBoard.getScores ().size ());
        for (int i=0; i<tempList.size (); i++) {
            assertEquals (tempList.get (i), playerBoard.getScores ().get (i));
        }
        assertTrue (playerBoard.isFirstBlood ());
        assertTrue ("Is death",playerBoard.isDeath ());
        assertTrue ("is overkill", playerBoard.isOverkill ());
    }

    @Test
    public void testAddOneSkull() {
        PlayerBoard playerBoard = new PlayerBoard ();
        try {
            playerBoard.addSkull ();
        }catch (OutofBoundException e) {
            e.printStackTrace ();
            fail ();
        }
        assertTrue (playerBoard.getSkulls () == 1);
    }

    @Test
    public void testAddAllSkulls() {
        PlayerBoard playerBoard = new PlayerBoard ();
        for (int i=0; i<6; i++) {
            try {
                playerBoard.addSkull ();
                assertEquals (i+1, playerBoard.getSkulls ());
            }catch (OutofBoundException e){
                e.printStackTrace ();
                fail ();
            }
        }
        assertEquals (6, playerBoard.getSkulls ());
    }

    @Test
    public void testDecreaseDeathScore() {
        PlayerBoard playerBoard = new PlayerBoard ();
        assertEquals (playerBoard.getDeathScore (), new Integer (8));
        playerBoard.decreaseDeathScore ();
        assertEquals (playerBoard.getDeathScore (), new Integer (6));
        playerBoard.decreaseDeathScore ();
        assertEquals (playerBoard.getDeathScore (), new Integer (4));
        playerBoard.decreaseDeathScore ();
        assertEquals (playerBoard.getDeathScore (), new Integer (2));
        playerBoard.decreaseDeathScore ();
        assertEquals (playerBoard.getDeathScore (), new Integer (1));
        playerBoard.decreaseDeathScore ();
        assertEquals (playerBoard.getDeathScore (), new Integer (1));
        playerBoard.decreaseDeathScore ();
        assertEquals (playerBoard.getDeathScore (), new Integer (1));





    }

    @Test
    public void testAddOneMark() {
        PlayerBoard playerBoard = new PlayerBoard ();
        try {
            playerBoard.addMark (DamageMark.YELLOW);
        }catch (OutofBoundException e) {
            e.printStackTrace ();
            fail ();
        }
        assertEquals (playerBoard.numMarkType (DamageMark.YELLOW), 1);
        assertEquals (playerBoard.numMarkType (DamageMark.PURPLE), 0);
        assertEquals (playerBoard.numMarkType (DamageMark.GREEN), 0);
        assertEquals (playerBoard.numMarkType (DamageMark.BLUE), 0);
        assertEquals (playerBoard.numMarkType (DamageMark.GRAY), 0);

    }

    @Test
    public void testAddAllMarks() {
        PlayerBoard playerBoard = new PlayerBoard ();
        ArrayList<DamageMark> tempList = new ArrayList<> ();
        for (int i=0; i<12; i++) {
            try {
                playerBoard.addMark (DamageMark.PURPLE);
                tempList.add (DamageMark.PURPLE);
            }catch (OutofBoundException e) {
                e.printStackTrace ();
                fail ();
            }
        }
        for (int i=0; i<tempList.size (); i++) {
            assertEquals (tempList.get (i), playerBoard.getMarks ().get (i));
        }
    }

    @Test
    public void testAddAmmo() {
        PlayerBoard playerBoard = new PlayerBoard ();
        ArrayList<Ammo> toAdd = new ArrayList<> ();
        for (int i=0; i<30; i++) {
            if(i>=0 && i<10)
                toAdd.add (Ammo.BLUE);
            else if(i>=10 && i<20)
                toAdd.add (Ammo.RED);
            else if(i>=20 && i<30);
            toAdd.add (Ammo.YELLOW);
        }
        playerBoard.addAmmo (toAdd);
        assertEquals (3, playerBoard.numAmmoType (Ammo.BLUE));
        assertEquals (3, playerBoard.numAmmoType (Ammo.RED));
        assertEquals (3, playerBoard.numAmmoType (Ammo.YELLOW));
        assertEquals (9, playerBoard.getAmmos ().size ());
    }

    @Test
    public void testSpendAmmo() {
        PlayerBoard playerBoard = new PlayerBoard ();
        ArrayList<Ammo> toAdd = new ArrayList<> ();
        ArrayList<Ammo> toSpend = new ArrayList<> ();
        for (int i=0; i<9; i++) {
            if(i>=0 && i<3)
                toAdd.add (Ammo.BLUE);
            else if(i>=3 && i<6)
                toAdd.add (Ammo.RED);
            else if(i>=6 && i<9);
            toAdd.add (Ammo.YELLOW);
        }
        playerBoard.addAmmo (toAdd);
        toSpend.add (Ammo.YELLOW);
        assertEquals (3, playerBoard.numAmmoType (Ammo.YELLOW));
        try {
            playerBoard.spendAmmo (toSpend);
        }catch (AmmoNotAvaibleException e) {
            e.printStackTrace ();
            fail ();
        }
        assertEquals (2, playerBoard.numAmmoType (Ammo.YELLOW));
        toSpend.add (Ammo.RED);
        try {
            playerBoard.spendAmmo (toSpend);
        }catch (AmmoNotAvaibleException e) {
            e.printStackTrace ();
            fail ();
        }
        assertEquals (2, playerBoard.numAmmoType (Ammo.RED));
        assertEquals (1, playerBoard.numAmmoType (Ammo.YELLOW));
    }

    @Test
    public void testSpendAllAmmo() {
        PlayerBoard playerBoard = new PlayerBoard ();
        ArrayList<Ammo> toAdd = new ArrayList<> ();
        ArrayList<Ammo> toSpend = new ArrayList<> ();
        for (int i=0; i<9; i++) {
            if(i>=0 && i<3) {
                toAdd.add (Ammo.BLUE);
                toSpend.add (Ammo.BLUE);
            }
            else if(i>=3 && i<6) {
                toAdd.add (Ammo.RED);
                toSpend.add (Ammo.RED);
            }
            else if(i>=6 && i<9) {
                toAdd.add (Ammo.YELLOW);
                toSpend.add (Ammo.YELLOW);
            }
        }
        playerBoard.addAmmo (toAdd);
        assertEquals (9, playerBoard.getAmmos ().size ());
        try {
            playerBoard.spendAmmo (toSpend);
        }catch (AmmoNotAvaibleException e) {
            e.printStackTrace ();
            fail ();
        }
        assertEquals (0, playerBoard.getAmmos ().size ());
        assertEquals (0, playerBoard.numAmmoType (Ammo.YELLOW));
        assertEquals (0, playerBoard.numAmmoType (Ammo.BLUE));
        assertEquals (0, playerBoard.numAmmoType (Ammo.RED));
    }

    @Test
    public void testCleanBoard() {
        PlayerBoard playerBoard = new PlayerBoard ();
        ArrayList<Ammo> toAdd = new ArrayList<> ();
        for (int i=0; i<12; i++) {
            if(i<6) {
                try {
                    playerBoard.addSkull ();
                }catch (OutofBoundException e) {
                    e.printStackTrace ();
                    fail ();
                }
            }
            try {
                playerBoard.addDamage (DamageMark.GREEN);
                playerBoard.addMark (DamageMark.YELLOW);
            }catch (OutofBoundException e) {
                e.printStackTrace ();
            }
        }
        toAdd.add (Ammo.YELLOW);
        toAdd.add (Ammo.RED);
        toAdd.add (Ammo.BLUE);
        playerBoard.addAmmo (toAdd);

        assertEquals (3, playerBoard.getAmmos ().size ());
        assertEquals (12, playerBoard.getMarks ().size ());
        assertEquals (12, playerBoard.getScores ().size ());

        playerBoard.cleanPlayerBoard ();
        assertEquals (playerBoard.getScores ().size (), 0);
        assertFalse (playerBoard.isDeath ());
        assertFalse (playerBoard.isOverkill ());
        assertFalse (playerBoard.isFirstBlood ());
        assertEquals (0, playerBoard.getSkulls ());
        assertEquals (3, playerBoard.getAmmos ().size ());
        assertEquals (12, playerBoard.getMarks ().size ());

    }

    @Test
    public void testCleanBoardMarks() {
        PlayerBoard playerBoard = new PlayerBoard ();
        ArrayList<Ammo> toAdd = new ArrayList<> ();
        for (int i=0; i<12; i++) {
            if(i<6) {
                try {
                    playerBoard.addSkull ();
                }catch (OutofBoundException e) {
                    e.printStackTrace ();
                    fail ();
                }
            }
            try {
                playerBoard.addDamage (DamageMark.GREEN);
                playerBoard.addMark (DamageMark.YELLOW);
            }catch (OutofBoundException e) {
                e.printStackTrace ();
            }
        }
        toAdd.add (Ammo.YELLOW);
        toAdd.add (Ammo.RED);
        toAdd.add (Ammo.BLUE);
        playerBoard.addAmmo (toAdd);

        assertEquals (3, playerBoard.getAmmos ().size ());
        assertEquals (12, playerBoard.getMarks ().size ());
        assertEquals (12, playerBoard.getScores ().size ());

        playerBoard.cleanPlayerBoard (true);
        assertEquals (playerBoard.getScores ().size (), 0);
        assertFalse (playerBoard.isDeath ());
        assertFalse (playerBoard.isOverkill ());
        assertFalse (playerBoard.isFirstBlood ());
        assertEquals (0, playerBoard.getSkulls ());
        assertEquals (3, playerBoard.getAmmos ().size ());
        assertEquals (0, playerBoard.getMarks ().size ());
    }
}