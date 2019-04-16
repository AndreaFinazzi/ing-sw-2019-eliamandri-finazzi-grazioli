package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoard {
    private static final int maxSkulls = 6;
    private static final int maxDamage = 12;
    private static final int maxMark = 12;
    private ArrayList<DamageMark> damages;
    private ArrayList<DamageMark> marks;
    private int skulls;
    private ArrayList<Integer> scores;

    public PlayerBoard(int damagesNumber, int marksNumber, int skulls, ArrayList<Integer> scores) {
        this.damages = new ArrayList<DamageMark>(damagesNumber);
        this.marks = new ArrayList<DamageMark>(marksNumber);
        this.skulls = skulls;
        this.scores = scores;
    }
    //TODO define type excpetion
    public void addDamage(DamageMark damage) throws Exception{
        if (damages.size () == maxDamage)
            throw new Exception ();
        damages.add(damage);
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
    }

    public void spendAmmo(List<Ammo> toSpend){
        //TODO
    }

    public void addAmmo(List<Ammo> toAdd){
        //TODO
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

}
