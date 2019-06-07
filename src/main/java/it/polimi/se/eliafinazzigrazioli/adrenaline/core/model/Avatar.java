package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import java.io.Serializable;

public enum Avatar implements Serializable {
    DESTRUCTOR(":D-STRUCT-OR", DamageMark.YELLOW),
    BANSHEE("BANSHEE", DamageMark.BLUE),
    DOZER("DOZER", DamageMark.GRAY),
    VIOLET("VIOLET", DamageMark.PURPLE),
    SPROG("SPROG", DamageMark.GREEN);

    private String name;
    private DamageMark damageMark;

    Avatar(String name, DamageMark damageMark) {
        this.name = name;
        this.damageMark = damageMark;
    }

    public DamageMark getDamageMark() {
        return damageMark;
    }
}
