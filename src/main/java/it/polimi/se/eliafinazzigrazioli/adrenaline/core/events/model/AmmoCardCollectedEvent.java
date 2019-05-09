package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;

import java.util.List;

public class AmmoCardCollectedEvent extends AbstractModelEvent {

    private String powerUpCollected;
    private Ammo powerUpCollectedEquivalentAmmo;
    private List<Ammo> ammosCollected;

    public AmmoCardCollectedEvent(String player, String powerUpCollected, Ammo powerUpCollectedEquivalentAmmo, List<Ammo> ammosCollected) {
        super(player);
        this.powerUpCollected = powerUpCollected;
        this.powerUpCollectedEquivalentAmmo = powerUpCollectedEquivalentAmmo;
        this.ammosCollected = ammosCollected;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}
