package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.ActionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.PlayerMovementEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

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

    private Player playerUsingPowerUp;
    private PowerUpCard powerUpUsed;
    private boolean powerUpIsActive;
    private Player playerForNewton;


    public CardController(EventController eventController, Match match) {
        this.match = match;
        currentPlayingWeapon = null;
        currentExecutingEffect = null;
        //init to interesting events
        eventController.addViewEventsListener(WeaponToUseSelectedEvent.class, this);
        eventController.addViewEventsListener(EffectSelectedEvent.class, this);
        eventController.addViewEventsListener(SquareSelectedEvent.class, this);
        eventController.addViewEventsListener(PlayersSelectedEvent.class, this);
        eventController.addViewEventsListener(RoomSelectedEvent.class, this);
        eventController.addViewEventsListener(PowerUpsToUseEvent.class, this);
        eventController.addViewEventsListener(UseTurnPowerUpEvent.class, this);
        eventController.addViewEventsListener(DirectionSelectedEvent.class, this);
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
        if (!powerUpIsActive) {
            List<BoardSquare> selectedBoardSquares = new ArrayList<>();
            GameBoard gameBoard = match.getGameBoard();
            for (Coordinates coordinates: event.getSquares())
                selectedBoardSquares.add(gameBoard.getBoardSquareByCoordinates(coordinates));
            currentExecutingEffect.addSelectedBoardSquares(selectedBoardSquares);

            effectExecutionLoop(match.getCurrentPlayer());
        }
        else {
            if (powerUpUsed.getType().equals("Teleporter")) {
                match.getCurrentPlayer().removePowerUp(powerUpUsed);
                match.getGameBoard().movePlayer(match.getCurrentPlayer(), match.getGameBoard().getBoardSquareByCoordinates(event.getSquares().get(0)));
                match.notifyObservers(new PlayerMovedByWeaponEvent(match.getCurrentPlayer(), powerUpUsed.getType(), match.getCurrentPlayer().getPlayerNickname(), event.getSquares().get(0)));
                powerUpIsActive = false;
                match.getPowerUpsDeck().discardPowerUp(powerUpUsed);
            } else if (powerUpUsed.getType().equals("Newton")) {
                match.getCurrentPlayer().removePowerUp(powerUpUsed);
                match.getGameBoard().movePlayer(playerForNewton, match.getGameBoard().getBoardSquareByCoordinates(event.getSquares().get(0)));
                match.notifyObservers(new PlayerMovedByWeaponEvent(match.getCurrentPlayer(), powerUpUsed.getType(), playerForNewton.getPlayerNickname(), event.getSquares().get(0)));
                powerUpIsActive = false;
            }
            if (match.getPhase() == MatchPhase.FINAL_FRENZY) {
                if (match.getCurrentPlayer().isFinalFrenzyDoubleActionEnabled()) {
                    match.notifyObservers(new FinalFrenzyActionRequestEvent(
                            match.getCurrentPlayer(),
                            Rules.MAX_ACTIONS_AVAILABLE,
                            Rules.FINAL_FRENZY_DOUBLE_ACTION_MAX_MOVES,
                            Rules.FINAL_FRENZY_DOUBLE_ACTION_MAX_MOVES_BEFORE_COLLECTION,
                            Rules.FINAL_FRENZY_DOUBLE_ACTION_MAX_MOVES_BEFORE_SHOOTING,
                            false));
                } else {
                    match.notifyObservers(new FinalFrenzyActionRequestEvent(
                            match.getCurrentPlayer(),
                            Rules.MAX_ACTIONS_AVAILABLE,
                            0,
                            Rules.FINAL_FRENZY_SINGLE_ACTION_MAX_MOVES_BEFORE_COLLECTION,
                            Rules.FINAL_FRENZY_SINGLE_ACTION_MAX_MOVES_BEFORE_SHOOTING,
                            true));
                }
            } else {
                match.notifyObservers(new ActionRequestEvent(
                        match.getCurrentPlayer(),
                        Rules.MAX_ACTIONS_AVAILABLE,
                        match.getCurrentPlayer().getPlayerBoard().simpleMovementMaxMoves(),
                        match.getCurrentPlayer().getPlayerBoard().preCollectionMaxMoves(),
                        match.getCurrentPlayer().getPlayerBoard().preShootingMaxMoves()));
            }

        }
    }

    @Override
    public void handleEvent(PlayersSelectedEvent event) throws HandlerNotImplementedException {
        if (!powerUpIsActive) {
            List<Player> selectedPlayers = new ArrayList<>();
            for (String player: event.getPlayers())
                selectedPlayers.add(match.getPlayer(player));
            currentExecutingEffect.addSelectedPlayers(selectedPlayers);

            effectExecutionLoop(match.getCurrentPlayer());
        } else if (powerUpUsed.getType().equals("Newton")) {
            playerForNewton = match.getPlayer(event.getPlayers().get(0));
            match.notifyObservers(new SelectableBoardSquaresEvent(match.getCurrentPlayer(), match.getGameBoard().getSquaresOnCardinals(playerForNewton, 2), 1));
        }
    }

    @Override
    public void handleEvent(RoomSelectedEvent event) throws HandlerNotImplementedException {
        currentExecutingEffect.addSelectedRooms(new ArrayList<>(Arrays.asList(event.getRoom())));
        effectExecutionLoop(match.getCurrentPlayer());
    }

    @Override
    public void handleEvent(DirectionSelectedEvent event) throws HandlerNotImplementedException {
        currentExecutingEffect.addSelectedDirections(new ArrayList<>(Arrays.asList(event.getDirection())));
        effectExecutionLoop(match.getCurrentPlayer());
    }

    @Override
    public void handleEvent(PowerUpsToUseEvent event) throws HandlerNotImplementedException {
        Player shooter = match.getPlayer(event.getPlayer());
        Player target = match.getPlayer(event.getTarget());
        PowerUpCard powerUpToPay = null;
        PowerUpCard powerUpUsed = null;
        if (event.getPowerUpType().equals("Targeting Scope")) {
            if (event.getPowerUpToPayId() != null) {
                for (PowerUpCard powerUpCard: shooter.getPowerUps()) {
                    if (event.getPowerUpToPayId().equals(powerUpCard.getId())) {
                        powerUpToPay = powerUpCard;
                    }
                }
            }
            if (shooter.canSpend(Arrays.asList(event.getAmmoToPayColor()), powerUpToPay == null ? new ArrayList<>() : Arrays.asList())) {
                List<Ammo> ammoSpent = shooter.spendPrice(Arrays.asList(event.getAmmoToPayColor()), powerUpToPay == null ? new ArrayList<>() : Arrays.asList(), match.getPowerUpsDeck());
                match.notifyObservers(new PaymentExecutedEvent(shooter, powerUpToPay == null ? Arrays.asList() : Arrays.asList(powerUpToPay), ammoSpent));
            }
            if (event.getPowerUpUsedId() != null) {
                for (PowerUpCard powerUpCard: shooter.getPowerUps()) {
                    if (event.getPowerUpUsedId().equals(powerUpCard.getId()))
                        powerUpUsed = powerUpCard;
                }
            }
            if (powerUpUsed != null) {
                shooter.spendPrice(Arrays.asList(powerUpUsed.getAmmo()), Arrays.asList(powerUpUsed), match.getPowerUpsDeck());
                match.notifyObservers(new PaymentExecutedEvent(shooter, powerUpUsed == null ? Arrays.asList() : Arrays.asList(powerUpUsed), Arrays.asList()));
                target.getPlayerBoard().addDamage(shooter.getDamageMarkDelivered());
                match.notifyObservers(new PlayerShotEvent(shooter, target.getPlayerNickname(), Arrays.asList(shooter.getDamageMarkDelivered()), Arrays.asList(), Arrays.asList()));
            }
        } else if (event.getPowerUpType().equals("Tagback Grenade")) {
            if (event.getPowerUpUsedId() != null) {
                for (PowerUpCard powerUpCard: shooter.getPowerUps()) {
                    if (event.getPowerUpUsedId().equals(powerUpCard.getId()))
                        powerUpUsed = powerUpCard;
                }
            }
            if (powerUpUsed != null) {
                shooter.spendPrice(Arrays.asList(powerUpUsed.getAmmo()), Arrays.asList(powerUpUsed), match.getPowerUpsDeck());
                match.notifyObservers(new PaymentExecutedEvent(shooter, powerUpUsed == null ? Arrays.asList() : Arrays.asList(powerUpUsed), Arrays.asList()));
                target.getPlayerBoard().addMark(shooter.getDamageMarkDelivered());
                match.notifyObservers(new PlayerShotEvent(shooter, target.getPlayerNickname(), Arrays.asList(), Arrays.asList(shooter.getDamageMarkDelivered()), Arrays.asList()));
            }
        }
    }

    @Override
    public void handleEvent(UseTurnPowerUpEvent event) throws HandlerNotImplementedException {
        List<AbstractModelEvent> events = new ArrayList<>();
        Player currentPLayer = match.getCurrentPlayer();
        PowerUpCard powerUpToUse = null;
        for (PowerUpCard powerUpCard: currentPLayer.getPowerUps()) {
            if (powerUpCard.getId().equals(event.getPowerUpId()))
                powerUpToUse = powerUpCard;
        }
        if (powerUpToUse != null) {
            powerUpIsActive = true;
            powerUpUsed = powerUpToUse;
            if (powerUpToUse.getType().equals("Teleporter")) {
                events.add(new PaymentExecutedEvent(currentPLayer, Arrays.asList(powerUpUsed), Arrays.asList()));
                events.add(new SelectableBoardSquaresEvent(currentPLayer, match.getGameBoard().getSquaresList(), 1));
            } else if (powerUpToUse.getType().equals("Newton")) {
                List<String> players = new ArrayList<>();
                for (Player player: match.getPlayers()) {
                    if (player != currentPLayer)
                        players.add(player.getPlayerNickname());
                }
                events.add(new PaymentExecutedEvent(currentPLayer, Arrays.asList(powerUpUsed), Arrays.asList()));
                events.add(new SelectablePlayersEvent(currentPLayer, players, 1));
            }

        }
        else {
            if (match.getPhase() == MatchPhase.FINAL_FRENZY) {
                if (match.getCurrentPlayer().isFinalFrenzyDoubleActionEnabled()) {
                    events.add(new FinalFrenzyActionRequestEvent(
                            match.getCurrentPlayer(),
                            Rules.MAX_ACTIONS_AVAILABLE,
                            Rules.FINAL_FRENZY_DOUBLE_ACTION_MAX_MOVES,
                            Rules.FINAL_FRENZY_DOUBLE_ACTION_MAX_MOVES_BEFORE_COLLECTION,
                            Rules.FINAL_FRENZY_DOUBLE_ACTION_MAX_MOVES_BEFORE_SHOOTING,
                            false));
                } else {
                    events.add(new FinalFrenzyActionRequestEvent(
                            match.getCurrentPlayer(),
                            Rules.MAX_ACTIONS_AVAILABLE,
                            0,
                            Rules.FINAL_FRENZY_SINGLE_ACTION_MAX_MOVES_BEFORE_COLLECTION,
                            Rules.FINAL_FRENZY_SINGLE_ACTION_MAX_MOVES_BEFORE_SHOOTING,
                            true));
                }
            } else {
                events.add(new ActionRequestEvent(
                        match.getCurrentPlayer(),
                        Rules.MAX_ACTIONS_AVAILABLE,
                        currentPLayer.getPlayerBoard().simpleMovementMaxMoves(),
                        currentPLayer.getPlayerBoard().preCollectionMaxMoves(),
                        currentPLayer.getPlayerBoard().preShootingMaxMoves()));
            }
        }
        match.notifyObservers(events);
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
