package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

import java.util.List;

public class PlayersSelectedEvent extends AbstractViewEvent {

    List<String> players;

    public PlayersSelectedEvent(String player, List<String> players) {
        super(player);
        this.players = players;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
