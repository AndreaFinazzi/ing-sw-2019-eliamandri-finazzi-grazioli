package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

public class PowerUpCard {

    private final Ammo equivalentAmmo;
    private PowerUpEffect effect;

    public PowerUpCard(Ammo equivalentAmmo, PowerUpEffect effect) {
        this.equivalentAmmo = equivalentAmmo;
        this.effect = effect;
    }

    public Ammo getEquivalentAmmo() {
        return equivalentAmmo;
    }

    public void activate(){
        effect.activate();
    }

}
