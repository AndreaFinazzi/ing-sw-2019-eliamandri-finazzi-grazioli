package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;

import java.util.List;

public class AmmoCardClient {

    private List<Ammo> ammos;
    private boolean powerUp;

    public AmmoCardClient(List<Ammo> ammos, boolean powerUp) {
        this.ammos = ammos;
        this.powerUp = powerUp;
    }

    @Override
    public String toString() {
        String string = "Ammo card: \n\t";
        int count = 1;
        for(Ammo ammo : ammos) {
            string = string + count + ") " + ammo + "\n\t";
        }
        return string;
    }
}
