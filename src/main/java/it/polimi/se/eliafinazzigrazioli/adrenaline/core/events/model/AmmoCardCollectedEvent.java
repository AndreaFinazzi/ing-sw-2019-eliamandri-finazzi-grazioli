package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;

/**
 * Is to be generated only if the last square of the path is a generic square. The different case in which a weapon has to
 * be collected must be managed by the client.
 */
public class AmmoCardCollectedEvent extends AbstractModelEvent {

    private String powerUpCollected;
    private Ammo powerUpCollectedEquivalentAmmo;
    private List<Ammo> ammosCollected;
    private List<Coordinates> path;

    public AmmoCardCollectedEvent(String player, PowerUpCard powerUpCard, List<Ammo> ammosCollected, List<Coordinates> path) {
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
        this.path = path;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}
