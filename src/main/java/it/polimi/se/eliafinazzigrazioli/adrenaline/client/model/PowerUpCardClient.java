package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

public class PowerUpCardClient {

    private String powerUpType;
    private Ammo equivalentAmmo;
    private String description;

    public PowerUpCardClient(PowerUpCard powerUpCard) {
        this.powerUpType = powerUpCard.getPowerUpType();
        this.equivalentAmmo = powerUpCard.getEquivalentAmmo();
        this.description = powerUpCard.getDescrption();
    }

    public String getPowerUpType() {
        return powerUpType;
    }

    public Ammo getEquivalentAmmo() {
        return equivalentAmmo;
    }

    @Override
    public String toString() {
        return "PowerUpCardClient{" +
                "powerUpType='" + powerUpType + '\'' +
                ", equivalentAmmo=" + equivalentAmmo +
                '}';
    }
}
