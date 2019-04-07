package it.polimi.se.eliafinazzigrazioli.adrenaline.utils;

public class Coordinates {

    private final int xCoordinates;
    private final int yCoordinates;

    public Coordinates(int xCoordinates, int yCoordinates) {
        this.xCoordinates = xCoordinates;
        this.yCoordinates = yCoordinates;
    }

    public int getXCoordinates() {
        return xCoordinates;
    }

    public int getYCoordinates() {
        return yCoordinates;
    }
}
