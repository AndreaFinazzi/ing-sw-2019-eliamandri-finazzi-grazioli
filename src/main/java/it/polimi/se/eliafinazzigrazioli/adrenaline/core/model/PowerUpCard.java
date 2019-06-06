package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

public class PowerUpCard {

    private final String powerUpType;
    private final Ammo equivalentAmmo;
    private PowerUpEffect effect;

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

    @Override
    public String toString() {
        return "PowerUpCard{" +
                "equivalentAmmo=" + equivalentAmmo +
                ", effect=" + effect +
                '}';
    }
}
