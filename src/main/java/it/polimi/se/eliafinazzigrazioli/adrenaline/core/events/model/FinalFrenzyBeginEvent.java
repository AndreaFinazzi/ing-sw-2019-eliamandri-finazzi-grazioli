package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PlayerBoard;

public class FinalFrenzyBeginEvent extends AbstractModelEvent {

    private PlayerBoard frenzyBoard;

    public FinalFrenzyBeginEvent(String player) {
        super(player);
    }

    public PlayerBoard getFrenzyBoard() {
        return frenzyBoard;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
