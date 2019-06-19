package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLIUtils;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeaponCardClient implements Serializable {

    private String weaponName;

    private Ammo weaponColor;
    private List<Ammo> loader;

    private List<WeaponEffectClient> effects;

    private Map<String, String> effectsDescription;

    private boolean loaded;

    private String notes;

    private final static int width = 24;
    private final static int height = 24;


    public WeaponCardClient(WeaponCard weaponCard) {
        weaponName = weaponCard.getWeaponName();
        weaponColor = weaponCard.getCardColor();
        loader = weaponCard.getLoader();
        effects = new ArrayList<>();
        for (WeaponEffect weaponEffect: weaponCard.getEffects()) {
            effects.add(new WeaponEffectClient(
                    weaponEffect.getEffectName(),
                    weaponEffect.getEffectDescription(),
                    weaponEffect.getPrice()
                    ));
        }
        loaded = true;

        notes = weaponCard.getNotes();
    }

    public WeaponCardClient(String weaponName, Map<String, String> effectsDescription, List<Ammo> loader) {
        this.weaponName = weaponName;
        this.effectsDescription = effectsDescription;
        this.loader = loader;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public Ammo getWeaponColor() {
        return weaponColor;
    }

    public List<Ammo> getLoader() {
        return loader;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public List<Ammo> getPrice() {
        List<Ammo> price = new ArrayList<>(loader);
        price.add(weaponColor);
        return price;
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
        for (WeaponEffectClient effect: effects) {
            //System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
            string = string + count + ": " + effect.getEffectName() + ": " + effect.getEffectDescription() + "\n";
            count++;
        }
        return string;
    }

    public String[][] drawCard() {
       String[][] weapon = CLIUtils.drawEmptyBox(width, height, Color.ammoToColor(weaponColor));
       weapon = CLIUtils.insertStringToMatrix(weapon, this.toString());
       return weapon;
    }
}
