package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;
import java.util.Map;

public class EndTurnEvent extends AbstractModelEvent {

    Map<Coordinates, AmmoCardClient> ammoCardsReplaced;
    Map<Coordinates, List<WeaponCardClient>> weaponCardsReplaced;

    public EndTurnEvent(Player player) {
        super(player);
    }

    public EndTurnEvent(Player player, Map<Coordinates, AmmoCardClient> ammoCardsReplaced, Map<Coordinates, List<WeaponCardClient>> weaponCardsReplaced) {
        super(player);
        this.ammoCardsReplaced = ammoCardsReplaced;
        this.weaponCardsReplaced = weaponCardsReplaced;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public Map<Coordinates, AmmoCardClient> getAmmoCardsReplaced() {
        return ammoCardsReplaced;
    }

    public Map<Coordinates, List<WeaponCardClient>> getWeaponCardsReplaced() {
        return weaponCardsReplaced;
    }
}
