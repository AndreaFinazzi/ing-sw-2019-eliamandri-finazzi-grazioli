package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

public class AmmoCollectedEvent extends AbstractModelEvent {

    private Ammo ammoCollected;
    private boolean actuallyCollected;

    public AmmoCollectedEvent(Player player, Ammo ammoCollected, boolean actuallyCollected) {
        super(player);
        this.ammoCollected = ammoCollected;
        this.actuallyCollected = actuallyCollected;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public Ammo getAmmoCollected() {
        return ammoCollected;
    }

    public boolean isActuallyCollected() {
        return actuallyCollected;
    }
}
