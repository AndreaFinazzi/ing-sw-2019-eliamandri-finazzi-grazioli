package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

import java.util.List;

public class PlayersSelectedEvent extends AbstractViewEvent {

    List<String> players;

    public PlayersSelectedEvent(int clientID, String player, List<String> players) {
        super(clientID, player);
        this.players = players;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public List<String> getPlayers() {
        return players;
    }
}
