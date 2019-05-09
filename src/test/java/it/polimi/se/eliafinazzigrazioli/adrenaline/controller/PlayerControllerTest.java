package it.polimi.se.eliafinazzigrazioli.adrenaline.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.MovePlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Coordinates;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerControllerTest {

    private PlayerController playerController;
    private MatchController matchController;
    private EventController eventController;
    private GameBoard gameBoard;
    private PlayerBoard playerBoard;

    @Before
    public void setUp() throws Exception {
        gameBoard = new GameBoard (MapType.ONE);
        matchController  = new MatchController ();
        eventController = new EventController (matchController);
        playerController = new PlayerController (eventController, matchController);
        matchController.addPlayer ("playerOne");

        matchController.getPlayers ().get ("playerOne").setPosition (gameBoard.getBoardSquareByCoordinates (
                new Coordinates (0,0)
        ));
        playerBoard = new PlayerBoard ();
        Player playerOne = matchController.getPlayers ().get ("playerOne");

        matchController.getMatch ().getMap ().movePlayer (playerOne, gameBoard.getBoardSquareByCoordinates (new Coordinates (0,0)));
        matchController.getPlayers ().get ("playerOne").setPlayerBoard (playerBoard);
    }

    @Test
    public void hadleEventTest() {
        List<Coordinates> coordinatesList = new ArrayList<> ();
        //coordinatesList.add (new Coordinates (0,0));
        coordinatesList.add (new Coordinates (1,0));
        coordinatesList.add (new Coordinates (1,1));
        MovePlayEvent movePlayEvent = new MovePlayEvent ("playerOne", coordinatesList);
        playerController.handleEvent (movePlayEvent);
        Player playerOne = matchController.getPlayers ().get ("playerOne");
        BoardSquare playerPos = matchController.getMatch ().getMap ().getPlayerPosition (playerOne);
        BoardSquare boardSquare = gameBoard.getBoardSquareByCoordinates (new Coordinates (1,1));
        System.out.println (matchController.getMatch ().getPlayers ().size ());
        assertEquals (boardSquare, playerPos);

    }
}