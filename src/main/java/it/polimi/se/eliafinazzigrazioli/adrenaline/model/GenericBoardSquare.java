package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Coordinates;

public class GenericBoardSquare extends BoardSquare {

    private AmmoCard collectable;


    public GenericBoardSquare(Room room, Coordinates coordinates, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west, Match match) {
        super(room, coordinates, north, south, east, west, match);
        collectable = null;
    }

    //TODO define type exception
    public AmmoCard removeCollectables() throws Exception {
        if (collectable == null)
            throw new Exception();
        AmmoCard tempCollectables = collectable;
        collectable = null;
        return tempCollectables;
    }

    public void dropCollectables(AmmoCard toDrop) {
        collectable = toDrop;
    }
}
