package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLIUtils;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.DamageMark;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Player client.
 */
public class PlayerClient implements Serializable, CardInterface {
    private static final long serialVersionUID = 9005L;

    private int powerUps;

    private List<WeaponCardClient> weapons;

    private List<Ammo> ammos;

    private Avatar avatar;

    //INFO PlayerBoard

    private int skulls;
    private boolean death;
    private boolean overkill;
    private ArrayList<DamageMark> damages;

    private ArrayList<DamageMark> marks;

    private ArrayList<Integer> deathScores;
    private final static int WIDTH = 30;

    private final static int HEIGHT = 12;
    private boolean disconnected;

    /**
     * Instantiates a new Player client.
     *
     * @param avatar the avatar
     */
    public PlayerClient(Avatar avatar) {
        powerUps = 0;
        weapons = new ArrayList<>();
        ammos = new ArrayList<>();
        damages = new ArrayList<>();
        marks = new ArrayList<>();
        //todo
        deathScores = Rules.PLAYER_BOARD_DEATH_SCORES;
        this.avatar = avatar;
    }

    /**
     * Instantiates a new Player client.
     *
     * @param player the player
     */
    public PlayerClient(Player player) {
        this(player.getAvatar());
        powerUps = player.getPowerUps().size();
        //TODO should be anonymous
        player.getWeapons().forEach(weaponCard -> weapons.add(new WeaponCardClient(weaponCard)));
        ammos = player.getPlayerBoard().getAmmos();
        skulls = player.getPlayerBoard().getSkulls();
        death = player.getPlayerBoard().isDeath();
        overkill = player.getPlayerBoard().isOverkill();
        damages = player.getPlayerBoard().getDamages();
        marks = player.getPlayerBoard().getMarks();
        deathScores = player.getPlayerBoard().getDeathScores();
    }

    /**
     * Init ammos.
     *
     * @param startingAmmosPerColor the starting ammos per color
     */
    public void initAmmos(int startingAmmosPerColor) {
        for (int i = 0; i < startingAmmosPerColor; i++) {
            for (Ammo ammo: Ammo.values()) {
                addAmmo(ammo);
            }
        }
    }

    /**
     * Gets avatar.
     *
     * @return the avatar
     */
    public Avatar getAvatar() {
        return avatar;
    }

    /**
     * Add ammo.
     *
     * @param ammo the ammo
     */
    public void addAmmo(Ammo ammo) {
        ammos.add(ammo);
    }

    /**
     * Remove ammo.
     *
     * @param ammo the ammo
     */
    public void removeAmmo(Ammo ammo) {
        ammos.remove(ammo);
    }

