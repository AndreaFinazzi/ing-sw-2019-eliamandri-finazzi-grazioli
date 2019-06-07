package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import java.util.Map;

public class WeaponCardClient {

    private String weaponName;

    private Map<String, String> effectsDescription;

    public WeaponCardClient(String weaponName, Map<String, String> effectsDescription) {
        this.weaponName = weaponName;
        this.effectsDescription = effectsDescription;
    }

    public String getWeaponName() {
        return weaponName;
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
}
