package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.AmmoNotAvailableException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.OutOfBoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerBoard {


    private int skulls;
    private boolean death;
    private boolean overkill;

    private ArrayList<DamageMark> damages;

    private ArrayList<DamageMark> marks;
    private ArrayList<Ammo> ammos;

    private ArrayList<Integer> deathScores;

    private int deliverableMarks;

    public PlayerBoard() {
        this.damages = new ArrayList<>();
        this.marks = new ArrayList<>();
        this.ammos = new ArrayList<>();

        deathScores = Rules.PLAYER_BOARD_DEATH_SCORES;
        deliverableMarks = Rules.PLAYER_BOARD_MAX_MARKS_DELIVERED;
    }

    public boolean canUseMark() {
        if (deliverableMarks > 0) {
            deliverableMarks--;
            return true;
        }
        else
            return false;
    }

    public void increaseDeliverableMarks() {
        if (deliverableMarks < Rules.PLAYER_BOARD_MAX_MARKS_DELIVERED)
            deliverableMarks++;
    }

    public DamageMark addDamage(DamageMark damage) {
        if (damages.size() == Rules.PLAYER_BOARD_MAX_DAMAGE)
            return null;
        else {
            damages.add(damage);
            if (damages.size() == Rules.PLAYER_BOARD_DEAD_SHOOT)
                death = true;
            else if (damages.size() == Rules.PLAYER_BOARD_MAX_DAMAGE)
                overkill = true;
            return damage;
        }
    }

    public DamageMark addMark(DamageMark mark) {
        if (numMarkType(mark) == Rules.PLAYER_BOARD_MAX_MARKS_PER_TYPE)
            return null;
        else {
            this.marks.add(mark);
            return mark;
        }
    }

    public void resuscitate() {
        death = false;
        overkill = false;
        cleanPlayerBoard();
    }

    public DamageMark removeMark(DamageMark mark) {
        if (marks.contains(mark)) {
            marks.remove(mark);
            return mark;
        }
        else
            return null;
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

    public ArrayList<DamageMark> getDamages() {
        return damages;
    }

    public ArrayList<DamageMark> getMarks() {
        return marks;
    }

    public void cleanPlayerBoard() {
        death = false;
        overkill = false;
        damages.clear();
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

    public DamageMark getKillerDamage() {
        return damages.get(Rules.PLAYER_BOARD_DEAD_SHOOT - 1);
    }

    public void addSkull() {
        if (skulls != Rules.PLAYER_BOARD_MAX_SKULLS)
            skulls++;
    }

    public List getScores() {
        return damages;
    }

    public ArrayList<Integer> getDeathScores() {
        return deathScores;
    }

    public boolean isDeath() {
        return death;
    }

    public boolean isOverkill() {
        return overkill;
    }

    public Player getFirstBloodShooter(List<Player> players) {
        for (Player player: players) {
            if (damages.get(0) == player.getDamageMarkDelivered())
                return player;
        }
        return null;
    }

    public List<DamageMark> damageAmountRanking() {
        Map<DamageMark,Integer> markOccurrencesMap = new HashMap<>();
        List<DamageMark> shootingOrder = new ArrayList<>();
        List<DamageMark> ranking = new ArrayList<>();
        for (DamageMark damageMark: damages) {
            if (!shootingOrder.contains(damageMark))
                shootingOrder.add(damageMark);
        }
        for (DamageMark damageMark: shootingOrder)
            markOccurrencesMap.put(damageMark, 0);
        for (DamageMark damageMark: damages)
            markOccurrencesMap.put(damageMark, markOccurrencesMap.get(damageMark) + 1);

        int maxOccurrences = 0;
        DamageMark mostUsedMark = null;
        while (!shootingOrder.isEmpty()) {
            for (DamageMark damageMark: shootingOrder) {
                if (markOccurrencesMap.get(damageMark) > maxOccurrences)
                    mostUsedMark = damageMark;
            }
            ranking.add(mostUsedMark);
            shootingOrder.remove(mostUsedMark);
            maxOccurrences = 0;
        }
        return ranking;
    }

    public int getPointsByDamageRank(int position) {
        return deathScores.get(skulls + position);
    }

    @Override
    public String toString() {
        return "PlayerBoard " +
                "skulls: " + skulls +
                "\n death: " + death +
                ", overkill: " + overkill +
                "\n movementsAllowed: " + Rules.MAX_MOVEMENTS +
                ", deathScore: " +deathScores.get (0) +
                '\n';
    }
}
