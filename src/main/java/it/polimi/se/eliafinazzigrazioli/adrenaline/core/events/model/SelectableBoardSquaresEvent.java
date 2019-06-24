package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;

public class SelectableBoardSquaresEvent extends AbstractModelEvent {

    private List<Coordinates> selectableBoardSquares;
    private int maxSelectableItems;

    public SelectableBoardSquaresEvent(Player player, List<Coordinates> selectableBoardSquares, int maxSelectableItems) {
        super(true, player);
        this.selectableBoardSquares = selectableBoardSquares;
        this.maxSelectableItems = maxSelectableItems;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public List<Coordinates> getSelectableBoardSquares() {
        return selectableBoardSquares;
    }

    public int getMaxSelectableItems() {
        return maxSelectableItems;
    }
}
