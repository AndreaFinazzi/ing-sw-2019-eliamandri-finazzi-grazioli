package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;

public class PowerUpsToUseEvent extends AbstractViewEvent {

    private String powerUpUsedId;
    private String target;
    private Ammo ammoToPayColor;
    private String powerUpToPayId;
    private String powerUpType;

    public PowerUpsToUseEvent(int clientID, String player, String powerUpUsedId, String target, Ammo ammoToPayColor, String powerUpToPayId, String powerUpType) {
        super(clientID, player);
        this.powerUpUsedId = powerUpUsedId;
        this.target = target;
        this.ammoToPayColor = ammoToPayColor;
        this.powerUpToPayId = powerUpToPayId;
        this.powerUpType = powerUpType;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public String getPowerUpUsedId() {
        return powerUpUsedId;
    }

    public String getTarget() {
        return target;
    }

    public Ammo getAmmoToPayColor() {
        return ammoToPayColor;
    }

    public String getPowerUpToPayId() {
        return powerUpToPayId;
    }

    public String getPowerUpType() {
        return powerUpType;
    }
}
