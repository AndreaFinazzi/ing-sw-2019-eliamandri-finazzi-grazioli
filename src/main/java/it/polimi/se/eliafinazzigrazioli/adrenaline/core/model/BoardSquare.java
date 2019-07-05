package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AmmoCardCollectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.PowerUpsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.RuntimeTypeAdapterFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class BoardSquare {
    private Room room;
    private Coordinates coordinates;
    private InterSquareLink north;
    private InterSquareLink south;
    private InterSquareLink east;
    private InterSquareLink west;


    /**
     * Class hierarchy definition used to parse the json file.
     */
    public static final RuntimeTypeAdapterFactory<BoardSquare> effectStateRuntimeTypeAdapterFactory = RuntimeTypeAdapterFactory.of(BoardSquare.class, "type")
            .registerSubtype(GenericBoardSquare.class, "GenericBoardSquare")
            .registerSubtype(SpawnBoardSquare.class, "SpawnBoardSquare");



    public BoardSquare() {

    }
    // TODO remove reference to match
    public BoardSquare(Room room, Coordinates coordinates, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west) {
        this.room = room;
        this.coordinates = coordinates;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }


    abstract public AmmoCardCollectedEvent collect(Player player, PowerUpsDeck deck);

    abstract public boolean isSpawnPoint();

    abstract public boolean ammoCollectionIsValid();

    public Coordinates getCoordinates() {
        return coordinates;
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

    public List<Player> getPlayers(List<Player> players) {
        List<Player> onSquare = new ArrayList<>();
        for (Player player:players){
            if (player.getPosition()==this)
                onSquare.add(player);
        }
        return onSquare;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals (obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return  "room=" + room +
                ", coordinates=" + coordinates +
                ", north=" + north +
                ", south=" + south +
                ", east=" + east +
                ", west=" + west +
                '}';
    }
}
