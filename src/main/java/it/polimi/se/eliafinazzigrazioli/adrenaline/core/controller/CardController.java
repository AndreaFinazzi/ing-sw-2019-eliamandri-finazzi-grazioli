package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.PaymentExecutedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.SelectableEffectsEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.PlayerMovementEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO implement controller
public class CardController implements ViewEventsListenerInterface {

    private static final Logger LOGGER = Logger.getLogger(MatchController.class.getName());

    private Match match;

    private WeaponCard currentPlayingWeapon;
    private WeaponEffect currentExecutingEffect;


    public CardController(EventController eventController, Match match) {
        this.match = match;
        currentPlayingWeapon = null;
        currentExecutingEffect = null;
        //init to interesting events
        eventController.addViewEventsListener(WeaponToUseSelectedEvent.class, this);
        eventController.addViewEventsListener(EffectSelectedEvent.class, this);
        eventController.addViewEventsListener(TargetSelectedEvent.class, this);
        eventController.addViewEventsListener(EffectSelectedEvent.class, this);
    }

    @Override
    public void handleEvent(AbstractEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException(event.getClass().getName());
    }

    @Override
    public void handleEvent(WeaponToUseSelectedEvent event) throws HandlerNotImplementedException {
        if (event.getPlayer().equals(match.getCurrentPlayer().getPlayerNickname())) {
            LOGGER.log(Level.SEVERE, "Player tried use a card out of his turn.", new Exception());
            return;
        }
        if (!currentPlayingWeapon.isLoaded()) {
            LOGGER.log(Level.SEVERE, "Player tried to use an unloaded card.", new Exception());
            return;
        }
        List<AbstractModelEvent> events = new ArrayList<>();
        GameBoard gameBoard = match.getGameBoard();
        Player currentPlayer = match.getCurrentPlayer();
        List<Coordinates> path = event.getPath();
        currentPlayingWeapon = currentPlayer.getWeaponByName(event.getWeaponName());
        currentPlayingWeapon.initialize();
        List<String> callableEffects = currentPlayingWeapon.getCallableEffects();

        if (!path.isEmpty()) {
            gameBoard.movePlayer(currentPlayer, gameBoard.getBoardSquareByCoordinates(path.get(path.size()-1)));
            events.add(new PlayerMovementEvent(currentPlayer, path));
        }

        events.add(new SelectableEffectsEvent(currentPlayer, currentPlayingWeapon.getWeaponName(), callableEffects));
        match.notifyObservers(events);
    }

    @Override
    public void handleEvent(EffectSelectedEvent event) {
        if (event.getPlayer().equals(match.getCurrentPlayer().getPlayerNickname())) {
            LOGGER.log(Level.SEVERE, "Player tried use a card out of his turn.", new Exception());
            return;
        }

        List<AbstractModelEvent> events = new ArrayList<>();
        Player currentPlayer = match.getCurrentPlayer();
        currentExecutingEffect = currentPlayingWeapon.getEffectByName(event.getEffect());
        List<PowerUpCard> powerUpsToSpend = currentPlayer.getRealModelReferences(event.getPowerUpsToPay());
        List<Ammo> ammosSpent = currentPlayer.spendPrice(currentExecutingEffect.getPrice(), powerUpsToSpend);
        match.notifyObservers(new PaymentExecutedEvent(currentPlayer, powerUpsToSpend, ammosSpent));

        currentExecutingEffect.initialize();



    }




    @Override
    public void handleEvent(CardSelectedEvent event) {

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
