package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;

import java.io.Serializable;

/**
 * The enum Damage mark.
 */
public enum DamageMark implements Serializable {
    /**
     * Purple damage mark.
     */
    PURPLE,
    /**
     * Yellow damage mark.
     */
    YELLOW,
    /**
     * Green damage mark.
     */
    GREEN,
    /**
     * Grey damage mark.
     */
    GREY,
    /**
     * Blue damage mark.
     */
    BLUE;

    @Override
    public String toString() {
        return Color.damageMarkToColor(this) + "\u2688" + Color.RESET;
    }
}
