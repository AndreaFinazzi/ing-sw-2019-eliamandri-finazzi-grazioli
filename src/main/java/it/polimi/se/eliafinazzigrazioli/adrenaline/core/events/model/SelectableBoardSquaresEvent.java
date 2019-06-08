package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;

public class SelectableBoardSquaresEvent extends AbstractModelEvent {

    private List<Coordinates> selectableBoardSquares;
    private int maxSelectableItems;

    public SelectableBoardSquaresEvent(String player, List<Coordinates> selectableBoardSquares, int maxSelectableItems) {
        super(player);
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
}
