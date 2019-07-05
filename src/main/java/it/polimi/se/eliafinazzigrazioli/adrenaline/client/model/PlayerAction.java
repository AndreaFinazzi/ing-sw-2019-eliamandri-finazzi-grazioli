package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

/**
 * The enum Player action.
 */
public enum PlayerAction {
    /**
     * Move player action.
     */
    MOVE("Move"),
    /**
     * Shoot player action.
     */
    SHOOT("Shoot"),
    /**
     * Collect player action.
     */
    COLLECT("Collect"),
    /**
     * The Use power up.
     */
    USE_POWER_UP("Use power up"),
    /**
     * The Show info.
     */
    SHOW_INFO("Show info", false),
    /**
     * The Show owned weapons.
     */
    SHOW_OWNED_WEAPONS("Show owned weapon", false),
    /**
     * The Show owned powerups.
     */
    SHOW_OWNED_POWERUPS("Show owned powerups", false),
    /**
     * The Show playerboards.
     */
    SHOW_PLAYERBOARDS("Show playerboards", false),
    /**
     * The Show opponent weapon.
     */
    SHOW_OPPONENT_WEAPON("Show opponent weapons", false),
    /**
     * The Show spawn weapon.
     */
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

    /**
     * Is gui enabled boolean.
     *
     * @return the boolean
     */
    public boolean isGuiEnabled() {
        return guiEnabled;
    }
}