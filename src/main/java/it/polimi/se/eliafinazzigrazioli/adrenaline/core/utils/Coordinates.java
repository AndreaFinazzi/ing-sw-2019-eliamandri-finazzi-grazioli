package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Serializable {

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
    public int hashCode() {
        return Objects.hash(xCoordinate, yCoordinate);
    }

    @Override
    public String toString() {
        return "(" +
                +xCoordinate +
                "," + yCoordinate +
                ')';
    }
}
