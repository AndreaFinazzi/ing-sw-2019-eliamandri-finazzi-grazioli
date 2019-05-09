package it.polimi.se.eliafinazzigrazioli.adrenaline.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Ammo;

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
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}
