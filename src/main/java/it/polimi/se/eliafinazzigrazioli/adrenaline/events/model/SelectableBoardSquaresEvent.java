package it.polimi.se.eliafinazzigrazioli.adrenaline.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Coordinates;

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
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        if (this.getPlayer() == listener.getPlayer())
            listener.handleEvent(this);
    }
}
