package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;

import java.io.Serializable;

public enum Avatar implements Serializable {
    DESTRUCTOR(":D-STRUCT-OR", DamageMark.YELLOW),
    BANSHEE("BANSHEE", DamageMark.BLUE),
    DOZER("DOZER", DamageMark.GREY),
    VIOLET("VIOLET", DamageMark.PURPLE),
    SPROG("SPROG", DamageMark.GREEN);

    private String name;
    private DamageMark damageMark;

    Avatar(String name, DamageMark damageMark) {
        this.name = name;
        this.damageMark = damageMark;
    }

    public String getName() {
        return name;
    }

    public DamageMark getDamageMark() {
        return damageMark;
    }

    @Override
    public String toString() {
        return Color.damageMarkToColor(this.damageMark).toString() + name
                +" " + "\u263B" + Color.RESET.toString();
    }
}
