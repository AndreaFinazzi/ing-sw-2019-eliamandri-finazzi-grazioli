package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.InterSquareLink;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;

/**
 * The type Board square client.
 */
public abstract class BoardSquareClient {
    private static final long serialVersionUID = 9003L;

    /**
     * The Coordinates.
     */
    protected Coordinates coordinates;
    /**
     * The Room.
     */
    protected Room room;
    /**
     * The North.
     */
    protected InterSquareLink north;
    /**
     * The South.
     */
    protected InterSquareLink south;
    /**
     * The East.
     */
    protected InterSquareLink east;
    /**
     * The West.
     */
    protected InterSquareLink west;

    /**
     * Instantiates a new Board square client.
     *
     * @param room the room
     * @param coordinates the coordinates
     * @param north the north
     * @param south the south
     * @param east the east
     * @param west the west
     */
    public BoardSquareClient(Room room, Coordinates coordinates, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {
        this.coordinates = coordinates;
        this.room = room;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    /**
     * Gets room.
     *
     * @return the room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Gets north.
     *
     * @return the north
     */
    public InterSquareLink getNorth() {
        return north;
    }

    /**
     * Gets south.
     *
     * @return the south
     */
    public InterSquareLink getSouth() {
        return south;
    }

    /**
     * Gets east.
     *
     * @return the east
     */
    public InterSquareLink getEast() {
        return east;
    }

    /**
     * Gets west.
     *
     * @return the west
     */
    public InterSquareLink getWest() {
        return west;
    }

    /**
     * Is spawn board boolean.
     *
     * @return the boolean
     */
    public boolean isSpawnBoard(){
        return false;
    }

    /**
     * Gets weapon cards.
     *
     * @return the weapon cards
     */
    public List<WeaponCardClient> getWeaponCards() {
        return null;
    }

    /**
     * Add weapon card boolean.
     *
     * @param weapon the weapon
     * @return the boolean
     */
    public boolean addWeaponCard(WeaponCardClient weapon){
        return false;
    }

    /**
     * Add ammo card boolean.
     *
     * @param ammoCard the ammo card
     * @return the boolean
     */
    public boolean addAmmoCard(AmmoCardClient ammoCard) {
        return false;
    }

    /**
     * Remove ammo card.
     */
    public void removeAmmoCard() {
    }

    /**
     * Has ammo card boolean.
     *
     * @return the boolean
     */
    public boolean hasAmmoCard() {
        return false;
    }

    /**
     * Gets ammo card.
     *
     * @return the ammo card
     */
    public AmmoCardClient getAmmoCard() {
        return null;
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
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
