package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;

/**
 * The type Selectable board squares event.
 */
public class SelectableBoardSquaresEvent extends AbstractModelEvent {

    private List<Coordinates> selectableBoardSquares;
    private int maxSelectableItems;

    /**
     * Instantiates a new Selectable board squares event.
     *
     * @param player the player
     * @param selectableBoardSquares the selectable board squares
     * @param maxSelectableItems the max selectable items
     */
    public SelectableBoardSquaresEvent(Player player, List<Coordinates> selectableBoardSquares, int maxSelectableItems) {
        super(true, true, player);
        this.selectableBoardSquares = selectableBoardSquares;
        this.maxSelectableItems = maxSelectableItems;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets selectable board squares.
     *
     * @return the selectable board squares
     */
    public List<Coordinates> getSelectableBoardSquares() {
        return selectableBoardSquares;
    }

    /**
     * Gets max selectable items.
     *
     * @return the max selectable items
     */
    public int getMaxSelectableItems() {
        return maxSelectableItems;
    }
}
