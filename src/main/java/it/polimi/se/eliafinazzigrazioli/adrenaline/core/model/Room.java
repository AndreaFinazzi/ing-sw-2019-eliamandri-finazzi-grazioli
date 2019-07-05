package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;

/**
 * The enum Room.
 */
public enum Room {
    /**
     * Purple room.
     */
    PURPLE,
    /**
     * Yellow room.
     */
    YELLOW,
    /**
     * Green room.
     */
    GREEN,
    /**
     * Gray room.
     */
    GRAY,
    /**
     * Blue room.
     */
    BLUE,
    /**
     * Red room.
     */
    RED;

    @Override
    public String toString() {
        return Color.roomToColor(this) + this.name() + Color.RESET;
    }
}
