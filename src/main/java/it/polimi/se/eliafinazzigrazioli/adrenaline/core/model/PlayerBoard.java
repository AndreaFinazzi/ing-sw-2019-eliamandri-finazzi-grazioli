package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.AmmoNotAvailableException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Player board.
 */
public class PlayerBoard {


    private int skulls;
    private boolean death;
    private boolean overkill;

    private ArrayList<DamageMark> damages;

    private ArrayList<DamageMark> marks;
    private ArrayList<Ammo> ammos;

    private ArrayList<Integer> deathScores;
    private ArrayList<Integer> deathScoresFinalFrenzy;
    private boolean finalFrenzyMode;

    private int deliverableMarks;

    /**
     * Instantiates a new Player board.
     */
    public PlayerBoard() {
        this.damages = new ArrayList<>();
        this.marks = new ArrayList<>();
        this.ammos = new ArrayList<>();

        finalFrenzyMode = false;

        deathScores = Rules.PLAYER_BOARD_DEATH_SCORES;
        deathScoresFinalFrenzy = Rules.FINAL_FRENZY_DEATH_SCORES;
        deliverableMarks = Rules.PLAYER_BOARD_MAX_MARKS_DELIVERED;
    }

    /**
     * Switch to final frenzy.
     */
    public void switchToFinalFrenzy() {
        finalFrenzyMode = true;
        skulls = 0;
        deathScores = Rules.FINAL_FRENZY_DEATH_SCORES;
    }

    /**
     * Is final frenzy mode boolean.
     *
     * @return the boolean
     */
    public boolean isFinalFrenzyMode() {
        return finalFrenzyMode;
    }

    /**
     * Can use mark boolean.
     *
     * @return the boolean
     */
    public boolean canUseMark() {
        if (deliverableMarks > 0) {
            deliverableMarks--;
            return true;
        } else
            return false;
    }

    /**
     * Increase deliverable marks.
     */
    public void increaseDeliverableMarks() {
        if (deliverableMarks < Rules.PLAYER_BOARD_MAX_MARKS_DELIVERED)
            deliverableMarks++;
    }

    /**
     * Add damage damage mark.
     *
     * @param damage the damage
     * @return the damage mark
     */
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

    /**
     * Has damages boolean.
     *
     * @return the boolean
     */
    public boolean hasDamages() {
        return !(damages.size() == 0);
    }

    /**
     * Add mark damage mark.
     *
     * @param mark the mark
     * @return the damage mark
     */
    public DamageMark addMark(DamageMark mark) {
        if (numMarkType(mark) == Rules.PLAYER_BOARD_MAX_MARKS_PER_TYPE)
            return null;
        else {
            this.marks.add(mark);
            return mark;
        }
    }

    /**
     * Resuscitate.
     */
    public void resuscitate() {
        death = false;
        overkill = false;
        cleanPlayerBoard();
    }

    /**
     * Remove mark damage mark.
     *
     * @param mark the mark
     * @return the damage mark
     */
    public DamageMark removeMark(DamageMark mark) {
        if (marks.contains(mark)) {
            marks.remove(mark);
            return mark;
        } else
            return null;
    }

    /**
     * Methods to obtain movement parameters
     *
     * @return the int
     */
    public int simpleMovementMaxMoves() {
        int max_movements = Rules.MAX_MOVEMENTS;
        if (damages.size() >= Rules.MOVEMENT_ADRENALINIC_ACTION_MIN_DAMAGE)
            max_movements += Rules.MOVEMENT_ADRENALINIC_ACTION_MOVES_SURPLUS;
        return max_movements;
    }

    /**
     * Pre collection max moves int.
     *
     * @return the int
     */
    public int preCollectionMaxMoves() {
        int max_movements = Rules.MAX_MOVEMENTS_BEFORE_COLLECTION;
        if (damages.size() >= Rules.COLLECTING_ADRENALINIC_ACTION_MIN_DAMAGE)
            max_movements += Rules.COLLECTING_ADRENALINIC_ACTION_MOVES_SURPLUS;
        return max_movements;
    }

    /**
     * Pre shooting max moves int.
     *
     * @return the int
     */
    public int preShootingMaxMoves() {
        int max_movements = Rules.MAX_MOVEMENTS_BEFORE_SHOOTING;
        if (damages.size() >= Rules.SHOOTING_ADRENALINIC_ACTION_MIN_DAMAGE)
            max_movements += Rules.SHOOTING_ADRENALINIC_ACTION_MOVES_SURPLUS;
        return max_movements;
    }


