package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.RuntimeTypeAdapterFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class BoardSquare implements Selectable {
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

    @Override
    public List<Selectable> getVisible(SelectableType selType, boolean notVisible, GameBoard gameBoard) {
        switch (selType) {
            case PLAYER:
                return new ArrayList<>(gameBoard.getVisiblePlayers(this, notVisible));
            case BOARDSQUARE:
                return new ArrayList<>(gameBoard.getVisibleSquares(this, notVisible));
            default:
                return null; //TODO define exception raise for default case
        }
    }

    @Override
    public List<Selectable> getByDistance(SelectableType selType, int maxDistance, int minDistance, GameBoard gameBoard) {
        switch (selType) {
            case PLAYER:
                try {
                    return new ArrayList<>(gameBoard.getPlayersByDistance(this, maxDistance, minDistance));
                } catch (Exception e) {
                    return null;
                }
            case BOARDSQUARE:
                try {
                    return new ArrayList<>(gameBoard.getSquaresByDistance(this, maxDistance, minDistance));
                } catch (Exception e) {
                    return null;
                }
            default:
                return null; //TODO define exception raise for default case
        }
    }

    @Override
    public List<Selectable> getByRoom(SelectableType selType, GameBoard gameBoard, List<Player> players) {
        switch (selType) {
            case PLAYER:
                List<Selectable> toSelect = new ArrayList<>();
                for (Player player : players) {
                    if (player.getPosition().getRoom() == room) {
                        toSelect.add(player);
                    }
                }
                return toSelect;
            case BOARDSQUARE:
                return new ArrayList<>(gameBoard.getRoomSquares(room));
            default:
                return null;
        }
    }

    @Override
    public List<Selectable> getOnCardinal(SelectableType selType, GameBoard gameBoard) {
        switch (selType) {
            case BOARDSQUARE:
                return null;
            case PLAYER:
                return null;
            default:
                return null;
        }
    }

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
        for (Player player : players) {
            if (player.getPosition() == this)
                onSquare.add(player);
        }
        return onSquare;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "room=" + room +
                ", coordinates=" + coordinates +
                ", north=" + north +
                ", south=" + south +
                ", east=" + east +
                ", west=" + west +
                '}';
    }
}
