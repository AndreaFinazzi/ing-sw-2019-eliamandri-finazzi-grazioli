package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLIUtils;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.AmmoCard;

import java.io.Serializable;
import java.util.List;

/**
 * The type Ammo card client.
 */
public class AmmoCardClient implements Serializable, CardInterface {

    private static final long serialVersionUID = 9001L;

    private String id;
    private List<Ammo> ammos;
    private boolean powerUp;

    private final static int WIDTH = 17;
    private final static int HEIGTH = 8;

    /**
     * Instantiates a new Ammo card client.
     *
     * @param ammoCard the ammo card
     */
    public AmmoCardClient(AmmoCard ammoCard) {
        this.id = ammoCard.getId();
        this.ammos = ammoCard.getAmmos();
        this.powerUp = ammoCard.containsPowerUpCard();
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
     * Contains power up boolean.
     *
     * @return the boolean
     */
    public boolean containsPowerUp() {
        return powerUp;
    }

    @Override
    public String toString() {
        String string = "Ammo card: \n\n";
        int count = 1;
        for(Ammo ammo : ammos) {
            string = string + count + ": " + ammo + "\n";
            count++;
        }
        if(powerUp)
            string = string + count + ": PowerUp!";
        return string;
    }

    @Override
    public String[][] drawCard() {
        String[][] matrix = CLIUtils.drawEmptyBox(WIDTH, HEIGTH, Color.GRAY);
        matrix = CLIUtils.insertStringToMatrix(matrix, this.toString());
        return matrix;
    }

    @Override
    public String[][] drawCard(boolean light) {
        return new String[0][];
    }
}
