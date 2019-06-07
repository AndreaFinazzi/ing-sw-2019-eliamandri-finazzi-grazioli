package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.InterSquareLink;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

public class GenericBoardSquareClient extends BoardSquareClient {

    private AmmoCardClient ammoCard;

    public GenericBoardSquareClient(Room room, Coordinates coordinates,
                                    InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {

        super(room, coordinates, north, south, east, west);
    }


    @Override
    public boolean addAmmoCard(AmmoCardClient ammo) {
        if(ammoCard != null){
            ammoCard = ammo;
            return true;
        }
        return false;
    }



}
