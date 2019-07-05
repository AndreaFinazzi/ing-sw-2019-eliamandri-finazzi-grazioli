package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;
import java.util.Map;

/**
 * The type Begin match event.
 */
public class BeginMatchEvent extends AbstractModelEvent {

    private MapType mapType;
    private Map<Coordinates, AmmoCardClient> ammoCardsSetup;
    private Map<Coordinates, List<WeaponCardClient>> weaponCardsSetup;

    private Map<String, Avatar> playerToAvatarMap;

    /**
     * Instantiates a new Begin match event.
     *
     * @param mapType the map type
     */
    public BeginMatchEvent(MapType mapType) {
        this.mapType = mapType;
    }

    /**
     * Instantiates a new Begin match event.
     *
     * @param mapType the map type
     * @param ammoCardsSetup the ammo cards setup
     * @param weaponCardsSetup the weapon cards setup
     * @param avatarMap the avatar map
     */
    public BeginMatchEvent(MapType mapType, Map<Coordinates, AmmoCardClient> ammoCardsSetup, Map<Coordinates, List<WeaponCardClient>> weaponCardsSetup, Map<String, Avatar> avatarMap) {
        this(mapType);
        this.ammoCardsSetup = ammoCardsSetup;
        this.playerToAvatarMap = avatarMap;
        this.weaponCardsSetup = weaponCardsSetup;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets map type.
     *
     * @return the map type
     */
    public MapType getMapType() {
        return mapType;
    }

    /**
     * Gets ammo cards setup.
     *
     * @return the ammo cards setup
     */
    public Map<Coordinates, AmmoCardClient> getAmmoCardsSetup() {
        return ammoCardsSetup;
    }

    /**
     * Gets weapon cards setup.
     *
     * @return the weapon cards setup
     */
    public Map<Coordinates, List<WeaponCardClient>> getWeaponCardsSetup() {
        return weaponCardsSetup;
    }

}
