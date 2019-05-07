package it.polimi.se.eliafinazzigrazioli.adrenaline.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.CardSelectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.CollectPlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.EffectSelectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.TargetSelectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

// TODO implement controller
public class CardController implements EventListenerInterface {
    private MatchController matchController;

    public CardController(EventController eventController, MatchController matchController) {
        this.matchController = matchController;
        //listen to interesting events
        eventController.addEventListener(CardSelectedEvent.class, this);
        eventController.addEventListener(EffectSelectedEvent.class, this);
        eventController.addEventListener(TargetSelectedEvent.class, this);
        eventController.addEventListener(CollectPlayEvent.class, this);
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
    public void handleEvent(TargetSelectedEvent event) {
        System.out.println("called for " + event.getPlayer());
    }

    @Override
    public void handleEvent(CollectPlayEvent event) {
        System.out.println("called for " + event.getPlayer());
    }
}
