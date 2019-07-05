package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

import java.util.List;

/**
 * The type Selectable players event.
 */
public class SelectablePlayersEvent extends AbstractModelEvent {

    private List<String> selectablePlayers;
    private int maxSelectableItems;

    /**
     * Instantiates a new Selectable players event.
     *
     * @param player the player
     * @param selectablePlayers the selectable players
     * @param maxSelectableItems the max selectable items
     */
    public SelectablePlayersEvent(Player player, List<String> selectablePlayers, int maxSelectableItems) {
        super(true, true, player);
        selectablePlayers.remove(player);

        this.selectablePlayers = selectablePlayers;
        this.maxSelectableItems = maxSelectableItems;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets selectable players.
     *
     * @return the selectable players
     */
    public List<String> getSelectablePlayers() {
        return selectablePlayers;
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
