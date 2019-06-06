package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

public class PowerUpCardClient {

    private String powerUpType;
    private Ammo equivalentAmmo;

    public PowerUpCardClient(PowerUpCard powerUpCard) {
        this.powerUpType = powerUpCard.getPowerUpType();
        this.equivalentAmmo = powerUpCard.getEquivalentAmmo();
    }

    public String getPowerUpType() {
        return powerUpType;
    }

    public Ammo getEquivalentAmmo() {
        return equivalentAmmo;
    }
}
