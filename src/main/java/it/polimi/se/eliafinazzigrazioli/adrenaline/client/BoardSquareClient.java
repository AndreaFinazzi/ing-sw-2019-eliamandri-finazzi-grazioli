package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.InterSquareLink;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;

public abstract class BoardSquareClient {

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

    public boolean isSpawnBoard(){
        return false;
    }

    public List<WeaponCardClient> getWeapons(){
        return null;
    }

    public boolean addWeaponCard(WeaponCardClient weapon){
        return false;
    }

    public boolean addAmmoCard(AmmoCardClient ammo){
        return false;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    //if return value is null, play is not valid
    public AmmoCardClient collectAmmoCard() {
        return null;
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
