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
    private Match match;

    // TODO remove reference to martch
    public BoardSquare(Room room, Coordinates coordinates, InterSquareLink north, InterSquareLink south, InterSquareLink east, InterSquareLink west, Match match) {
        this.room = room;
        this.coordinates = coordinates;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.match = match;
    }

    @Override
    public List<Selectable> getVisible(SelectableType selType, boolean notVisible) {
        switch (selType){
            case PLAYER:
                return new ArrayList<>(match.getMap().getVisiblePlayers(this, notVisible));
            case BOARDSQUARE:
                return new ArrayList<>(match.getMap().getVisibleSquares(this, notVisible));
            default:
                return null; //TODO define exception raise for default case
        }
    }

    @Override
    public List<Selectable> getByDistance(SelectableType selType, int maxDistance, int minDistance){
        switch (selType){
            case PLAYER:
                try {
                    return new ArrayList<>(match.getMap().getPlayersByDistance(this, maxDistance, minDistance));
                }
                catch (Exception e){
                    return null;
                }
            case BOARDSQUARE:
                try {
                    return new ArrayList<>(match.getMap().getSquaresByDistance(this, maxDistance, minDistance));
                }
                catch (Exception e){
                    return null;
                }
            default:
                return null; //TODO define exception raise for default case
        }
    }

    @Override
    public List<Selectable> getByRoom(SelectableType selType) {
        switch (selType){
            case PLAYER:
                List<Selectable> toSelect = new ArrayList<>();
                for (Player player:match.getPlayers()){
                    if (player.getPosition().getRoom() == room){
                        toSelect.add(player);
                    }
                }
                return toSelect;
            case BOARDSQUARE:
                return new ArrayList<>(match.getMap().getRoomSquares(room));
            default:
                return null;
        }
    }

    @Override
    public List<Selectable> getOnCardinal(SelectableType selType) {
        switch (selType){
            case BOARDSQUARE:
                return new ArrayList<>(match.getMap().getSquaresOnCardinalDirections(this));
            case PLAYER:
                return new ArrayList<>(match.getMap().getPlayersOnCardinalDirections(this));
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

    public List<Player> getPlayers() {
        return match.getPlayersOnSquare(this);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals (obj);
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
