package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;

import java.io.Serializable;

public enum DamageMark implements Serializable {
    PURPLE,
    YELLOW,
    GREEN,
    GREY,
    BLUE;

    @Override
    public String toString() {
        return Color.damageMarkToColor(this) + "\u2688" + Color.RESET;
    }
}
