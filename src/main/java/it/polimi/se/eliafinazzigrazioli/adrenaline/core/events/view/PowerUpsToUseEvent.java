package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;

/**
 * The type Power ups to use event.
 */
public class PowerUpsToUseEvent extends AbstractViewEvent {

    private String powerUpUsedId;
    private String target;
    private Ammo ammoToPayColor;
    private String powerUpToPayId;
    private String powerUpType;

    /**
     * Instantiates a new Power ups to use event.
     *
     * @param clientID the client id
     * @param player the player
     * @param powerUpUsedId the power up used id
     * @param target the target
     * @param ammoToPayColor the ammo to pay color
     * @param powerUpToPayId the power up to pay id
     * @param powerUpType the power up type
     */
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

    /**
     * Gets power up used id.
     *
     * @return the power up used id
     */
    public String getPowerUpUsedId() {
        return powerUpUsedId;
    }

    /**
     * Gets target.
     *
     * @return the target
     */
    public String getTarget() {
        return target;
    }

    /**
     * Gets ammo to pay color.
     *
     * @return the ammo to pay color
     */
    public Ammo getAmmoToPayColor() {
        return ammoToPayColor;
    }

    /**
     * Gets power up to pay id.
     *
     * @return the power up to pay id
     */
    public String getPowerUpToPayId() {
        return powerUpToPayId;
    }

    /**
     * Gets power up type.
     *
     * @return the power up type
     */
    public String getPowerUpType() {
        return powerUpType;
    }
}
