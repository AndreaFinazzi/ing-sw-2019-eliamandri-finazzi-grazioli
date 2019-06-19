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

public class BeginMatchEvent extends AbstractModelEvent {

    private MapType mapType;
    private Map<Coordinates, AmmoCardClient> ammoCardsSetup;
    private Map<Coordinates, List<WeaponCardClient>> weaponCardsSetup;

    private Map<String, Avatar> playerToAvatarMap;

    public BeginMatchEvent(MapType mapType) {
        this.mapType = mapType;
    }

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

    public MapType getMapType() {
        return mapType;
    }

    public Map<Coordinates, AmmoCardClient> getAmmoCardsSetup() {
        return ammoCardsSetup;
    }

    public Map<Coordinates, List<WeaponCardClient>> getWeaponCardsSetup() {
        return weaponCardsSetup;
    }

    public Map<String, Avatar> getPlayerToAvatar() {
        return playerToAvatarMap;
    }
}
