package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.MatchController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

public class PlayerConnectedEvent extends AbstractViewEvent {

    @Override
    public void handle(EventListenerInterface listener, MatchController matchController) throws HandlerNotImplementedException {
        listener.handleEvent(this, matchController);
    }
}

