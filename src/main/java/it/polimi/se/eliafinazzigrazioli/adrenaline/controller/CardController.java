package it.polimi.se.eliafinazzigrazioli.adrenaline.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.CardSelectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.CollectPlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.EffectSelectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.TargetSelectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

// TODO implement controller
public class CardController implements EventListenerInterface {

    public CardController(EventController eventController) {
        //listen to interesting events
        eventController.addEventListener(CardSelectedEvent.class, this);
        eventController.addEventListener(EffectSelectedEvent.class, this);
        eventController.addEventListener(TargetSelectedEvent.class, this);
        eventController.addEventListener(CollectPlayEvent.class, this);
    }

    @Override
    public void handleEvent(AbstractEvent event, MatchController matchController) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException(event.getClass().getName());
    }

    @Override
    public void handleEvent(CardSelectedEvent event, MatchController matchController) {

    }

    @Override
    public void handleEvent(EffectSelectedEvent event, MatchController matchController) {
        //matchController.getActiveWeapon().setActiveEffect();
        //new WeaponCard().executeStep();
    }

    @Override
    public void handleEvent(TargetSelectedEvent event, MatchController matchController) {
        System.out.println("called for " + event.getPlayer());
    }

    @Override
    public void handleEvent(CollectPlayEvent event, MatchController matchController) {
        System.out.println("called for " + event.getPlayer());
    }
}
