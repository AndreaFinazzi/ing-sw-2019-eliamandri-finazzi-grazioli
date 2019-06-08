package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;

import java.util.List;

public class SpawnSelectionRequestEvent extends AbstractModelEvent {

    List<PowerUpCard> selectableCards;

    public SpawnSelectionRequestEvent(String player, List<PowerUpCard> selectableCards) {
        super(player);
        this.selectableCards = selectableCards;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public List<PowerUpCard> getSelectableCards() {
        return selectableCards;
    }
}
