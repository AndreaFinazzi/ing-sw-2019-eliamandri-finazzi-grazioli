package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

public enum PlayerAction {
    MOVE("Move"),
    SHOOT("Shoot"),
    COLLECT("Collect"),
    SHOW_MAP("Show map"),
    SHOW_OWNED_WEAPONS("Show owned weapon"),
    SHOW_OWNED_POWERUPS("Show owned powerup"),
    SHOW_OWNED_PLAYERBOARD("Show owned playerboard");


    private String label;

    PlayerAction(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}