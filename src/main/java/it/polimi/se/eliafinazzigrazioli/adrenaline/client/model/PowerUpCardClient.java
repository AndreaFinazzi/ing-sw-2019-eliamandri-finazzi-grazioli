package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLIUtils;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

import java.io.Serializable;

public class PowerUpCardClient implements Serializable {


    private String id;
    private String powerUpType;
    private Ammo equivalentAmmo;
    private String description;

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
        String string = "PowerUp card:\n" + powerUpType + "\n\nDescription :\n\n" + description;
        String[][] box = CLIUtils.drawEmptyBox(width, height, Color.ammoToColor(equivalentAmmo));
        box = CLIUtils.insertStringToMatrix(box, string);
        return box;
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
        return CLIUtils.matrixToString(drawCard());
    }
}
