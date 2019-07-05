package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.InterSquareLink;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

/**
 * The type Generic board square client.
 */
public class GenericBoardSquareClient extends BoardSquareClient {
    private static final long serialVersionUID = 9004L;

    private AmmoCardClient ammoCard;

    /**
     * Instantiates a new Generic board square client.
     *
     * @param room the room
     * @param coordinates the coordinates
     * @param north the north
     * @param south the south
     * @param east the east
     * @param west the west
     */
    public GenericBoardSquareClient(Room room, Coordinates coordinates,
                                    InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {

        super(room, coordinates, north, south, east, west);
        ammoCard = null;
    }


    @Override
    public boolean addAmmoCard(AmmoCardClient ammoCardToAdd) {
        if(ammoCard == null) {
            this.ammoCard = ammoCardToAdd;
            return true;
        }
        return false;
    }

    @Override
    public AmmoCardClient getAmmoCard() {
        return ammoCard;
    }

    @Override
    public void removeAmmoCard() {
        ammoCard = null;
    }

    public boolean hasAmmoCard() {
        return ammoCard != null;
    }

    @Override
    public String toString() {
        return "Board square at " + coordinates + "\n" + "Room: " + room;
    }
}
