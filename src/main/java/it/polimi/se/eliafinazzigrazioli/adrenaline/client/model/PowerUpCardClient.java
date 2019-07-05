package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLIUtils;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

import java.io.Serializable;

/**
 * The type Power up card client.
 */
public class PowerUpCardClient implements Serializable, CardInterface {
    private static final long serialVersionUID = 9006L;

    private String id;
    private String powerUpType;
    private Ammo equivalentAmmo;
    private String description;

    private final static int FULL_WIDTH = 24;
    private final static int FULL_HEIGHT = 24;

    private final static int LIGTH_WIDTH = 20;
    private final static int LIGHT_HEIGHT = 12;

    private int slotPosition;

    private final static int DRAW_WIDTH = 24;
    private final static int DRAW_HEIGHT = 24;

    /**
     * Instantiates a new Power up card client.
     */
    public PowerUpCardClient() {

    }

    /**
     * Instantiates a new Power up card client.
     *
     * @param powerUpCard the power up card
     */
    public PowerUpCardClient(PowerUpCard powerUpCard) {
        this.id = powerUpCard.getId();
        this.powerUpType = powerUpCard.getType();
        this.equivalentAmmo = powerUpCard.getAmmo();
        this.description = powerUpCard.getDescription();
        slotPosition = -1;
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
     * Gets power up type.
     *
     * @return the power up type
     */
    public String getPowerUpType() {
        return powerUpType;
    }

    /**
     * Gets equivalent ammo.
     *
     * @return the equivalent ammo
     */
    public Ammo getEquivalentAmmo() {
        return equivalentAmmo;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }


    public String[][] drawCard() {
        String string = this.toString();
        String[][] box = CLIUtils.drawEmptyBox(FULL_WIDTH, FULL_HEIGHT, Color.ammoToColor(equivalentAmmo));
        box = CLIUtils.insertStringToMatrix(box, string);
        return box;
    }

    public String[][] drawCard(boolean light) {
        if(light) {
            String string = this.toStringLight();
            String[][] box = CLIUtils.drawEmptyBox(LIGTH_WIDTH, LIGHT_HEIGHT, Color.ammoToColor(equivalentAmmo));
            box = CLIUtils.insertStringToMatrix(box, string);
            return box;
        } else {
            return drawCard();
        }
    }

    /**
     * Gets slot position.
     *
     * @return the slot position
     */
    public int getSlotPosition() {
        return slotPosition;
    }

    /**
     * Sets slot position.
     *
     * @param slotPosition the slot position
     */
    public void setSlotPosition(int slotPosition) {
        this.slotPosition = slotPosition;
    }

    @Override
    public boolean equals(Object powerUpCardClient) {
        return id.equals(((PowerUpCardClient)powerUpCardClient).id);
    }

    @Override
    public String toString() {
        return "PowerUp card:\n" + powerUpType + "\n\nDescription :\n\n" + description;

    }

    /**
     * To string light string.
     *
     * @return the string
     */
    public String toStringLight() {
        return Color.ammoToColor(equivalentAmmo).toString() + powerUpType;
    }
}
