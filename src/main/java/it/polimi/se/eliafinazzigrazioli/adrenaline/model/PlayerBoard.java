package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class PlayerBoard {
    private static final int maxSkulls = 6;
    private static final int maxDamage = 12;
    private static final int maxMark = 12;
    private static final int deadShoot = 11;
    private static final int firstShoot = 1;
    private static final int maxAmmo = 3;
    private int skulls;
    private boolean firstBlood;
    private boolean death;
    private boolean overkill;
    private ArrayList<DamageMark> damages;
    private ArrayList<DamageMark> marks;
    private ArrayList<Ammo> ammos;
    private ArrayList<Integer> scores;

    public PlayerBoard(int damagesNumber, int marksNumber, int skulls, ArrayList<Integer> scores) {
        this.damages = new ArrayList<DamageMark>(damagesNumber);
        this.marks = new ArrayList<DamageMark>(marksNumber);
        this.ammos = new ArrayList<Ammo> ();
        this.skulls = skulls;
        this.scores = scores;
    }
    //TODO define type excpetion
    public void addDamage(DamageMark damage) throws Exception{
        if (damages.size () == maxDamage)
            throw new Exception ();
        damages.add(damage);
        if (damages.size () == firstShoot)
            firstBlood = true;
        else if (damages.size () == deadShoot)
            death = true;
        else if (damages.size () == maxDamage)
            overkill = true;
    }
    //TODO define type excpetion
    public void addMark(DamageMark mark) throws Exception{
        if (marks.size () == maxMark)
            throw new Exception ();
        marks.add(mark);
    }

    public void cleanPlayerBoard() {
        damages.clear();
        marks.clear();
        death = false;
        firstBlood = false;
        overkill = false;
        skulls = 0;
    }
    //TODO define type excpetion
    public void spendAmmo(List<Ammo> toSpend) throws Exception{
        if (!ammos.containsAll (toSpend))
            throw new Exception ();
        if (!ammos.removeAll (toSpend))
            throw new Exception ();
    }

    public void addAmmo(List<Ammo> toAdd){
        for (Ammo tempAmmo: toAdd) {
            if(numAmmoType (tempAmmo) < maxAmmo)
                ammos.add (tempAmmo);
        }
    }

    public int getSkulls() {
        return skulls;
    }

    public void addSkull() throws Exception{
        if (skulls == maxSkulls)
            throw new Exception ();
        skulls++;
    }

    public ArrayList<Integer> getScores(){
        //TODO
        return null;
    }

    public int numAmmoType(Ammo ammo){
        int cont = 0;
        for(Ammo tempAmmo: ammos) {
            if (tempAmmo.equals (ammo))
                cont++;
        }
        return cont;

    }

}
