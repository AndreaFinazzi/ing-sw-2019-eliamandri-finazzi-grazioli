package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Weapon collected event.
 */
public class WeaponCollectedEvent extends AbstractModelEvent {

    private String collectedWeapon;
    private String dropOffWeapon;
    private Coordinates collectionSpawnPoint;
    private List<PowerUpCardClient> powerUpsSpent;
    private List<Ammo> ammosSpent;
    private boolean handFull;

    /**
     * Instantiates a new Weapon collected event.
     *
     * @param player the player
     * @param collectedWeapon the collected weapon
     * @param dropOffWeapon the drop off weapon
     * @param collectionSpawnPoint the collection spawn point
     * @param powerUpsSpent the power ups spent
     * @param ammosSpent the ammos spent
     * @param handFull the hand full
     */
    public WeaponCollectedEvent(Player player, WeaponCard collectedWeapon, WeaponCard dropOffWeapon, Coordinates collectionSpawnPoint, List<PowerUpCard> powerUpsSpent, List<Ammo> ammosSpent, boolean handFull) {
        super(player);
        this.collectedWeapon = collectedWeapon.getWeaponName();
        if (dropOffWeapon != null)
            this.dropOffWeapon = dropOffWeapon.getWeaponName();
        else
            this.dropOffWeapon = null;
        this.collectionSpawnPoint = collectionSpawnPoint;
        this.powerUpsSpent = new ArrayList<>();
        for (PowerUpCard powerUpCard: powerUpsSpent)
            this.powerUpsSpent.add(new PowerUpCardClient(powerUpCard));
        this.ammosSpent = ammosSpent;
        this.handFull = handFull;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets collected weapon.
     *
     * @return the collected weapon
     */
    public String getCollectedWeapon() {
        return collectedWeapon;
    }

    /**
     * Gets drop off weapon.
     *
     * @return the drop off weapon
     */
    public String getDropOffWeapon() {
        return dropOffWeapon;
    }

    /**
     * Gets collection spawn point.
     *
     * @return the collection spawn point
     */
    public Coordinates getCollectionSpawnPoint() {
        return collectionSpawnPoint;
    }

    /**
     * Gets power ups spent.
     *
     * @return the power ups spent
     */
    public List<PowerUpCardClient> getPowerUpsSpent() {
        return powerUpsSpent;
    }

    /**
     * Gets ammos spent.
     *
     * @return the ammos spent
     */
    public List<Ammo> getAmmosSpent() {
        return ammosSpent;
    }

    /**
     * Is hand full boolean.
     *
     * @return the boolean
     */
    public boolean isHandFull() {
        return handFull;
    }
}
