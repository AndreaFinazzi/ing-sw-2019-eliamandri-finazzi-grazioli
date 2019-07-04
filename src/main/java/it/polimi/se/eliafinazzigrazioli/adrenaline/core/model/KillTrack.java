package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;

import java.util.ArrayList;
import java.util.List;

public class KillTrack {

    private final static String SKULL = "\u2620";

    public class Slot {
        private DamageMark damageMark;
        private boolean skull;
        private boolean doubleDamage;

        private Slot() {
            damageMark = null;
            doubleDamage = false;
            skull = true;
        }

        public void addDamage(DamageMark damageMark, boolean doubleDamage) {
            skull = false;
            this.doubleDamage = doubleDamage;
            this.damageMark = damageMark;
        }

        public DamageMark getDamageMark() {
            return damageMark;
        }

        public boolean isSkull() {
            return skull;
        }

        public boolean isDoubleDamage() {
            return doubleDamage;
        }
    }

    private List<Slot> track;

    private int skullsRemoved;
    private int finalMultipleDeathMarks;
    private DamageMark finalMultipleDeathMarkType;

    public KillTrack(int size) {
        track = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            track.add(new Slot());
        }
        skullsRemoved = 0;
        finalMultipleDeathMarks = 0;
        finalMultipleDeathMarkType = null;
    }

    public void removeSkull(DamageMark killerDamage, boolean doubleDamage) {
        if (isFull()) {
            finalMultipleDeathMarks++;
            if (doubleDamage)
                finalMultipleDeathMarks++;
        }
        else {
            track.get(skullsRemoved).addDamage(killerDamage, doubleDamage);
            skullsRemoved++;

        }
    }

    @Override
    public String toString() {
        String string = "KillTrack: \n";
        for(Slot slot : track) {
            if(slot.isSkull()) {
                string = string + "  " + Color.RED + SKULL + Color.RESET;
            }
            else if(!slot.isDoubleDamage()) {
                string = string + "  " + slot.getDamageMark().toString();
            }
            else if(slot.isDoubleDamage()) {
                string = string + "  " + slot.getDamageMark().toString()+slot.getDamageMark().toString();
            }
        }
        for(int i = 0; i < finalMultipleDeathMarks; i++) {
            string = string + track.get(track.size()-1).getDamageMark();
        }
        return string;
    }

    public int size() {
        return track.size();
    }

    public boolean isFull() {
        return track.size() == skullsRemoved;
    }

    public int getFinalMultipleDeathMarks() {
        return finalMultipleDeathMarks;
    }

    public DamageMark getFinalMultipleDeathMarkType() {
        return finalMultipleDeathMarkType;
    }

    public List<Slot> getTrack() {
        return track;
    }
}
