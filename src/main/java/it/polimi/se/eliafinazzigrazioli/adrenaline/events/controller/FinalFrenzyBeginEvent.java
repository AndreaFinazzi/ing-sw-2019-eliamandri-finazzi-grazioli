package it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.MatchController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.PlayerBoard;

public class FinalFrenzyBeginEvent extends AbstractControllerEvent {

    private PlayerBoard frenzyBoard;

    public PlayerBoard getFrenzyBoard() {
        return frenzyBoard;
    }

    @Override
    public void handle(EventListenerInterface listener, MatchController matchController) throws HandlerNotImplementedException {
        listener.handleEvent(this, matchController);
    }
}
