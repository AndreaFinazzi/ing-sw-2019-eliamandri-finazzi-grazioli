package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

import java.util.ArrayList;
import java.util.List;

public class SpawnSelectionRequestEvent extends AbstractModelEvent {

    List<PowerUpCardClient> selectableCards;
    boolean firstSpawn;

    public SpawnSelectionRequestEvent(Player player, List<PowerUpCard> selectableCards, boolean firstSpawn) {
        super(true, true, player);
        this.selectableCards = new ArrayList<>();
        for (PowerUpCard powerUpCard: selectableCards)
            this.selectableCards.add(new PowerUpCardClient(powerUpCard));
        this.firstSpawn = firstSpawn;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public List<PowerUpCardClient> getSelectableCards() {
        return selectableCards;
    }

    public boolean isFirstSpawn() {
        return firstSpawn;
    }
}
