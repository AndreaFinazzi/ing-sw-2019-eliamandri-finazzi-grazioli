package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

public enum PlayerAction {
    MOVE("Move"),
    SHOOT("Shoot"),
    COLLECT("Collect"),
    SHOW_MAP("Show map");

    private String label;

    PlayerAction(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}