package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AmmoCardCollectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.NotAllowedPlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.CollectPlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.MovePlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ViewEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.BoardSquare;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Match;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.List;

public class TurnController implements ViewEventsListenerInterface {

    private Match match;
    private Player currentPlayer;
    private BoardSquare startingPosition;
    private int actionsPerformed;
    private int movementsPerformed;

    public TurnController(EventController eventController, Match match) {
        this.match = match;
        eventController.addViewEventsListener(MovePlayEvent.class, this);
        eventController.addViewEventsListener(CollectPlayEvent.class, this);
    }

    public void beginTurn(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        startingPosition = match.getGameBoard().getPlayerPosition(currentPlayer);
        actionsPerformed = 0;
        movementsPerformed = 0;
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
    public void handleEvent(MovePlayEvent event) {
        List<Coordinates> path = event.getPath();
        GameBoard gameBoard = match.getGameBoard();

        // If this condition is verified it means that something isn't correct in the execution of the client
        // or alternatively this control can be used regularly to inhibit further actions
        if (actionsPerformed >= Rules.MAX_ACTIONS_AVAILABLE) {
            match.notifyObservers(new NotAllowedPlayEvent(currentPlayer.getPlayerNickname()));
            return;
        }


        if (!gameBoard.pathIsValid(currentPlayer, path) || path.size() + movementsPerformed > Rules.MAX_MOVEMENTS){
            match.notifyObservers(new NotAllowedPlayEvent(currentPlayer.getPlayerNickname()));
            return;
        }

        movementsPerformed += path.size();
        match.notifyObservers(gameBoard.playerMovement(currentPlayer, path));

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
        GameBoard gameBoard = match.getGameBoard();
        BoardSquare playerPosition = gameBoard.getPlayerPosition(currentPlayer);
        AmmoCardCollectedEvent generatedEvent;
        if (actionsPerformed < Rules.MAX_ACTIONS_AVAILABLE || movementsPerformed > currentPlayer.getPlayerBoard().preCollectionMaxMoves()) {
            generatedEvent = playerPosition.collect(currentPlayer, match.getPowerUpsDeck());
        }
        else {
            match.notifyObservers(new NotAllowedPlayEvent(currentPlayer.getPlayerNickname()));
            return;
        }
        if (generatedEvent == null)
            match.notifyObservers(new NotAllowedPlayEvent(currentPlayer.getPlayerNickname()));
        else {
            actionsPerformed++;
            startingPosition = playerPosition;
            movementsPerformed = 0;
            match.notifyObservers(generatedEvent);
        }

    }
}
