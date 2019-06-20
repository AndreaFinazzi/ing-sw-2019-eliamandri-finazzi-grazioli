package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLIUtils;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

import java.io.Serializable;

public class PowerUpCardClient implements Serializable, CardInterface {


    private String id;
    private String powerUpType;
    private Ammo equivalentAmmo;
    private String description;

    private final static int FULL_WIDTH = 24;
    private final static int FULL_HEIGHT = 24;

    private final static int LIGTH_WIDTH = 20;
    private final static int LIGHT_HEIGHT = 12;

    private int slotPosition;

    private final static int width = 24;
    private final static int height = 24;

    public PowerUpCardClient() {

    }

    public PowerUpCardClient(PowerUpCard powerUpCard) {
        this.id = powerUpCard.getId();
        this.powerUpType = powerUpCard.getType();
        this.equivalentAmmo = powerUpCard.getAmmo();
        this.description = powerUpCard.getDescription();
        slotPosition = -1;
    }

    public String getId() {
        return id;
    }

    public String getPowerUpType() {
        return powerUpType;
    }

    public Ammo getEquivalentAmmo() {
        return equivalentAmmo;
    }

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

    public int getSlotPosition() {
        return slotPosition;
    }

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

    private String toStringLight() {
        return "PowerUp card:\n" + powerUpType +"\n";
    }
}
