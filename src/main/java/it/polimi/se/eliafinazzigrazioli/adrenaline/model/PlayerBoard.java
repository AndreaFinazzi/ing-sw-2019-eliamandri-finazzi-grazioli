package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoard {
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

    public void addDamage(DamageMark mark){
        damages.add(mark);
    }

    public void addMark(DamageMark mark){
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

    public void addSkull(){
        skulls++;
    }

    public ArrayList<Integer> getScores(){
        return null;
    }

}
