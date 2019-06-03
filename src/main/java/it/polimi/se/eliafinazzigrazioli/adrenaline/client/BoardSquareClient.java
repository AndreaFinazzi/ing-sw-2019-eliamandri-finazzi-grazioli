package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.InterSquareLink;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

public class BoardSquareClient {

    protected Coordinates coordinates;
    protected Room room;
    protected InterSquareLink north;
    protected InterSquareLink south;
    protected InterSquareLink east;
    protected InterSquareLink west;

    public BoardSquareClient(Room room, Coordinates coordinates, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {
        this.coordinates = coordinates;
        this.room = room;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "BoardSquareClient{" +
                "coordinates=" + coordinates +
                ", room=" + room +
                ", north=" + north +
                ", south=" + south +
                ", east=" + east +
                ", west=" + west +
                '}';
    }
}
