package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;

public enum Ammo {
    RED,
    BLUE,
    YELLOW;


    //Todo
    @Override
    public String toString() {
        switch(this) {
            case YELLOW:
                return "Y";
            case BLUE:
                return "B";
            case RED:
                return "R";
            default:
                return "A";
        }
    }
}
