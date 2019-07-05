package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;

import java.io.Serializable;

/**
 * The enum Avatar.
 */
public enum Avatar implements Serializable {
    /**
     * Destructor avatar.
     */
    DESTRUCTOR(":D-STRUCT-OR", DamageMark.YELLOW),
    /**
     * Banshee avatar.
     */
    BANSHEE("BANSHEE", DamageMark.BLUE),
    /**
     * Dozer avatar.
     */
    DOZER("DOZER", DamageMark.GREY),
    /**
     * Violet avatar.
     */
    VIOLET("VIOLET", DamageMark.PURPLE),
    /**
     * Sprog avatar.
     */
    SPROG("SPROG", DamageMark.GREEN);

    private String name;
    private DamageMark damageMark;

    Avatar(String name, DamageMark damageMark) {
        this.name = name;
        this.damageMark = damageMark;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets damage mark.
     *
     * @return the damage mark
     */
    public DamageMark getDamageMark() {
        return damageMark;
    }

    @Override
    public String toString() {
        return Color.damageMarkToColor(this.damageMark).toString() + name
                +" " + "\u263B" + Color.RESET.toString();
    }
}
