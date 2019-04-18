package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Coordinates;

import java.util.ArrayList;
import java.util.List;

public abstract class BoardSquare implements Selectable {
    private Room room;
    private Coordinates coordinates;
    private InterSquareLink north;
    private InterSquareLink south;
    private InterSquareLink east;
    private InterSquareLink west;
    private List<Player> players;   //TODO Should be removed, with the new logic are player them selves to keep track of their position

    public BoardSquare(Room room, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {
        this.room = room;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        players = new ArrayList<Player>();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }  //TODO Should be removed as well as said above

    public InterSquareLink getNorth() {
        return north;
    }

    public InterSquareLink getSouth() {
        return south;
    }

    public InterSquareLink getEast() {
        return east;
    }

    public InterSquareLink getWest() {
        return west;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

}
