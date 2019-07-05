package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KillTrackTest {

    private KillTrack killTrack;

    @Before
    public void setUp() throws Exception {
        killTrack = new KillTrack(8);
    }

    @Test
    public void addDamageTest() {
        int oldSize = killTrack.getTrack().size();
        killTrack.removeSkull(DamageMark.BLUE, false);
        assertEquals(oldSize, killTrack.getTrack().size());
    }

    @Test
    public void toStringTest() {
        System.out.println(killTrack);
        for(int i = 0; i < 5; i++) {
            killTrack.removeSkull(DamageMark.GREEN, false);
        }
        for(int i=0; i<5; i++)
            killTrack.removeSkull(DamageMark.BLUE, true);
        System.out.println(killTrack);

    }
}