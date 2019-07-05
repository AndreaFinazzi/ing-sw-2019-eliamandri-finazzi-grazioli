package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Kill track.
 */
public class KillTrack implements Serializable {

    private final static String SKULL = "\u2620";

    /**
     * The type Slot.
     */
    public class Slot implements Serializable {
        private DamageMark damageMark;
        private boolean skull;
        private boolean doubleDamage;

        private Slot() {
            damageMark = null;
            doubleDamage = false;
            skull = true;
        }

        /**
         * Add damage.
         *
         * @param damageMark the damage mark
         * @param doubleDamage the double damage
         */
        public void addDamage(DamageMark damageMark, boolean doubleDamage) {
            skull = false;
            this.doubleDamage = doubleDamage;
            this.damageMark = damageMark;
        }

        /**
         * Gets damage mark.
         *
         * @return the damage mark
         */
        public DamageMark getDamageMark() {
            return damageMark;
        }

        /**
         * Is skull boolean.
         *
         * @return the boolean
         */
        public boolean isSkull() {
            return skull;
        }

        /**
         * Is double damage boolean.
         *
         * @return the boolean
         */
        public boolean isDoubleDamage() {
            return doubleDamage;
        }
    }

    private List<Slot> track;

    private int skullsRemoved;
    private List<DamageMark> marksAfterLastSkull;
    private int finalMultipleDeathMarks;
    private DamageMark finalMultipleDeathMarkType;

    /**
     * Instantiates a new Kill track.
     *
     * @param size the size
     */
    public KillTrack(int size) {
        track = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            track.add(new Slot());
        }
        skullsRemoved = 0;
        marksAfterLastSkull = new ArrayList<>();
        finalMultipleDeathMarks = 0;
        finalMultipleDeathMarkType = null;
    }

    public List<DamageMark> getMarksAfterLastSkull() {
        return marksAfterLastSkull;
    }

    /**
     * Remove skull.
     *
     * @param killerDamage the killer damage
     * @param doubleDamage the double damage
     */
    public void removeSkull(DamageMark killerDamage, boolean doubleDamage) {
        if (isFull()) {
            marksAfterLastSkull.add(killerDamage);
            if (doubleDamage)
                marksAfterLastSkull.add(killerDamage);
        } else {
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

    /**
     * Size int.
     *
     * @return the int
     */
    public int size() {
        return track.size();
    }

    /**
     * Is full boolean.
     *
     * @return the boolean
     */
    public boolean isFull() {
        return track.size() == skullsRemoved;
    }

    /**
     * Gets final multiple death marks.
     *
     * @return the final multiple death marks
     */
    public int getFinalMultipleDeathMarks() {
        return finalMultipleDeathMarks;
    }

    /**
     * Gets final multiple death mark type.
     *
     * @return the final multiple death mark type
     */
    public DamageMark getFinalMultipleDeathMarkType() {
        return finalMultipleDeathMarkType;
    }

    /**
     * Gets track.
     *
     * @return the track
     */
    public List<Slot> getTrack() {
        return track;
    }
}