    /**
     * Num mark type int.
     *
     * @param type the type
     * @return the int
     */
    public int numMarkType(DamageMark type) {
        int cont = 0;
        for (DamageMark tempMark : marks) {
            if (tempMark.equals(type))
                cont++;
        }
        return cont;
    }

    /**
     * Gets damages.
     *
     * @return the damages
     */
    public ArrayList<DamageMark> getDamages() {
        return damages;
    }

    /**
     * Gets marks.
     *
     * @return the marks
     */
    public ArrayList<DamageMark> getMarks() {
        return marks;
    }

    /**
     * Clean player board.
     */
    public void cleanPlayerBoard() {
        death = false;
        overkill = false;
        damages.clear();
    }

    /**
     * Decrease death score.
     */
    public void decreaseDeathScore() {
        if (deathScores.size() > 1)
            deathScores.remove(0);
    }

    /**
     * Gets death score.
     *
     * @return the death score
     */
    public Integer getDeathScore() {
        return deathScores.get(0);
    }

    /**
     * Clean player board.
     *
     * @param cleanMark the clean mark
     */
    public void cleanPlayerBoard(boolean cleanMark) {
        cleanPlayerBoard();
        if (cleanMark)
            marks.clear();
    }


    /**
     * Spend ammo.
     *
     * @param toSpend the to spend
     * @throws AmmoNotAvailableException the ammo not available exception
     */
// Ammo exchange methods
    public void spendAmmo(List<Ammo> toSpend) throws AmmoNotAvailableException {
        if (!ammos.containsAll(toSpend))
            throw new AmmoNotAvailableException();
        for (Ammo ammo : toSpend) {
            ammos.remove(ammo);
        }
    }

    /**
     * Add ammos.
     *
     * @param toAdd the to add
     */
    public void addAmmos(List<Ammo> toAdd) {
        for (Ammo tempAmmo : toAdd)
            addAmmo(tempAmmo);
    }

    /**
     * Add ammo boolean.
     *
     * @param ammo the ammo
     * @return the boolean
     */
    public boolean addAmmo(Ammo ammo) {
        if (numAmmoType(ammo) < Rules.PLAYER_BOARD_MAX_AMMO) {
            ammos.add(ammo);
            return true;
        }
        return false;
    }

    /**
     * Gets ammos.
     *
     * @return the ammos
     */
    public ArrayList<Ammo> getAmmos() {
        return ammos;
    }

    /**
     * Num ammo type int.
     *
     * @param ammo the ammo
     * @return the int
     */
    public int numAmmoType(Ammo ammo) {
        int cont = 0;
        for (Ammo tempAmmo : ammos) {
            if (tempAmmo.equals(ammo))
                cont++;
        }
        return cont;
    }

    /**
     * Gets skulls.
     *
     * @return the skulls
     */
    public int getSkulls() {
        return skulls;
    }

    /**
     * Gets killer damage.
     *
     * @return the killer damage
     */
    public DamageMark getKillerDamage() {
        return damages.get(Rules.PLAYER_BOARD_DEAD_SHOOT - 1);
    }

    /**
     * Add skull.
     */
    public void addSkull() {
        if (skulls != Rules.PLAYER_BOARD_MAX_SKULLS)
            skulls++;
    }

    /**
     * Gets scores.
     *
     * @return the scores
     */
    public List getScores() {
        return damages;
    }

    /**
     * Gets death scores.
     *
     * @return the death scores
     */
    public ArrayList<Integer> getDeathScores() {
        return deathScores;
    }

    /**
     * Is death boolean.
     *
     * @return the boolean
     */
    public boolean isDeath() {
        return death;
    }

    /**
     * Is overkill boolean.
     *
     * @return the boolean
     */
    public boolean isOverkill() {
        return overkill;
    }

    /**
     * Gets first blood shooter.
     *
     * @param players the players
     * @return the first blood shooter
     */
    public Player getFirstBloodShooter(List<Player> players) {
        for (Player player: players) {
            if (damages.get(0) == player.getDamageMarkDelivered())
                return player;
        }
        return null;
    }

    /**
     * Damage amount ranking list.
     *
     * @return the list
     */
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

    /**
     * Gets points by damage rank.
     *
     * @param position the position
     * @return the points by damage rank
     */
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
