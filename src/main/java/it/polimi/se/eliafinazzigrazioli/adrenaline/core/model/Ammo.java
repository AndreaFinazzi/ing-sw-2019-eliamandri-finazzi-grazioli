package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;

import java.io.Serializable;

/**
 * The enum Ammo.
 */
public enum Ammo implements Serializable {
    /**
     * Red ammo.
     */
    RED,
    /**
     * Blue ammo.
     */
    BLUE,
    /**
     * Yellow ammo.
     */
    YELLOW;


    /**
     * To string to map string.
     *
     * @return the string
     */
//Todo
    public String toStringToMap() {
        switch(this) {
            case YELLOW:
                return Color.ammoToColor(this) + "Y";
            case BLUE:
                return Color.ammoToColor(this) + "B";
            case RED:
                return Color.ammoToColor(this) + "R";
            default:
                return Color.ammoToColor(this) + "A";
        }
    }

    //todo
    public String toString() {
        return Color.ammoToColor(this) + "\u2B1B";
    }
}
