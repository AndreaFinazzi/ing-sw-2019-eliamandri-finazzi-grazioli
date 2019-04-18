package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

public class GenericBoardSquare extends BoardSquare {

    private AmmoCard collectable;


    public GenericBoardSquare(Room room, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {
        super(room, north, south, east, west);
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


    @Override
    public BoardSquare getSquare() {
        return this;
    }
}
