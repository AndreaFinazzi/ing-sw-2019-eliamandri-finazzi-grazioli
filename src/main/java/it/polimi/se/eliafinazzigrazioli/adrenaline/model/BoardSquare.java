package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import java.util.ArrayList;
import java.util.List;

public abstract class BoardSquare implements Selectable{
    private Room room;
    private InterSquareLink north;
    private InterSquareLink south;
    private InterSquareLink east;
    private InterSquareLink west;
    private List<Player> players;

    public BoardSquare(Room room, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {
        this.room = room;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        players = new ArrayList<Player> ();
    }


    public Room getRoom() {
        return room;
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }


    public void addPlayer(Player player) {
        players.add (player);
    }

    public void removePlayer(Player player) {
        players.remove (player);
    }

}
