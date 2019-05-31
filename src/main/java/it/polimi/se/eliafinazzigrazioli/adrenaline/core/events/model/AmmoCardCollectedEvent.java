package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

import java.util.List;

public class AmmoCardCollectedEvent extends AbstractModelEvent {

    private String powerUpCollected;
    private Ammo powerUpCollectedEquivalentAmmo;
    private List<Ammo> ammosCollected;

    public AmmoCardCollectedEvent(String player, PowerUpCard powerUpCard, List<Ammo> ammosCollected) {
        super(player);
        if (powerUpCard != null) {
            this.powerUpCollected = powerUpCard.getPowerUpType();
            this.powerUpCollectedEquivalentAmmo = powerUpCard.getEquivalentAmmo();
        }
        else {
            this.powerUpCollected = null;
            this.powerUpCollectedEquivalentAmmo = null;
        }
        this.ammosCollected = ammosCollected;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}
