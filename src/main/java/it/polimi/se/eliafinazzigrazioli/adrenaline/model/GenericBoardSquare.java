package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import java.util.List;

public class GenericBoardSquare extends BoardSquare {

    private AmmoCard collectables;


    public GenericBoardSquare(Room room, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west, AmmoCard collectables) {
        super(room, north, south, east, west);
        this.collectables = collectables;
    }

    public AmmoCard removeCollectables(){
        //TODO
        return null;
    }

    public void dropCollectables(AmmoCard toDrop){
        //TODO
    }

    public List<Player> getPlayers(){
        //TODO
        return null;
    }

    public BoardSquare getSquare(){
        //TODO
        return null;
    }

}
