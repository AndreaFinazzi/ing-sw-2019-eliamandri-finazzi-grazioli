package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

// TODO implement controller
public class CardController implements ViewEventsListenerInterface {
    private MatchController matchController;

    public CardController(EventController eventController, MatchController matchController) {
        this.matchController = matchController;
        //listen to interesting events
        eventController.addViewEventsListener(CardSelectedEvent.class, this);
        eventController.addViewEventsListener(EffectSelectedEvent.class, this);
        eventController.addViewEventsListener(TargetSelectedEvent.class, this);
        eventController.addViewEventsListener(CollectPlayEvent.class, this);
        eventController.addViewEventsListener(WeaponToUseSelectedEvent.class, this);
    }

    @Override
    public void handleEvent(AbstractEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException(event.getClass().getName());
    }

    @Override
    public void handleEvent(CardSelectedEvent event) {

    }

    @Override
    public void handleEvent(EffectSelectedEvent event) {
        //matchController.getActiveWeapon().setActiveEffect();
        //new WeaponCard().executeStep();
    }

    @Override
    public void handleEvent(WeaponToUseSelectedEvent event) throws HandlerNotImplementedException {

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
