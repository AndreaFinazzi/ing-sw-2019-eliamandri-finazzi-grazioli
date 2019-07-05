package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

/**
 * The type Card reloaded event.
 */
public class CardReloadedEvent extends AbstractModelEvent {

    private String WeaponReloaded;

    /**
     * Instantiates a new Card reloaded event.
     *
     * @param player the player
     * @param weaponReloaded the weapon reloaded
     */
    public CardReloadedEvent(String player, String weaponReloaded) {
        super(player);
        WeaponReloaded = weaponReloaded;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}