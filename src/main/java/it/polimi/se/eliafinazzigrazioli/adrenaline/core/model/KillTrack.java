package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import java.util.ArrayList;
import java.util.List;

public class KillTrack {

    private class Slot {
        DamageMark damageMark;
        boolean skull;
        boolean doubleDamage;

        public Slot() {
            damageMark = null;
            doubleDamage = false;
            skull = true;
        }

        void addDamage(DamageMark damageMark, boolean doubleDamage) {
            skull = false;
            this.doubleDamage = doubleDamage;
            this.damageMark = damageMark;
        }
    }

    private List<Slot> track;
    private int size;
    private int skullsRemoved;
    private int finalMultipleDeathMarks;
    private DamageMark finalMultipleDeathMarkType;

    public KillTrack(int size) {
        this.size = size;
        track = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            track.add(new Slot());
        }
        skullsRemoved = 0;
        finalMultipleDeathMarks = 0;
        finalMultipleDeathMarkType = null;
    }

    public void removeSkull(Player killer, boolean doubleDamage) {
        if (isFull()) {
            finalMultipleDeathMarks++;
            if (doubleDamage)
                finalMultipleDeathMarks++;
        }
        else {
            track.get(skullsRemoved).addDamage(killer.getDamageMarkDelivered(), doubleDamage);
            skullsRemoved++;
        }
    }

    public boolean isFull() {
        return track.size() == skullsRemoved;
    }
}
