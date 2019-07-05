package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeaponEffectClient implements Serializable {
    private static final long serialVersionUID = 9010L;

    private String effectName;
    private String effectDescription;
    private List<Ammo> price;

    public WeaponEffectClient(String effectName, String effectDescription, List<Ammo> price) {
        this.effectName = effectName;
        this.effectDescription = effectDescription;
        this.price = price;
    }

    public String getEffectName() {
        return effectName;
    }

    public String getEffectDescription() {
        return effectDescription;
    }

    public List<Ammo> getPrice() {
        return new ArrayList<>(price);
    }

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
