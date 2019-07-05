package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Weapon effect client.
 */
public class WeaponEffectClient implements Serializable {
    private static final long serialVersionUID = 9010L;

    private String effectName;
    private String effectDescription;
    private List<Ammo> price;

    /**
     * Instantiates a new Weapon effect client.
     *
     * @param effectName the effect name
     * @param effectDescription the effect description
     * @param price the price
     */
    public WeaponEffectClient(String effectName, String effectDescription, List<Ammo> price) {
        this.effectName = effectName;
        this.effectDescription = effectDescription;
        this.price = price;
    }

    /**
     * Gets effect name.
     *
     * @return the effect name
     */
    public String getEffectName() {
        return effectName;
    }

    /**
     * Gets effect description.
     *
     * @return the effect description
     */
    public String getEffectDescription() {
        return effectDescription;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public List<Ammo> getPrice() {
        return new ArrayList<>(price);
    }

    /**
     * Price to string string.
     *
     * @return the string
     */
    public String priceToString() {
        String string = "";
        for(Ammo ammo : price) {
            string = string + ammo.toString() + " ";
        }
        return string;
    }

    @Override
    public String toString() {
        return this.effectName +
                (price.size()==0 ? " " : " at the cost of :" + priceToString());
    }
}
