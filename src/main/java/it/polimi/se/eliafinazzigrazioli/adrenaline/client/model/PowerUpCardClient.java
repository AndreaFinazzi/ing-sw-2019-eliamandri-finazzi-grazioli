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
    private final static int width = 24;
    private final static int height = 24;

    public PowerUpCardClient() {

    }

    public PowerUpCardClient(PowerUpCard powerUpCard) {
        this.id = powerUpCard.getId();
        this.powerUpType = powerUpCard.getType();
        this.equivalentAmmo = powerUpCard.getAmmo();
        this.description = powerUpCard.getDescription();
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

    @Override
    public String toString() {
        return CLIUtils.matrixToString(drawCard());
    }
}
