package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

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

    @Override
    public void handleEvent(MovePlayEvent event) {
        List<Coordinates> path = event.getPath();
        GameBoard gameBoard = match.getGameBoard();
        if (!gameBoard.pathIsValid(currentPlayer, path) || path.size() + movementsPerformed > Rules.MAX_MOVEMENTS){
            match.notifyObservers(new NotAllowedPlayEvent(currentPlayer.getPlayerNickname()));
            return;
        }

        movementsPerformed = path.size();
        match.notifyObservers(gameBoard.playerMovement(currentPlayer, path));

    }


}
