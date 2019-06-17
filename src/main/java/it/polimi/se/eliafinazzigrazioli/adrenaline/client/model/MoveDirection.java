package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

public enum MoveDirection {
    UP("Up", 0, 1),
    RIGHT("Right", 1, 0),
    DOWN("Down", 0, -1),
    LEFT("Left", -1, 0),
    STOP("Stop", 0, 0);

    private String label;
    private int deltaX;
    private int deltaY;

    MoveDirection(String label, int deltaX, int deltaY) {
        this.label = label;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    @Override
    public String toString() {
        return label;
    }

}
