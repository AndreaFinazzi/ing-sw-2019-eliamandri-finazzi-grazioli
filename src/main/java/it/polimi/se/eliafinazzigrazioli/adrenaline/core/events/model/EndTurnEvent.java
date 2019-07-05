package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;
import java.util.Map;

/**
 * The type End turn event.
 */
public class EndTurnEvent extends AbstractModelEvent {

    /**
     * The Ammo cards replaced.
     */
    Map<Coordinates, AmmoCardClient> ammoCardsReplaced;
    /**
     * The Weapon cards replaced.
     */
    Map<Coordinates, List<WeaponCardClient>> weaponCardsReplaced;

    /**
     * Instantiates a new End turn event.
     *
     * @param player the player
     */
    public EndTurnEvent(Player player) {
        super(player);
    }

    /**
     * Instantiates a new End turn event.
     *
     * @param player the player
     * @param ammoCardsReplaced the ammo cards replaced
     * @param weaponCardsReplaced the weapon cards replaced
     */
    public EndTurnEvent(Player player, Map<Coordinates, AmmoCardClient> ammoCardsReplaced, Map<Coordinates, List<WeaponCardClient>> weaponCardsReplaced) {
        super(player);
        this.ammoCardsReplaced = ammoCardsReplaced;
        this.weaponCardsReplaced = weaponCardsReplaced;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets ammo cards replaced.
     *
     * @return the ammo cards replaced
     */
    public Map<Coordinates, AmmoCardClient> getAmmoCardsReplaced() {
        return ammoCardsReplaced;
    }

    /**
     * Gets weapon cards replaced.
     *
     * @return the weapon cards replaced
     */
    public Map<Coordinates, List<WeaponCardClient>> getWeaponCardsReplaced() {
        return weaponCardsReplaced;
    }
}
