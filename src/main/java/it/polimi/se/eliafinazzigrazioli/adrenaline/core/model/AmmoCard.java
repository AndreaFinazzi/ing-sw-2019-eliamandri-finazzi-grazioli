package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import java.io.Serializable;
import java.util.List;

public class AmmoCard implements Serializable {

    private final String id;
    private final List<Ammo> ammos;
    private final boolean powerUp;

    public AmmoCard(List<Ammo> ammos, boolean powerUpCard, String id) {
        this.id = id;
        this.ammos = ammos;
        this.powerUp = powerUpCard;
    }

    public String getId() {
        return id;
    }

    public List<Ammo> getAmmos() {
        return ammos;
    }

    public boolean containsPowerUpCard() {
        return powerUp;
    }

}
