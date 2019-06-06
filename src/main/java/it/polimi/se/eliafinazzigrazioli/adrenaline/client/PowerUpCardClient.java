package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;

public class PowerUpCardClient {

    private String powerUpType;
    private Ammo equivalentAmmo;

    public PowerUpCardClient(String powerUpType, Ammo equivalentAmmo) {
        this.powerUpType = powerUpType;
        this.equivalentAmmo = equivalentAmmo;
    }

    public String getPowerUpType() {
        return powerUpType;
    }

    public Ammo getEquivalentAmmo() {
        return equivalentAmmo;
    }
}
