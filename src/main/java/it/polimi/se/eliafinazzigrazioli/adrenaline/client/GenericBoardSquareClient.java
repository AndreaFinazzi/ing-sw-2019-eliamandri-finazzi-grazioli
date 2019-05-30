package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.InterSquareLink;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

public class GenericBoardSquareClient extends BoardSquareClient {

    private boolean ammoCard;

    public GenericBoardSquareClient( Room room, Coordinates coordinates,
                                    InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {

        super(room, coordinates, north, south, east, west);
    }

    public boolean isAmmoCard() {
        return ammoCard;
    }

    public void setAmmoCard(boolean ammoCard) {
        this.ammoCard = ammoCard;
    }
}
