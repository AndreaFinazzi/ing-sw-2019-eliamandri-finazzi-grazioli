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
import java.util.Arrays;
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
        eventController.addViewEventsListener(SquareSelectedEvent.class, this);
        eventController.addViewEventsListener(PlayersSelectedEvent.class, this);
        eventController.addViewEventsListener(RoomSelectedEvent.class, this);
    }

    @Override
    public void handleEvent(AbstractEvent event) throws HandlerNotImplementedException {
        throw new HandlerNotImplementedException(event.getClass().getName());
    }

    @Override
    public void handleEvent(WeaponToUseSelectedEvent event) throws HandlerNotImplementedException {
        Player currentPlayer = match.getCurrentPlayer();
        currentPlayingWeapon = currentPlayer.getWeaponByName(event.getWeaponName());
        if (!event.getPlayer().equals(currentPlayer.getPlayerNickname())) {
            LOGGER.log(Level.SEVERE, "Player tried use a card out of his turn.", new Exception());
            return;
        }
        if (!currentPlayingWeapon.isLoaded()) {
            LOGGER.log(Level.SEVERE, "Player tried to use an unloaded card.", new Exception());
            LOGGER.log(Level.INFO, currentPlayingWeapon.getWeaponName());
            return;
        }
        List<AbstractModelEvent> events = new ArrayList<>();
        GameBoard gameBoard = match.getGameBoard();
        List<Coordinates> path = event.getPath();
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
        if (!event.getPlayer().equals(match.getCurrentPlayer().getPlayerNickname())) {
            LOGGER.log(Level.SEVERE, "Player tried use a card out of his turn.", new Exception());
            return;
        }
        if (event.getEffect() != null) {
            currentPlayingWeapon.setLoaded(false);
            Player currentPlayer = match.getCurrentPlayer();
            currentPlayingWeapon.setActiveEffect(event.getEffect());
            currentExecutingEffect = currentPlayingWeapon.getEffectByName(event.getEffect());
            List<PowerUpCard> powerUpsToSpend = currentPlayer.getRealModelReferences(event.getPowerUpsToPay());
            List<Ammo> ammosSpent = currentPlayer.spendPrice(currentExecutingEffect.getPrice(), powerUpsToSpend, match.getPowerUpsDeck());
            match.notifyObservers(new PaymentExecutedEvent(currentPlayer, powerUpsToSpend, ammosSpent));

            currentExecutingEffect.initialize();

            effectExecutionLoop(currentPlayer);
        }
    }

    @Override
    public void handleEvent(SquareSelectedEvent event) throws HandlerNotImplementedException {
        List<BoardSquare> selectedBoardSquares = new ArrayList<>();
        GameBoard gameBoard = match.getGameBoard();
        for (Coordinates coordinates: event.getSquares())
            selectedBoardSquares.add(gameBoard.getBoardSquareByCoordinates(coordinates));
        currentExecutingEffect.addSelectedBoardSquares(selectedBoardSquares);

        effectExecutionLoop(match.getCurrentPlayer());
    }

    @Override
    public void handleEvent(PlayersSelectedEvent event) throws HandlerNotImplementedException {
        List<Player> selectedPlayers = new ArrayList<>();
        for (String player: event.getPlayers())
            selectedPlayers.add(match.getPlayer(player));
        currentExecutingEffect.addSelectedPlayers(selectedPlayers);

        effectExecutionLoop(match.getCurrentPlayer());
    }

    @Override
    public void handleEvent(RoomSelectedEvent event) throws HandlerNotImplementedException {
        currentExecutingEffect.addSelectedRooms(new ArrayList<>(Arrays.asList(event.getRoom())));
        effectExecutionLoop(match.getCurrentPlayer());
    }

    private void effectExecutionLoop(Player currentPlayer) {
        while (!currentExecutingEffect.needsSelection() && currentExecutingEffect.hasNextState()) {
            currentExecutingEffect.nextState();
            match.notifyObservers(currentExecutingEffect.execute(currentPlayingWeapon, match.getGameBoard(), currentPlayer));
        }
        currentExecutingEffect.setNeedsSelection(false);

        if (!currentExecutingEffect.hasNextState()) {
            currentExecutingEffect.setAlreadyUsed(true);
            match.notifyObservers(new SelectableEffectsEvent(currentPlayer, currentPlayingWeapon.getWeaponName(), currentExecutingEffect.getNextCallableEffects(currentPlayingWeapon)));
        }
    }
}
