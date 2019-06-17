package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import java.io.Serializable;

public class PowerUpCard implements Serializable {

    private final String powerUpType;
    private final Ammo equivalentAmmo;
    private PowerUpEffect effect;
    private String descrption;
    private boolean toPay;

    public PowerUpCard(String powerUpType, Ammo equivalentAmmo) {
        this.powerUpType = powerUpType;
        this.equivalentAmmo = equivalentAmmo;
    }

    public Ammo getEquivalentAmmo() {
        return equivalentAmmo;
    }

    public String getPowerUpType() {
        return powerUpType;
    }

    public void activate() {
        effect.activate();
    }

    public String getDescrption() {
        return descrption;
    }

    public boolean isToPay() {
        return toPay;
    }

    @Override
    public String toString() {
        return "PowerUp Card: " + powerUpType + " " + equivalentAmmo;
    }
}
