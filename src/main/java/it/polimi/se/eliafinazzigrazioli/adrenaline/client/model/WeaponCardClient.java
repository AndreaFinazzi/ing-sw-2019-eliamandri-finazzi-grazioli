package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLIUtils;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeaponCardClient implements Serializable, CardInterface {

    private static final long serialVersionUID = 9000;

    private String weaponName;
    private String id;

    private Ammo weaponColor;
    private List<Ammo> loader;

    private List<WeaponEffectClient> effects;

    private Map<String, String> effectsDescription;

    private boolean loaded;

    private String notes;

    private final static int FULL_WIDTH = 24;
    private final static int FULL_HEIGHT = 24;

    private final static int LIGTH_WIDTH = 24;
    private final static int LIGHT_HEIGHT = 12;

    private Room spawnBoardSquare;

    private int slotPosition;
    private final static int width = 24;

    private final static int height = 24;

    public WeaponCardClient(WeaponCard weaponCard) {
        weaponName = weaponCard.getWeaponName();
        id = weaponCard.getId();
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

        slotPosition = -1;
    }

    public WeaponCardClient(String weaponName, Map<String, String> effectsDescription, List<Ammo> loader) {
        this.weaponName = weaponName;
        this.effectsDescription = effectsDescription;
        this.loader = loader;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public String getId() {
        return id;
    }

    public Ammo getWeaponColor() {
        return weaponColor;
    }

    public List<Ammo> getLoader() {
        return new ArrayList<>(loader);
    }

    public List<WeaponEffectClient> getEffects() {
        return new ArrayList<>(effects);
    }

    public boolean isLoaded() {
        return loaded;
    }

    public List<Ammo> getPrice() {
        List<Ammo> price = new ArrayList<>(loader);
        price.add(weaponColor);
        return price;
    }

    public int getSlotPosition() {
        return slotPosition;
    }

    public void setSlotPosition(Room spawnBoardSquare, int slotPosition) {
        this.spawnBoardSquare = spawnBoardSquare;
        this.slotPosition = slotPosition;
    }

    public Room getSpawnBoardSquare() {
        return spawnBoardSquare;
    }

    public void setSpawnBoardSquare(Room spawnBoardSquare) {
        this.spawnBoardSquare = spawnBoardSquare;
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

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    @Override
    public String toString() {
        String string = String.format("%s%n", weaponName);
        int count = 1;
        for (WeaponEffectClient effect: effects) {
            //System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
            string = string + count + ": " + effect.getEffectName() + ": " + effect.getEffectDescription();
            string = String.format("%s%n", string);
            count++;
        }
        return string;
    }

    public String toStringLight() {
        String string = String.format("%s%n", weaponName);
        int count = 1;
        for (WeaponEffectClient effect: effects) {
            string = string + count + ": " + effect.getEffectName() + " price: " + effect.priceToString();
            string = String.format("%s%n", string);
            count++;
        }
        return string;
    }

    public String[][] drawCard() {
       String[][] weapon = CLIUtils.drawEmptyBox(FULL_WIDTH, FULL_HEIGHT, Color.ammoToColor(weaponColor));
       weapon = CLIUtils.insertStringToMatrix(weapon, this.toString());
       return weapon;
    }

    public String[][] drawCard(boolean light) {
        if(!light) {
            return drawCard();
        }
        else {
            String[][] weapon = CLIUtils.drawEmptyBox(LIGTH_WIDTH, LIGHT_HEIGHT, Color.ammoToColor(weaponColor));
            weapon = CLIUtils.insertStringToMatrix(weapon, this.toStringLight());
            return weapon;
        }
    }
}
