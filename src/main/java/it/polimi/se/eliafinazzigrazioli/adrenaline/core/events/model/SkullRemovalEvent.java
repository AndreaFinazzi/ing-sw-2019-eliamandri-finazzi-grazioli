package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.DamageMark;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

public class SkullRemovalEvent extends AbstractModelEvent {

    DamageMark damageMark;
    String deadPlayer;
    boolean overkill;
    boolean trackFull;

    public SkullRemovalEvent(Player player, DamageMark damageMark, String deadPlayer, boolean overkill, boolean trackFull) {
        super(player);
        this.damageMark = damageMark;
        this.deadPlayer = deadPlayer;
        this.overkill = overkill;
        this.trackFull = trackFull;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public DamageMark getDamageMark() {
        return damageMark;
    }

    public String getDeadPlayer() {
        return deadPlayer;
    }

    public boolean isOverkill() {
        return overkill;
    }
}
