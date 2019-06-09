package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.CollectPlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.MovePlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ViewEventsListenerInterface;

public class PlayerController implements ViewEventsListenerInterface {
    private MatchController matchController;

    public PlayerController(EventController eventController, MatchController matchController) {
        this.matchController = matchController;
        //init to interesting events
//        eventController.addViewEventsListener(PlayerConnectedEvent.class, this);
        eventController.addViewEventsListener(MovePlayEvent.class, this);
    }

    @Override
    public void handleEvent(MovePlayEvent event) {
/*        Match match = matchController.getMatch();
        Player currentPlayer = matchController.getPlayers().get(event.getPlayer());
        List<Coordinates> path = event.getPath();
        GameBoard gameBoard = match.getGameBoard();
        if (!gameBoard.pathIsValid(currentPlayer, path, currentPlayer.getPlayerBoard().simpleMovementMaxMoves())){
            match.notifyObservers(new NotAllowedPlayEvent(currentPlayer.getPlayerNickname()));
            return;
        }

        match.notifyObservers(gameBoard.playerMovement(currentPlayer, path));
*/
    }

    @Override
    public void handleEvent(CollectPlayEvent event) {
/*
        List<AbstractModelEvent> eventList = new ArrayList<>();
        Match match = matchController.getMatch();
        Player currentPlayer = matchController.getPlayers().get(event.getPlayer());
        List<Coordinates> path = event.getPath();
        GameBoard gameBoard = match.getGameBoard();

        // Both if() blocks have to be kept because a different message will be add to the events.

        if (!gameBoard.pathIsValid(currentPlayer, path, currentPlayer.getPlayerBoard().preCollectionMaxMoves())){
            match.notifyObservers(new NotAllowedPlayEvent(currentPlayer.getPlayerNickname()));
            return;
        }

        //Controls if the collection action is valid in the destination square.
        if (!gameBoard.getBoardSquareByCoordinates(path.get(path.size()-1)).collectActionIsValid(currentPlayer)) {
            match.notifyObservers(new NotAllowedPlayEvent(currentPlayer.getPlayerNickname()));
            return;
        }

        // Movement execution
        eventList.add(gameBoard.playerMovement(currentPlayer, path));
        // Collection execution
        eventList.add(gameBoard.getBoardSquareByCoordinates(path.get(path.size()-1)).collect(currentPlayer, match.getPowerUpsDeck()));

        match.notifyObservers(eventList);
*/
    }


//    @Override
//    public void handleEvent(PlayerConnectedEvent event) {
//        try {
//            matchController.addPlayer(event.getPlayer());
//        } catch (PlayerAlreadyPresentException | MaxPlayerException e) {
//            e.printStackTrace();
//        }
//    }
}