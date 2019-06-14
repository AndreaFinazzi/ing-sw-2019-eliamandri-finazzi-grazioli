package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AmmoCardCollectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.BeginTurnEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.ActionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.NotAllowedPlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.SpawnSelectionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.ReloadWeaponsRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.AmmoCollectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.PlayerMovementEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.PowerUpsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

//TODO DEFINE AND INSERT MESSAGES, IN PARTICULAR FOR NOT ALLOWED PLAY EVENT BECAUSE IT IS THROWN IN A VARIETY OF SITUATIONS

public class TurnController implements ViewEventsListenerInterface {

    private static final Logger LOGGER = Logger.getLogger(MatchController.class.getName());

    private Match match;

    private int actionsPerformed;


    public TurnController(EventController eventController, Match match) {
        this.match = match;
        this.actionsPerformed = 0;
        eventController.addViewEventsListener(MovePlayEvent.class, this);
        eventController.addViewEventsListener(CollectPlayEvent.class, this);
        eventController.addViewEventsListener(SpawnPowerUpSelected.class, this);
        eventController.addViewEventsListener(ReloadWeaponEvent.class, this);
    }

    public void beginTurn() {
        actionsPerformed = 0;
    }

    /**
     * Handles the event generated when the player has to select a power up to spawn when the game begins. It spawns
     * the player on the map and adds the other power up to the player, contextually generating:
     * -{@code PlayerSpawnedEvent}
     * -{@code PowerUpCollectedEvent}
     * -{@code BeginTurnEvent}
     * and notifying them to the views.
     * @param event
     * @throws HandlerNotImplementedException
     */
    @Override
    public void handleEvent(SpawnPowerUpSelected event) throws HandlerNotImplementedException {
        List<AbstractModelEvent> events = new ArrayList<>();
        GameBoard gameBoard = match.getGameBoard();
        Player currentPlayer = match.getCurrentPlayer();
        events.add(gameBoard.spawnPlayer(currentPlayer, event.getSpawnCard()));
        events.add(currentPlayer.addPowerUp(event.getToKeep(), match.getPowerUpsDeck()));
        match.getPowerUpsDeck().discardPowerUp(event.getSpawnCard());
        events.add(new ActionRequestEvent(
                currentPlayer,
                Rules.MAX_ACTIONS_AVAILABLE,
                currentPlayer.getPlayerBoard().simpleMovementMaxMoves(),
                currentPlayer.getPlayerBoard().preCollectionMaxMoves(),
                currentPlayer.getPlayerBoard().preShootingMaxMoves()));

        match.notifyObservers(events);
    }

    /**
     * Handles a request of movement by the player. The handler guaranties that the movement is performed only if the
     * total number of moves (counting also possible moves performed before) doesn't exceed the total number of moves allowed and
     * that the total number of actions allowed is not exceeded.
     * If any of these controls fail it notifies via match a {@code NotAllowedPlayEvent}. If the controls succeed the movement is
     * performed and a {@code PlayerMovementEvent} is notified. Collaterally the {@code movementsPerformed} ins increased by
     * the number of moves performed.
     * @param event
     */
    @Override
    public void handleEvent(MovePlayEvent event) throws HandlerNotImplementedException {
        Player currentPlayer = match.getCurrentPlayer();
        if (!currentPlayer.getPlayerNickname().equals(event.getPlayer())) {
            LOGGER.info("Player " + event.getPlayer() + " tried to move out of his turn!");
            return;
        }
        List<Coordinates> path = event.getPath();
        GameBoard gameBoard = match.getGameBoard();
        List<AbstractModelEvent> events = new ArrayList<>();
        // If this condition is verified it means that something isn't correct in the execution of the client
        // or alternatively this control can be used regularly to inhibit further actions
        if (actionsPerformed >= Rules.MAX_ACTIONS_AVAILABLE)
            match.notifyObservers(new NotAllowedPlayEvent(currentPlayer));

        else if (!gameBoard.pathIsValid(currentPlayer, path) || path.size() > currentPlayer.getPlayerBoard().simpleMovementMaxMoves())
            match.notifyObservers(new NotAllowedPlayEvent(currentPlayer));

        else{
            actionsPerformed++;
            events.add(gameBoard.playerMovement(currentPlayer, path));
            if (actionsPerformed < Rules.MAX_ACTIONS_AVAILABLE)
                events.add(new ActionRequestEvent(
                        currentPlayer,
                        Rules.MAX_ACTIONS_AVAILABLE - actionsPerformed,
                        currentPlayer.getPlayerBoard().simpleMovementMaxMoves(),
                        currentPlayer.getPlayerBoard().preCollectionMaxMoves(),
                        currentPlayer.getPlayerBoard().preShootingMaxMoves()
                ));
            else
                events.add(new ReloadWeaponsRequestEvent(currentPlayer));
            match.notifyObservers(events);
        }

    }

