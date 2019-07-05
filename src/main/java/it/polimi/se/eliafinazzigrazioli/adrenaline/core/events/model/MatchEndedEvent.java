package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class MatchEndedEvent extends AbstractModelEvent {

    String winner;

    public MatchEndedEvent(String winner) {
        this.winner = winner;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public String getWinner() {
        return winner;
    }
}
