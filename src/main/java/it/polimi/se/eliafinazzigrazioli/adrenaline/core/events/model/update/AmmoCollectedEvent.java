package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

/**
 * The type Ammo collected event.
 */
public class AmmoCollectedEvent extends AbstractModelEvent {

    private Ammo ammoCollected;
    private boolean actuallyCollected;
    private boolean lastOfCard;

    /**
     * Instantiates a new Ammo collected event.
     *
     * @param player the player
     * @param ammoCollected the ammo collected
     * @param actuallyCollected the actually collected
     */
    public AmmoCollectedEvent(Player player, Ammo ammoCollected, boolean actuallyCollected) {
        this(player, ammoCollected, actuallyCollected, false);
    }

    /**
     * Instantiates a new Ammo collected event.
     *
     * @param player the player
     * @param ammoCollected the ammo collected
     * @param actuallyCollected the actually collected
     * @param lastOfCard the last of card
     */
    public AmmoCollectedEvent(Player player, Ammo ammoCollected, boolean actuallyCollected, boolean lastOfCard) {
        super(player);
        this.ammoCollected = ammoCollected;
        this.actuallyCollected = actuallyCollected;
        this.lastOfCard = lastOfCard;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets ammo collected.
     *
     * @return the ammo collected
     */
    public Ammo getAmmoCollected() {
        return ammoCollected;
    }

    /**
     * Is actually collected boolean.
     *
     * @return the boolean
     */
    public boolean isActuallyCollected() {
        return actuallyCollected;
    }

    /**
     * Is last of card boolean.
     *
     * @return the boolean
     */
    public boolean isLastOfCard() {
        return lastOfCard;
    }
}
