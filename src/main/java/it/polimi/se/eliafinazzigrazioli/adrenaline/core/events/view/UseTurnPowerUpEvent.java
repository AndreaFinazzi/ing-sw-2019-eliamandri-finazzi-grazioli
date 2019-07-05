package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

/**
 * The type Use turn power up event.
 */
public class UseTurnPowerUpEvent extends AbstractViewEvent {

    private String powerUpId;

    /**
     * Instantiates a new Use turn power up event.
     *
     * @param clientID the client id
     * @param player the player
     * @param powerUpId the power up id
     */
    public UseTurnPowerUpEvent(int clientID, String player, String powerUpId) {
        super(clientID, player);
        this.powerUpId = powerUpId;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets power up id.
     *
     * @return the power up id
     */
    public String getPowerUpId() {
        return powerUpId;
    }
}
