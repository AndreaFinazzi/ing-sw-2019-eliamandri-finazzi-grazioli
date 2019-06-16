package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;

import java.util.List;
import java.util.Map;

public class WeaponCardClient {

    private String weaponName;

    private Ammo weaponColor;
    private List<Ammo> price;

    private Map<String, String> effectsDescription;

    private boolean loaded;


    public WeaponCardClient(String weaponName, Map<String, String> effectsDescription, List<Ammo> price) {
        this.weaponName = weaponName;
        this.effectsDescription = effectsDescription;
        this.price = price;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public Ammo getWeaponColor() {
        return weaponColor;
    }

    public List<Ammo> getPrice() {
        return price;
    }

    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        WeaponCardClient that = (WeaponCardClient) o;

        if(weaponName != null && weaponName.equals(that)) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        String string = weaponName + " :\n\n";
        int count = 1;
        for (Map.Entry<String, String> entry : effectsDescription.entrySet()) {
            //System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
            string = string + count + ": " + entry.getKey() + ":\n\t" + entry.getValue() + "\n";
            count++;
        }
        return string;
    }

    public String toReloadString() {
        String string = weaponName + " :\n" + "at the cost of: ";
        for(Ammo ammo : price) {
            string = string + ammo + " ";
        }
        return string;
    }
}
