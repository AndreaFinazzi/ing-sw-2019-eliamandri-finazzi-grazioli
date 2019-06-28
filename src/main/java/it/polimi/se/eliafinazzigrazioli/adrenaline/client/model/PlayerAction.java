package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

public enum PlayerAction {
    MOVE("Move"),
    SHOOT("Shoot"),
    COLLECT("Collect"),
    SHOW_MAP("Show map", false),
    SHOW_OWNED_WEAPONS("Show owned weapon", false),
    SHOW_OWNED_POWERUPS("Show owned powerup", false),
    SHOW_PLAYERBOARDS("Show playerboards", false),
    SHOW_SPAWN_WEAPON("Show weapons on spawns", false);



    private String label;
    private boolean guiEnabled;

    PlayerAction(String label) {
        this(label, true);
    }

    PlayerAction(String label, boolean guiEnabled) {
        this.label = label;
        this.guiEnabled = guiEnabled;
    }

    @Override
    public String toString() {
        return label;
    }

    public boolean isGuiEnabled() {
        return guiEnabled;
    }
}