package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.AmmoCard;

import java.io.Serializable;
import java.util.List;

public class AmmoCardClient implements Serializable {

    private String id;
    private List<Ammo> ammos;
    private boolean powerUp;

    public AmmoCardClient(AmmoCard ammoCard) {
        this.id = ammoCard.getId();
        this.ammos = ammoCard.getAmmos();
        this.powerUp = ammoCard.containsPowerUpCard();
    }

    public String getId() {
        return id;
    }

    public List<Ammo> getAmmos() {
        return ammos;
    }

    public boolean containsPowerUp() {
        return powerUp;
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
