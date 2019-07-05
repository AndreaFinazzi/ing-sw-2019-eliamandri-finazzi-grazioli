package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Coordinates.
 */
public class Coordinates implements Serializable {

    private static final long serialVersionUID = 9002L;

    private final int xCoordinate;
    private final int yCoordinate;

    /**
     * Instantiates a new Coordinates.
     *
     * @param xCoordinate the x coordinate
     * @param yCoordinate the y coordinate
     */
    public Coordinates(int xCoordinate, int yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * Gets x coordinate.
     *
     * @return the x coordinate
     */
    public int getXCoordinate() {
        return xCoordinate;
    }

    /**
     * Gets y coordinate.
     *
     * @return the y coordinate
     */
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
