package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import java.util.List;

public abstract class BoardSquare implements Selectable{
    private Room room;
    private InterSquareLink north;
    private InterSquareLink south;
    private InterSquareLink east;
    private InterSquareLink west;

    //TODO Costruttore

    public List<BoardSquare> getRoom(){
        //TODO
    }

}
