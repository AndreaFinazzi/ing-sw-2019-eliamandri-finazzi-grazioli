package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

import java.util.List;

public class SelectablePlayersEvent extends AbstractModelEvent {

    private List<String> selectablePlayers;
    private int maxSelectableItems;

    public SelectablePlayersEvent(Player player, List<String> selectablePlayers, int maxSelectableItems) {
        super(true, player);
        selectablePlayers.remove(player);

        this.selectablePlayers = selectablePlayers;
        this.maxSelectableItems = maxSelectableItems;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
            listener.handleEvent(this);
    }

    public List<String> getSelectablePlayers() {
        return selectablePlayers;
    }

    public int getMaxSelectableItems() {
        return maxSelectableItems;
    }
}
