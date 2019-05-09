package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

import java.util.List;

public class PlayersPlayersSelectedEvent extends AbstractViewEvent {

    List<String> players;

    public PlayersPlayersSelectedEvent(String player, List<String> players) {
        super(player);
        this.players = players;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