    /**
     * Handles a request of movement by the player. It controls that the total number of actions or moves allowed is not exceeded.
     * If the control fail it notifies via match a {@code NotAllowedPlayEvent}. If it succeeds the method {@code BoardSquare.collect()}.
     * The return of null value by this method means that collection can't be performed on that square because it doesn't contain
     * any item to collect. If a non null value is returned, {@code actionsPerformed} is incremented, {@code startingPosition} is updated
     * to the current position of the player and {@code movementsPerformed} is reset to 0. Then a {@code AmmoCardCollectedEvent} event
     * is notified.
     * @param event
     */
    @Override
    public void handleEvent(CollectPlayEvent event) {
        List<Coordinates> path = event.getPath();
        GameBoard gameBoard = match.getGameBoard();
        Player currentPlayer = match.getCurrentPlayer();
        boolean movementActuaded = false;
        List<AbstractModelEvent> events = new ArrayList<>();
        BoardSquare finalPosition;
        if (actionsPerformed < Rules.MAX_ACTIONS_AVAILABLE && path.size() <= currentPlayer.getPlayerBoard().preCollectionMaxMoves()) {
            if (path.size() > 0) {
                finalPosition = gameBoard.getBoardSquareByCoordinates(path.get(path.size()-1));
                movementActuaded = true;
            }
            else
                finalPosition = gameBoard.getPlayerPosition(currentPlayer);
            if (movementActuaded)
            {
                gameBoard.movePlayer(currentPlayer, finalPosition);
                events.add(new PlayerMovementEvent(currentPlayer, path));
            }
            if (finalPosition.ammoCollectionIsValid()) {
                AmmoCard ammoCard = ((GenericBoardSquare) finalPosition).gatherCollectables();
                //todo verify if it's better to sent the entire ammoCard somehow
                for (Ammo ammo: ammoCard.getAmmos())
                    events.add(new AmmoCollectedEvent(currentPlayer, ammo, currentPlayer.addAmmo(ammo)));
                if (ammoCard.containsPowerUpCard()) {
                    PowerUpsDeck deck = match.getPowerUpsDeck();
                    events.add(currentPlayer.addPowerUp(deck.drawCard(), deck));
                }
                events.add(new AmmoCardCollectedEvent(currentPlayer, ammoCard, finalPosition.getCoordinates()));
                actionsPerformed++;
                if (actionsPerformed < Rules.MAX_ACTIONS_AVAILABLE)
                    events.add(new ActionRequestEvent(
                        currentPlayer,
                        Rules.MAX_ACTIONS_AVAILABLE-actionsPerformed,
                        currentPlayer.getPlayerBoard().simpleMovementMaxMoves(),
                        currentPlayer.getPlayerBoard().preCollectionMaxMoves(),
                        currentPlayer.getPlayerBoard().preShootingMaxMoves()));
                else
                    events.add(new ReloadWeaponsRequestEvent(currentPlayer));
                match.notifyObservers(events);
            }
            else
                match.notifyObservers(new NotAllowedPlayEvent(currentPlayer));
        }
        else {
            match.notifyObservers(new NotAllowedPlayEvent(currentPlayer));
        }
    }

    @Override
    public void handleEvent(ReloadWeaponEvent event) throws HandlerNotImplementedException {
        if (event.getWeapon() == null) {
            beginTurn();
            match.nextTurn();
        }
        else {
            //todo payment logic and further reload request
        }
    }

    @Override
    public void handleEvent(EndTurnRequestEvent event) throws HandlerNotImplementedException {
        //todo points assignment and end turn resets
        match.getGameBoard().ammoCardsSetup(match.getAmmoCardsDeck());
        match.nextCurrentPlayer();
        beginTurn();
        if (match.getTurn() != 0)
            match.notifyObservers(new BeginTurnEvent(match.getCurrentPlayer().getPlayerNickname()));
        else {
            PowerUpsDeck deck = match.getPowerUpsDeck();
            match.notifyObservers(new SpawnSelectionRequestEvent(match.getCurrentPlayer(), Arrays.asList(deck.drawCard(), deck.drawCard())));
        }
    }
}
