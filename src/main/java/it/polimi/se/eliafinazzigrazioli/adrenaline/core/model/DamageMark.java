package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;

public enum DamageMark {
    PURPLE,
    YELLOW,
    GREEN,
    GREY,
    BLUE;

    @Override
    public String toString() {
        return Color.damageMarkToColor(this) + "\u26AB" + Color.RESET;
    }
}
