package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;

import java.util.ArrayList;
import java.util.List;

public class PlayerClient {

    private int powerUps;
    private List<WeaponCardClient> weapons;
    private List<Ammo> ammos;

    public PlayerClient() {
        powerUps = 0;
        weapons = new ArrayList<>();
        ammos = new ArrayList<>();
    }

    public void initAmmos(int startingAmmosPerColor) {
        for (int i = 0; i < startingAmmosPerColor; i++) {
            for (Ammo ammo: Ammo.values()) {
                addAmmo(ammo);
            }
        }
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
            if (weapon.equals(weaponCardClient))
                toRemove = weaponCardClient;
        }
        weapons.remove(toRemove);
        toRemove.setSlotPosition(null, -1);
        return toRemove;
    }

    public void addPowerUp() {
        powerUps++;
    }

    public void removePowerUp() {
        if (powerUps==0)
            new Exception().printStackTrace();
        powerUps--;
    }

}
