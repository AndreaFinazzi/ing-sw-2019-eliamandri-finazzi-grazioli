package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

public class Coordinates {

    private final int xCoordinate;
    private final int yCoordinate;

    public Coordinates(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinates) {
            Coordinates coordinates = (Coordinates) obj;
            return xCoordinate == coordinates.getXCoordinate() && yCoordinate == coordinates.getYCoordinate();
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" +
                +xCoordinate +
                "," + yCoordinate +
                ')';
    }
}
