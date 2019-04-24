package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model.AmmoNotAvailableException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model.OutOfBoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Messages;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Rules;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoard {
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
        deathScores = Rules.PLAYER_BOARD_DEATH_SCORES;
    }

    public void addDamage(DamageMark damage) throws OutOfBoundException {
        if (damages.size() == Rules.PLAYER_BOARD_MAX_DAMAGE)
            throw new OutOfBoundException(Messages.get("app.exceptions.game.player.damages_out_of_bound"));
        damages.add(damage);
        if (damages.size() == Rules.PLAYER_BOARD_FIRST_SHOOT)
            firstBlood = true;
        else if (damages.size() == Rules.PLAYER_BOARD_DEAD_SHOOT)
            death = true;
        else if (damages.size() == Rules.PLAYER_BOARD_MAX_DAMAGE)
            overkill = true;
    }

    public void addMark(DamageMark mark) throws OutOfBoundException {
        if (marks.size() == Rules.PLAYER_BOARD_MAX_MARKS || numMarkType(mark) >= Rules.PLAYER_BOARD_MAX_MARKS_PER_TYPE)
            throw new OutOfBoundException(Messages.get("app.exceptions.game.player.marks_out_of_bound"));

        marks.add(mark);
    }

    public int numMarkType(DamageMark type) {
        int cont = 0;
        for (DamageMark tempMark : marks) {
            if (tempMark.equals(type))
                cont++;
        }
        return cont;
    }

    public ArrayList<DamageMark> getMarks() {
        return marks;
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

    public void spendAmmo(List<Ammo> toSpend) throws AmmoNotAvailableException {
        if (!ammos.containsAll(toSpend))
            throw new AmmoNotAvailableException();
        for (Ammo ammo: toSpend) {
            ammos.remove (ammo);
        }
    }

    public void addAmmo(List<Ammo> toAdd) {
        for (Ammo tempAmmo : toAdd) {
            if (numAmmoType(tempAmmo) < Rules.PLAYER_BOARD_MAX_AMMO)
                ammos.add(tempAmmo);
        }
    }

    public ArrayList<Ammo> getAmmos() {
        return ammos;
    }

    public int getSkulls() {
        return skulls;
    }

    public void addSkull() throws OutOfBoundException {
        if (skulls == Rules.PLAYER_BOARD_MAX_SKULLS)
            throw new OutOfBoundException(Messages.get("app.exceptions.game.player.skulls_out_of_bound"));
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
