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

public class PlayerClient implements Serializable, CardInterface {

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

    private final static int HEIGHT = 13;
    private boolean disconnected;

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

    public void initAmmos(int startingAmmosPerColor) {
        for (int i = 0; i < startingAmmosPerColor; i++) {
            for (Ammo ammo: Ammo.values()) {
                addAmmo(ammo);
            }
        }
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void addAmmo(Ammo ammo) {
        ammos.add(ammo);
    }

    public void removeAmmo(Ammo ammo) {
        ammos.remove(ammo);
    }

    public List<Ammo> getAmmos() {
        return new ArrayList<>(ammos);
    }

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

    public List<WeaponCardClient> getWeapons() {
        return weapons;
    }

    public void addPowerUp() {
        powerUps++;
    }

    public void removePowerUp() {
        if (powerUps==0)
            new Exception().printStackTrace();
        powerUps--;
    }

    public int getPowerUps() {
        return powerUps;
    }

    public void addDamages(List<DamageMark> damages) {
        this.damages.addAll(damages);
    }

    public List<DamageMark> getDamages() {
        return damages;
    }

    public void addMarks(List<DamageMark> marks) {
        this.marks.addAll(marks);
    }

    public List<DamageMark> getMarks() {
        return marks;
    }

    public void removeMarks(List<DamageMark> removedMarks) {
        marks.removeAll(removedMarks);
    }

    public void cleanPlayerBoard() {
        damages.clear();
        death = false;
        overkill = false;
    }

    public boolean isDeath() {
        return death;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public boolean isOverkill() {
        return overkill;
    }

    public void setOverkill(boolean overkill) {
        this.overkill = overkill;
    }

    public void addSkull() {
        skulls++;
    }

    @Override
    public String[][] drawCard() {
        String[][] matrix = CLIUtils.drawEmptyBox(WIDTH, HEIGHT, Color.damageMarkToColor(avatar.getDamageMark()));
        String string = "Avatar: " + avatar.getName();
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][1] = String.valueOf(string.charAt(i));
        }
        string = "Skulls: " + skulls;
        for(int i=0; i<string.length(); i++) {
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
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][8] = String.valueOf(string.charAt(i));
        }
        poseX = 2;
        for(int i=0; i<marks.size(); i++) {
            matrix[poseX+i][9] = marks.get(i).toString();
            poseX++;
        }
        string = "Number of powerUp: " + powerUps;
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][10] = String.valueOf(string.charAt(i));
        }

        return  matrix;
    }

    public WeaponCardClient getWeaponByName(String weapon) {
        for (WeaponCardClient weaponCardClient: weapons) {
            if (weapon.equals(weaponCardClient.getWeaponName()))
                return weaponCardClient;
        }
        return null;
    }

    @Override
    public String[][] drawCard(boolean light) {
        return new String[0][];
    }

    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }

    public boolean getDisconnected() {
        return disconnected;
    }
}
