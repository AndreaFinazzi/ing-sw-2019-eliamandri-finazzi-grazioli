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

    public SpawnSelectionRequestEvent(String player, List<PowerUpCardClient> selectableCards) {
        super(player, true);

        this.selectableCards = selectableCards;
    }

    public SpawnSelectionRequestEvent(Player player, List<PowerUpCard> selectableCards) {
        super(true, player);
        this.selectableCards = new ArrayList<>();
        for (PowerUpCard powerUpCard: selectableCards)
            this.selectableCards.add(new PowerUpCardClient(powerUpCard));
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public List<PowerUpCardClient> getSelectableCards() {
        return selectableCards;
    }
}
