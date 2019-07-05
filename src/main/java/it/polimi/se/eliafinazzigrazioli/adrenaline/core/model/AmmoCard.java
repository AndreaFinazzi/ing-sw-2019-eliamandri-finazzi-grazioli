package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import java.io.Serializable;
import java.util.List;

/**
 * The type Ammo card.
 */
public class AmmoCard implements Serializable {

    private final String id;
    private final List<Ammo> ammos;
    private final boolean powerUp;

    /**
     * Instantiates a new Ammo card.
     *
     * @param ammos the ammos
     * @param powerUpCard the power up card
     * @param id the id
     */
    public AmmoCard(List<Ammo> ammos, boolean powerUpCard, String id) {
        this.id = id;
        this.ammos = ammos;
        this.powerUp = powerUpCard;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets ammos.
     *
     * @return the ammos
     */
    public List<Ammo> getAmmos() {
        return ammos;
    }

    /**
     * Contains power up card boolean.
     *
     * @return the boolean
     */
    public boolean containsPowerUpCard() {
        return powerUp;
    }

}
