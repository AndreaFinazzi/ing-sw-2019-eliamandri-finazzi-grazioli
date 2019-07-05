package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class UseTurnPowerUpEvent extends AbstractViewEvent {

    private String powerUpId;

    public UseTurnPowerUpEvent(int clientID, String player, String powerUpId) {
        super(clientID, player);
        this.powerUpId = powerUpId;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public String getPowerUpId() {
        return powerUpId;
    }
}
