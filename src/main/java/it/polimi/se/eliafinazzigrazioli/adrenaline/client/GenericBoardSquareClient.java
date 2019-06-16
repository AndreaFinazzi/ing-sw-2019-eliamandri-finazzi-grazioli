package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.InterSquareLink;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

public class GenericBoardSquareClient extends BoardSquareClient {

    private AmmoCardClient ammoCard;

    public GenericBoardSquareClient(Room room, Coordinates coordinates,
                                    InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {

        super(room, coordinates, north, south, east, west);
        ammoCard = null;
    }


    @Override
    public boolean addAmmoCard(AmmoCardClient ammoCard) {
        if(ammoCard == null) {
            this.ammoCard = ammoCard;
            return true;
        }
        return false;
    }

    @Override
    public AmmoCardClient collectAmmoCard() {
        AmmoCardClient ammoCardClient = ammoCard;
        ammoCard = null;
        return ammoCardClient;
    }

    @Override
    public void removeAmmoCard() {

    }

    public boolean hasAmmoCard() {
        if(ammoCard != null)
            return true;
        return false;
    }

}
