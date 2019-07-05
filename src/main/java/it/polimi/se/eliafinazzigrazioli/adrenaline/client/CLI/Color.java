package it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.DamageMark;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;

/**
 * The enum Color.
 */
public enum Color {
    /**
     * Purple color.
     */
    PURPLE("\u001B[35m"),
    /**
     * Yellow color.
     */
    YELLOW("\u001B[33m"),
    /**
     * Green color.
     */
    GREEN("\u001B[32m"),
    /**
     * Gray color.
     */
    GRAY("\u001B[37m"),
    /**
     * Blue color.
     */
    BLUE("\u001B[34m"),
    /**
     * Red color.
     */
    RED("\u001B[31m"),
    /**
     * Reset color.
     */
    RESET("\u001B[0m");

    private String ansiColor;

    Color(String ansiColor) {
        this.ansiColor = ansiColor;
    }

    @Override
    public String toString() {
        return ansiColor;
    }

    /**
     * Ammo to color color.
     *
     * @param ammo the ammo
     * @return the color
     */
    public static Color ammoToColor(Ammo ammo) {

        switch(ammo) {
            case BLUE:
                return Color.BLUE;

            case YELLOW:
                return Color.YELLOW;

            case RED:
                return Color.RED;

            default:
                return Color.RESET;
        }
    }

    /**
     * Room to color color.
     *
     * @param room the room
     * @return the color
     */
    public static Color roomToColor(Room room) {

        switch(room) {
            case RED:
                return Color.RED;

            case BLUE:
                return Color.BLUE;

            case GRAY:
                return Color.GRAY;

            case GREEN:
                return Color.GREEN;

            case PURPLE:
                return Color.PURPLE;

            case YELLOW:
                return Color.YELLOW;

            default:
                return Color.RESET;

        }
    }

    /**
     * Damage mark to color color.
     *
     * @param damageMark the damage mark
     * @return the color
     */
    public static Color damageMarkToColor(DamageMark damageMark) {

        switch(damageMark) {

            case BLUE:
                return Color.BLUE;

            case GREEN:
                return Color.GREEN;

            case PURPLE:
                return Color.PURPLE;

            case YELLOW:
                return Color.YELLOW;

            default:
                return Color.RESET;

        }
    }
}