    /**
     * Gets ammos.
     *
     * @return the ammos
     */
    public List<Ammo> getAmmos() {
        return new ArrayList<>(ammos);
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
     * Add weapon.
     *
     * @param weapon the weapon
     */
    public void addWeapon(WeaponCardClient weapon) {
        List<Integer> slotPositions = new ArrayList<>();
        int index = 0;
        for (WeaponCardClient weaponCardClient: weapons)
            slotPositions.add(weaponCardClient.getSlotPosition());
        while (slotPositions.contains(index))
            index++;
        weapon.setSlotPosition(null, index);
        weapons.add(weapon);
    }

    /**
     * Remove weapon weapon card client.
     *
     * @param weapon the weapon
     * @return the weapon card client
     */
    public WeaponCardClient removeWeapon(String weapon) {

        WeaponCardClient toRemove = null;
        for (WeaponCardClient weaponCardClient: weapons) {
            if (weapon.equals(weaponCardClient.getWeaponName()))
                toRemove = weaponCardClient;
        }
        weapons.remove(toRemove);
        if (toRemove != null) toRemove.setSlotPosition(null, -1);
        return toRemove;
    }

    /**
     * Gets weapons.
     *
     * @return the weapons
     */
    public List<WeaponCardClient> getWeapons() {
        return weapons;
    }

    /**
     * Add power up.
     */
    public void addPowerUp() {
        powerUps++;
    }

    /**
     * Remove power up.
     */
    public void removePowerUp() {
        if (powerUps==0)
            new Exception().printStackTrace();
        powerUps--;
    }

    /**
     * Gets power ups.
     *
     * @return the power ups
     */
    public int getPowerUps() {
        return powerUps;
    }

    /**
     * Add damages.
     *
     * @param damages the damages
     */
    public void addDamages(List<DamageMark> damages) {
        this.damages.addAll(damages);
    }

    /**
     * Gets damages.
     *
     * @return the damages
     */
    public List<DamageMark> getDamages() {
        return damages;
    }

    /**
     * Add marks.
     *
     * @param marks the marks
     */
    public void addMarks(List<DamageMark> marks) {
        this.marks.addAll(marks);
    }

    /**
     * Gets marks.
     *
     * @return the marks
     */
    public List<DamageMark> getMarks() {
        return marks;
    }

    /**
     * Remove marks.
     *
     * @param removedMarks the removed marks
     */
    public void removeMarks(List<DamageMark> removedMarks) {
        marks.removeAll(removedMarks);
    }

    /**
     * Clean player board.
     */
    public void cleanPlayerBoard() {
        damages.clear();
        death = false;
        overkill = false;
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
     * Sets death.
     *
     * @param death the death
     */
    public void setDeath(boolean death) {
        this.death = death;
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
     * Sets overkill.
     *
     * @param overkill the overkill
     */
    public void setOverkill(boolean overkill) {
        this.overkill = overkill;
    }

    /**
     * Add skull.
     */
    public void addSkull() {
        skulls++;
    }

    @Override
    public String[][] drawCard() {
        String[][] matrix = CLIUtils.drawEmptyBox(WIDTH, HEIGHT, Color.damageMarkToColor(avatar.getDamageMark()));
        String string = "Avatar: " + avatar.getName();
        for(int i = 0; i<string.length(); i++) {
            matrix[i+2][1] = String.valueOf(string.charAt(i));
        }
        string = "Skulls: " + skulls;
        for(int i = 0; i<string.length(); i++) {
            matrix[i+2][2] = String.valueOf(string.charAt(i));
        }
        string = "Death score: " + deathScores.get(0);
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][3] = String.valueOf(string.charAt(i));
        }
        string = "Death: " + (death ? "Yes" : "No");
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
        string = "Damages: ";
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][6] = String.valueOf(string.charAt(i));
        }
        poseX = 2;
        for(int i=0; i<damages.size(); i++) {
            matrix[poseX+i][7] = damages.get(i).toString();
            poseX++;
        }
        string = "Marks: ";
        for(int i = 0; i<string.length(); i++) {
            matrix[i+2][8] = String.valueOf(string.charAt(i));
        }
        poseX = 2;
        for(int i = 0; i<marks.size(); i++) {
            matrix[poseX+i][9] = marks.get(i).toString();
            poseX++;
        }
        string = "Number of powerUp: " + powerUps;
        for(int i = 0; i<string.length(); i++) {
            matrix[i+2][10] = String.valueOf(string.charAt(i));
        }
        return  matrix;
    }

    /**
     * Gets weapon by name.
     *
     * @param weapon the weapon
     * @return the weapon by name
     */
    public WeaponCardClient getWeaponByName(String weapon) {
        for (WeaponCardClient weaponCardClient: weapons) {
            if (weapon.equals(weaponCardClient.getWeaponName()))
                return weaponCardClient;
        }
        return null;
    }

    @Override
    public String[][] drawCard(boolean light) {
        return drawCard();
    }

    /**
     * Sets disconnected.
     *
     * @param disconnected the disconnected
     */
    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    /**
     * Gets disconnected.
     *
     * @return the disconnected
     */
    public boolean getDisconnected() {
        return disconnected;
    }
}
