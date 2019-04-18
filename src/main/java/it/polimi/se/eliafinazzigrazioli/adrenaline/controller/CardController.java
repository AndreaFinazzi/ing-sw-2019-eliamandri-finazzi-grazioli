package it.polimi.se.eliafinazzigrazioli.adrenaline.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.CardSelectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.CollectPlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.EffectSelectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.TargetSelectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;


public class CardController implements EventListenerInterface {

    @Override
    public void handleEvent(AbstractEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException("This listener does not implement proper handler for class: " + event.getClass());
    }

    @Override
    public void handleEvent(CardSelectedEvent event) {

    }

    @Override
    public void handleEvent(EffectSelectedEvent event) {

    }

    @Override
    public void handleEvent(TargetSelectedEvent event) {
        System.out.println("called for " + event.getPlayer());
    }

    @Override
    public void handleEvent(CollectPlayEvent event) {
        System.out.println("called for " + event.getPlayer());
    }
}
