package it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;

public enum Color {
    PURPLE("\u001B[35m"),
    YELLOW("\u001B[33m"),
    GREEN("\u001B[32m"),
    GRAY("\u001B[37m"),
    BLUE("\u001B[34m"),
    RED("\u001B[41m"),
    RESET("\u001B[0m");

    private String ansiColor;

    Color(String ansiColor) {
        this.ansiColor = ansiColor;
    }

    @Override
    public String toString() {
        return ansiColor;
    }

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
}
