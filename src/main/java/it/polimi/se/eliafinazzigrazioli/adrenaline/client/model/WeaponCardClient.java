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

/**
 * The type Weapon card client.
 */
public class WeaponCardClient implements Serializable, CardInterface {

    private static final long serialVersionUID = 9000L;

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

    /**
     * Instantiates a new Weapon card client.
     *
     * @param weaponCard the weapon card
     */
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

    /**
     * Instantiates a new Weapon card client.
     *
     * @param weaponName the weapon name
     * @param effectsDescription the effects description
     * @param loader the loader
     */
    public WeaponCardClient(String weaponName, Map<String, String> effectsDescription, List<Ammo> loader) {
        this.weaponName = weaponName;
        this.effectsDescription = effectsDescription;
        this.loader = loader;
    }

    /**
     * Gets weapon name.
     *
     * @return the weapon name
     */
    public String getWeaponName() {
        return weaponName;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets weapon color.
     *
     * @return the weapon color
     */
    public Ammo getWeaponColor() {
        return weaponColor;
    }

    /**
     * Gets loader.
     *
     * @return the loader
     */
    public List<Ammo> getLoader() {
        return new ArrayList<>(loader);
    }

    /**
     * Gets effects.
     *
     * @return the effects
     */
    public List<WeaponEffectClient> getEffects() {
        return new ArrayList<>(effects);
    }

    /**
     * Is loaded boolean.
     *
     * @return the boolean
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public List<Ammo> getPrice() {
        List<Ammo> price = new ArrayList<>(loader);
        price.add(weaponColor);
        return price;
    }

    /**
     * Gets slot position.
     *
     * @return the slot position
     */
    public int getSlotPosition() {
        return slotPosition;
    }

    /**
     * Sets slot position.
     *
     * @param spawnBoardSquare the spawn board square
     * @param slotPosition the slot position
     */
    public void setSlotPosition(Room spawnBoardSquare, int slotPosition) {
        this.spawnBoardSquare = spawnBoardSquare;
        this.slotPosition = slotPosition;
    }

    /**
     * Gets spawn board square.
     *
     * @return the spawn board square
     */
    public Room getSpawnBoardSquare() {
        return spawnBoardSquare;
    }

    /**
     * Sets spawn board square.
     *
     * @param spawnBoardSquare the spawn board square
     */
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

    /**
     * Sets loaded.
     *
     * @param loaded the loaded
     */
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
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

    /**
     * To string light string.
     *
     * @return the string
     */
    public String toStringLight() {
        String string = weaponName + " :\n\n";
        int count = 1;
        for (WeaponEffectClient effect: effects) {
            string = string + count + ": " + effect.getEffectName() + " price: " + effect.priceToString() + "\n\n";
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
        } else {
            String[][] weapon = CLIUtils.drawEmptyBox(LIGTH_WIDTH, LIGHT_HEIGHT, Color.ammoToColor(weaponColor));
            weapon = CLIUtils.insertStringToMatrix(weapon, this.toStringLight());
            return weapon;
        }
    }
}
