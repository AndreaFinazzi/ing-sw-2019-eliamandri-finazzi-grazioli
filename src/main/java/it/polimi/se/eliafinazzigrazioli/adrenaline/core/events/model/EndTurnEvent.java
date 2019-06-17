package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.Map;

public class EndTurnEvent extends AbstractModelEvent {

    Map<Coordinates, AmmoCardClient> ammoCardsSetup;

    public EndTurnEvent(Player player) {
        super(player);
    }

    public EndTurnEvent(Player player, Map<Coordinates, AmmoCardClient> ammoCardsSetup) {
        super(player);
        this.ammoCardsSetup = ammoCardsSetup;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public Map<Coordinates, AmmoCardClient> getAmmoCardsSetup() {
        return ammoCardsSetup;
    }
}
