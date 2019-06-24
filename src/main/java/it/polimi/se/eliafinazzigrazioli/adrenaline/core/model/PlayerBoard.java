package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLIUtils;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.AmmoNotAvailableException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.OutOfBoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.List;

public class PlayerBoard {


    private int skulls;
    private boolean death;
    private boolean overkill;
    private ArrayList<DamageMark> damages;
    private ArrayList<DamageMark> marks;
    private ArrayList<Ammo> ammos;
    private ArrayList<Integer> deathScores;
    private int movementsAllowed;
    private int movementsBeforeCollectingAllowed;
    private int movementsBeforeShootingAllowed;

    private final static int WIDTH = 30;
    private final static int HEIGHT = 7;


    public PlayerBoard() {
        this.damages = new ArrayList<>();
        this.marks = new ArrayList<>();
        this.ammos = new ArrayList<>();
        deathScores = Rules.PLAYER_BOARD_DEATH_SCORES;
        movementsAllowed = Rules.MAX_MOVEMENTS;
        movementsBeforeCollectingAllowed = Rules.MAX_MOVEMENTS_BEFORE_COLLECTION;
        movementsBeforeShootingAllowed = Rules.MAX_MOVEMENTS_BEFORE_SHOOTING;
    }

    public DamageMark addDamage(DamageMark damage) {
        damages.add(damage);
        if (damages.size() == Rules.PLAYER_BOARD_DEAD_SHOOT)
            death = true;
        else if (damages.size() == Rules.PLAYER_BOARD_MAX_DAMAGE)
            overkill = true;
        return damage;
    }

    public DamageMark addMark(DamageMark mark) {
        if (numMarkType(mark) == Rules.PLAYER_BOARD_MAX_MARKS_PER_TYPE)
            return null;
        else {
            this.marks.add(mark);
            return mark;
        }
    }

    /**
     * Methods to obtain movement parameters
     *
     */

    public int simpleMovementMaxMoves() {
        int max_movements = Rules.MAX_MOVEMENTS;
        if (damages.size() >= Rules.MOVEMENT_ADRENALINIC_ACTION_MIN_DAMAGE)
            max_movements += Rules.MOVEMENT_ADRENALINIC_ACTION_MOVES_SURPLUS;
        return max_movements;
    }

    public int preCollectionMaxMoves() {
        int max_movements = Rules.MAX_MOVEMENTS_BEFORE_COLLECTION;
        if (damages.size() >= Rules.COLLECTING_ADRENALINIC_ACTION_MIN_DAMAGE)
            max_movements += Rules.COLLECTING_ADRENALINIC_ACTION_MOVES_SURPLUS;
        return max_movements;
    }

    public int preShootingMaxMoves() {
        int max_movements = Rules.MAX_MOVEMENTS_BEFORE_SHOOTING;
        if (damages.size() >= Rules.SHOOTING_ADRENALINIC_ACTION_MIN_DAMAGE)
            max_movements += Rules.SHOOTING_ADRENALINIC_ACTION_MOVES_SURPLUS;
        return max_movements;
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
        overkill = false;
        skulls = 0;
    }

    public void decreaseDeathScore() {
        if (deathScores.size() > 1)
            deathScores.remove(0);
    }

    public Integer getDeathScore() {
        return deathScores.get(0);
    }

    public void cleanPlayerBoard(boolean cleanMark) {
        cleanPlayerBoard();
        if (cleanMark)
            marks.clear();
    }

    // Ammo exchange methods

    public void spendAmmo(List<Ammo> toSpend) throws AmmoNotAvailableException {
        if (!ammos.containsAll(toSpend))
            throw new AmmoNotAvailableException();
        for (Ammo ammo : toSpend) {
            ammos.remove(ammo);
        }
    }

    public void addAmmos(List<Ammo> toAdd) {
        for (Ammo tempAmmo : toAdd)
            addAmmo(tempAmmo);
    }

    public boolean addAmmo(Ammo ammo) {
        if (numAmmoType(ammo) < Rules.PLAYER_BOARD_MAX_AMMO) {
            ammos.add(ammo);
            return true;
        }
        return false;
    }

    public ArrayList<Ammo> getAmmos() {
        return ammos;
    }

    public int numAmmoType(Ammo ammo) {
        int cont = 0;
        for (Ammo tempAmmo : ammos) {
            if (tempAmmo.equals(ammo))
                cont++;
        }
        return cont;
    }

    public int getSkulls() {
        return skulls;
    }

    public void addSkull() throws OutOfBoundException {
        if (skulls == Rules.PLAYER_BOARD_MAX_SKULLS)
            throw new OutOfBoundException(Messages.MESSAGE_EXCEPTIONS_GAME_PLAYER_SKULLS_OUT_OF_BOUND);
        skulls++;
    }

    public List getScores() {
        return damages;
    }

    public boolean isDeath() {
        return death;
    }

    public boolean isOverkill() {
        return overkill;
    }

    public String[][] toDraw() {
        String[][] matrix = CLIUtils.drawEmptyBox(WIDTH, HEIGHT, Color.BLUE);
        String string = "Skulls: " + skulls;
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][1] = String.valueOf(string.charAt(i));
        }
        string = "Death score: " + deathScores.get(0);
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][2] = String.valueOf(string.charAt(i));
        }
        string = "Death: " + (death ? "Yes" : "No");
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][3] = String.valueOf(string.charAt(i));
        }
        string = "Movementes allowed: " + movementsAllowed;
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][4] = String.valueOf(string.charAt(i));
        }
        string = "ammos: ";
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][5] = String.valueOf(string.charAt(i));
        }
        int poseX = string.length()+2;
        for(int i=0; i<ammos.size(); i++) {
            matrix[poseX+i][5] = ammos.get(i).toString();
            poseX++;
        }

        return  matrix;
    }

    @Override
    public String toString() {
        return "PlayerBoard " +
                "skulls: " + skulls +
                "\n death: " + death +
                ", overkill: " + overkill +
                "\n movementsAllowed: " + movementsAllowed +
                ", deathScore: " +deathScores.get (0) +
                '\n';
    }
}
