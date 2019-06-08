package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;

public class MapVoteEvent extends AbstractViewEvent {

    private MapType votedMap;

    public MapVoteEvent(int clientID, String player, MapType mapType) {
        super(clientID, player);

        this.votedMap = mapType;
    }

    public MapType getVotedMap() {
        return votedMap;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
