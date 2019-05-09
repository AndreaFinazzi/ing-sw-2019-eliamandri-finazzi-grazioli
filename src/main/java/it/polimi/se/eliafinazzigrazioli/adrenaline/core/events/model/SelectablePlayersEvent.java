package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

import java.util.List;

public class SelectablePlayersEvent extends AbstractModelEvent {

    private List<String> selectablePlayers;
    private int maxSelectableItems;

    public SelectablePlayersEvent(String player, List<String> selectablePlayers, int maxSelectableItems) {
        super(player);
        this.selectablePlayers = selectablePlayers;
        this.maxSelectableItems = maxSelectableItems;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        if (this.getPlayer() == listener.getPlayer())
            listener.handleEvent(this);
    }
}
