package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import java.util.List;

public abstract class BoardSquare implements Selectable{
    private Room room;
    private InterSquareLink north;
    private InterSquareLink south;
    private InterSquareLink east;
    private InterSquareLink west;

    public BoardSquare(Room room, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {
        this.room = room;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }


    public Room getRoom() {
        return room;
    }

    public List<Player> getPlayers(){
        //TODO
        return null;
    }

}
