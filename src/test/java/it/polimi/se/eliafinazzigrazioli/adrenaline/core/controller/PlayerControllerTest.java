package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.MovePlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.MatchBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * The type Player controller test.
 */
public class PlayerControllerTest {

    private MatchController matchController;
    private EventController eventController;
    private GameBoard gameBoard;
    private PlayerBoard playerBoard;

    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        matchController = new MatchController(new MatchBuilder());
        eventController = new EventController(matchController);
        matchController.addPlayer("playerOne", Avatar.BANSHEE);
        matchController.initMatch(MapType.ONE);
        gameBoard = matchController.getMatch().getGameBoard();
        gameBoard.setPlayerPositions(matchController.getPlayers().get("playerOne"),
                gameBoard.getBoardSquareByCoordinates(
                        new Coordinates(0, 0)));

        matchController.getPlayers().get("playerOne").setPosition(gameBoard.getBoardSquareByCoordinates(
                new Coordinates(0, 0)
        ));
        playerBoard = new PlayerBoard();
        Player playerOne = matchController.getPlayers().get("playerOne");

        matchController.getMatch().getGameBoard().movePlayer(playerOne, gameBoard.getBoardSquareByCoordinates(new Coordinates(0, 0)));
        matchController.getPlayers().get("playerOne").setPlayerBoard(playerBoard);
    }

    /**
     * Handle event test.
     */
    @Test
    public void handleEventTest() {
        List<Coordinates> coordinatesList = new ArrayList<>();
        coordinatesList.add(new Coordinates(1, 0));
        coordinatesList.add(new Coordinates(1, 1));
        MovePlayEvent movePlayEvent = new MovePlayEvent(0, "playerOne", coordinatesList);
        Player playerOne = matchController.getPlayers().get("playerOne");
        BoardSquare playerPos = matchController.getMatch().getGameBoard().getPlayerPosition(playerOne);
        BoardSquare boardSquare = gameBoard.getBoardSquareByCoordinates(new Coordinates(1, 1));
        System.out.println(matchController.getMatch().getPlayers().size());
        assertEquals(boardSquare, playerPos);
    }
}