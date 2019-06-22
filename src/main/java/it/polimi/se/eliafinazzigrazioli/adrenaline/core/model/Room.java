package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;

public enum Room {
    PURPLE,
    YELLOW,
    GREEN,
    GRAY,
    BLUE,
    RED;

    @Override
    public String toString() {
        return Color.roomToColor(this) + this.name() + Color.RESET;
    }
}
