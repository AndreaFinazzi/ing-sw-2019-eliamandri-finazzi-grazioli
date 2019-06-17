package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

import java.io.Serializable;

public class PowerUpCardClient implements Serializable {


    private String id;
    private String powerUpType;
    private Ammo equivalentAmmo;
    private String description;

    public PowerUpCardClient(PowerUpCard powerUpCard) {
        this.id = powerUpCard.getId();
        this.powerUpType = powerUpCard.getType();
        this.equivalentAmmo = powerUpCard.getAmmo();
        this.description = powerUpCard.getDescription();
    }

    public String getId() {
        return id;
    }

    public String getPowerUpType() {
        return powerUpType;
    }

    public Ammo getEquivalentAmmo() {
        return equivalentAmmo;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "PowerUp Card: " + powerUpType + " " + equivalentAmmo;
    }
}
