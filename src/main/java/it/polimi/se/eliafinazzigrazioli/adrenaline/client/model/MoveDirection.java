package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

/**
 * The enum Move direction.
 */
public enum MoveDirection {
    /**
     * Up move direction.
     */
    UP("Up", 0, 1),
    /**
     * Right move direction.
     */
    RIGHT("Right", 1, 0),
    /**
     * Down move direction.
     */
    DOWN("Down", 0, -1),
    /**
     * Left move direction.
     */
    LEFT("Left", -1, 0),
    /**
     * Stop move direction.
     */
    STOP("Stop", 0, 0);

    private String label;
    private int deltaX;
    private int deltaY;

    MoveDirection(String label, int deltaX, int deltaY) {
        this.label = label;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    /**
     * Gets delta x.
     *
     * @return the delta x
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * Gets delta y.
     *
     * @return the delta y
     */
    public int getDeltaY() {
        return deltaY;
    }

    @Override
    public String toString() {
        return label;
    }

}
