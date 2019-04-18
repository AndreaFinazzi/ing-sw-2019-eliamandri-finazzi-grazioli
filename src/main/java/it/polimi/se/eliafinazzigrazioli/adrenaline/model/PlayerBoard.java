package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.exception.model.AmmoNotAvaibleException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exception.model.OutofBoundException;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoard {
    private static final int MAX_SKULLS = 6;
    private static final int MAX_DAMAGE = 12;
    private static final int MAX_MARK = 12;
    private static final int DEAD_SHOOT = 11;
    private static final int FIRST_SHOOT = 1;
    private static final int MAX_AMMO = 3;
    private int skulls;
    private boolean firstBlood;
    private boolean death;
    private boolean overkill;
    private ArrayList<DamageMark> damages;
    private ArrayList<DamageMark> marks;
    private ArrayList<Ammo> ammos;
    private ArrayList<Integer> deathScores;

    public PlayerBoard() {
        this.damages = new ArrayList<>();
        this.marks = new ArrayList<>();
        this.ammos = new ArrayList<>();
        deathScores = new ArrayList<> ();
        deathScores.add (8);
        deathScores.add (6);
        deathScores.add (4);
        deathScores.add (2);
        deathScores.add (1);
        deathScores.add (1);
    }

    public void addDamage(DamageMark damage) throws OutofBoundException {
        if (damages.size() == MAX_DAMAGE)
            throw new OutofBoundException ("damages out of bound");
        damages.add(damage);
        if (damages.size() == FIRST_SHOOT)
            firstBlood = true;
        else if (damages.size() == DEAD_SHOOT)
            death = true;
        else if (damages.size() == MAX_DAMAGE)
            overkill = true;
    }

    public void addMark(DamageMark mark) throws OutofBoundException {
        if (marks.size() == MAX_MARK)
            throw new OutofBoundException ("marks out of bount");
        marks.add(mark);
    }

    public void cleanPlayerBoard() {
        damages.clear();
        death = false;
        firstBlood = false;
        overkill = false;
        skulls = 0;
    }
    public void decreaseDeathScore() {
        if(deathScores.size () > 1)
            deathScores.remove (0);
    }

    public Integer getDeathScore() {
        return deathScores.get (0);
    }

    public void cleanPlayerBoard(boolean cleanMark) {
        cleanPlayerBoard ();
        if (cleanMark)
            marks.clear ();
    }

    public void spendAmmo(List<Ammo> toSpend) throws AmmoNotAvaibleException {
        if (!ammos.containsAll(toSpend))
            throw new AmmoNotAvaibleException ();
        if (!ammos.removeAll(toSpend))
            throw new AmmoNotAvaibleException ();
    }

    public void addAmmo(List<Ammo> toAdd) {
        for (Ammo tempAmmo : toAdd) {
            if (numAmmoType(tempAmmo) < MAX_AMMO)
                ammos.add(tempAmmo);
        }
    }

    public int getSkulls() {
        return skulls;
    }

    public void addSkull() throws OutofBoundException {
        if (skulls == MAX_SKULLS)
            throw new OutofBoundException ("Skulls out of bound");
        skulls++;
    }

    public List getScores() {
        return damages;
    }

    public int numAmmoType(Ammo ammo) {
        int cont = 0;
        for (Ammo tempAmmo : ammos) {
            if (tempAmmo.equals(ammo))
                cont++;
        }
        return cont;
    }

    public boolean isFirstBlood() {
        return firstBlood;
    }

    public boolean isDeath() {
        return death;
    }

    public boolean isOverkill() {
        return overkill;
    }
}
