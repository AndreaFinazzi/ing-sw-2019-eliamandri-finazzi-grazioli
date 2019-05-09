package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PlayerBoard;

public class FinalFrenzyBeginEvent extends AbstractControllerEvent {

    private PlayerBoard frenzyBoard;

    public PlayerBoard getFrenzyBoard() {
        return frenzyBoard;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
