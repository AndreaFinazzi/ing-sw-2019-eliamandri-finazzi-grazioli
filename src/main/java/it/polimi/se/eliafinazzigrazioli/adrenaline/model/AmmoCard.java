package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import java.util.List;
//TODO
public class AmmoCard {

    private final List<Ammo> ammos;
    private final PowerUpCard powerUp;

    public AmmoCard(String cardName, List<Ammo> ammos, PowerUpCard powerUpCard) {
        this.ammos = ammos;
        this.powerUp = powerUpCard;
    }


    public List<Ammo> getAmmos() {
        return ammos;
    }

    public PowerUpCard getPowerUpCard() {
        return powerUp;
    }
}
