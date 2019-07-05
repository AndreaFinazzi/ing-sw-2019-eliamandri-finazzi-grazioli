package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.OutOfBoundBoardException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GameBoardTest {
    private GameBoard gameBoard;
    Player firstPlayer;
    Player secondPlayer;
    Player thirdPlayer;
    Player fourthPlayer;
    Player fifthPlayer;
    @Before
    public void setUp() {
        gameBoard = new GameBoard(MapType.TWO);
        gameBoard = new GameBoard(MapType.THREE);
        gameBoard = new GameBoard(MapType.FOUR);
        gameBoard = new GameBoard(MapType.ONE);
        firstPlayer = new Player("First");
        secondPlayer = new Player("Two");
        thirdPlayer = new Player("Third");
        fourthPlayer = new Player("Fourth");
        fifthPlayer = new Player("Fifth");
    }

    @Test
    public void getRoomSquaresTest() {
        List<BoardSquare> boardSquares = gameBoard.getRoomSquares(Room.RED);
        assertTrue(boardSquares.size() == 2);
        for (int i = 0; i < boardSquares.size(); i++) {
            assertEquals(boardSquares.get(i).getRoom(), Room.RED);
        }

        boardSquares = gameBoard.getRoomSquares(Room.BLUE);
        assertEquals(2, boardSquares.size());
        for (int i = 0; i < boardSquares.size(); i++) {
            assertEquals(boardSquares.get(i).getRoom(), Room.BLUE);
        }
        boardSquares = gameBoard.getRoomSquares(Room.PURPLE);
        assertEquals(2, boardSquares.size());
        for (int i = 0; i < boardSquares.size(); i++) {
            assertEquals(boardSquares.get(i).getRoom(), Room.PURPLE);
        }
        boardSquares = gameBoard.getRoomSquares(Room.YELLOW);
        assertEquals(2, boardSquares.size());
        for (int i = 0; i < boardSquares.size(); i++) {
            assertEquals(boardSquares.get(i).getRoom(), Room.YELLOW);
        }
        boardSquares = gameBoard.getRoomSquares(Room.GRAY);
        assertEquals(3, boardSquares.size());
        for (int i = 0; i < boardSquares.size(); i++) {
            assertEquals(boardSquares.get(i).getRoom(), Room.GRAY);
        }
    }

    @Test
    public void getVisibleSquaresTest() {
        BoardSquare reference = gameBoard.getBoardSquareByCoordinates(new Coordinates(2, 2));
        List<BoardSquare> aspectedVisible = new ArrayList<>();
        aspectedVisible.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(1, 2)));
        aspectedVisible.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(2, 2)));
        aspectedVisible.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(1, 1)));
        aspectedVisible.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(1, 2)));
        List<BoardSquare> visible = gameBoard.getVisibleSquares(reference, false);
        assertTrue(visible.containsAll(aspectedVisible));

        List<BoardSquare> aspectedNotVisible = new ArrayList<>();
        aspectedNotVisible.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(0, 0)));
        aspectedNotVisible.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(0, 1)));
        aspectedNotVisible.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(0, 2)));
        aspectedNotVisible.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(1, 0)));
        aspectedNotVisible.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(2, 0)));
        aspectedNotVisible.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(3, 0)));
        aspectedNotVisible.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(3, 1)));

        List<BoardSquare> notVisible = gameBoard.getVisibleSquares(reference, true);
        assertTrue(notVisible.containsAll(aspectedNotVisible));

    }

    @Test
    public void pathIsValidTest() {
        firstPlayer.setPosition(gameBoard.getBoardSquareByCoordinates(new Coordinates(1,1)));
        try {
            gameBoard.setPlayerPositions(firstPlayer, gameBoard.getBoardSquareByCoordinates(new Coordinates(1,1)));
        } catch(OutOfBoundBoardException e) {
            e.printStackTrace();
        }
        List<Coordinates> coordinatesList = new ArrayList<>();
        coordinatesList.add(new Coordinates(1,2));
        coordinatesList.add(new Coordinates(1,2));
        coordinatesList.add(new Coordinates(2,2));
        assertFalse(gameBoard.pathIsValid(firstPlayer, coordinatesList));
    }

    @Test
    public void getRoomPlayersTest() {
        List<Player> playerList = new ArrayList<>();
        playerList.add(firstPlayer);
        BoardSquare boardSquare = gameBoard.getBoardSquareByCoordinates(new Coordinates(1,1));
        firstPlayer.setPosition(boardSquare);
        try {
            gameBoard.setPlayerPositions(firstPlayer, boardSquare);
        } catch(OutOfBoundBoardException e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(firstPlayer, gameBoard.getRoomPlayers(Room.PURPLE).get(0));
    }

    @Test
    public void getVisibleRoomsTest() {
        BoardSquare boardSquare = gameBoard.getBoardSquareByCoordinates(new Coordinates(1,1));
        List<Room> roomList = gameBoard.getVisibleRooms(boardSquare, false);
        List<Room> expectedList = new ArrayList<>();
        expectedList.add(Room.BLUE);
        expectedList.add(Room.GRAY);
        assertTrue(roomList.containsAll(expectedList));
        assertTrue(expectedList.containsAll(roomList));
    }

    @Test
    public void getVisiblePlayersTest() {
        List<Player> playerList = new ArrayList<>();
        BoardSquare boardSquare = gameBoard.getBoardSquareByCoordinates(new Coordinates(1,1));
        firstPlayer.setPosition(boardSquare);
        secondPlayer.setPosition(boardSquare);
        thirdPlayer.setPosition(boardSquare);
        playerList.add(firstPlayer);
        playerList.add(secondPlayer);
        playerList.add(thirdPlayer);
        try {
            gameBoard.setPlayerPositions(firstPlayer, boardSquare);
            gameBoard.setPlayerPositions(secondPlayer, boardSquare);
            gameBoard.setPlayerPositions(thirdPlayer, boardSquare);
        } catch(OutOfBoundBoardException e) {
            e.printStackTrace();
            fail();
        }
        assertTrue(playerList.containsAll(gameBoard.getVisiblePlayers(boardSquare, false)));
    }

    @Test
    public void getSquareByDistance() {
        BoardSquare boardSquare = gameBoard.getBoardSquareByCoordinates(new Coordinates(1,1));
        List<BoardSquare> expectedList = new ArrayList<>();
        expectedList.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(1,0)));
        expectedList.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(1,2)));
        try {
            List<BoardSquare> squaresByDistance = gameBoard.getSquaresByDistance(boardSquare, 1, 1);
            assertTrue(squaresByDistance.containsAll(expectedList));

        } catch(Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void getBoardSquareByCardinalDirectionTest() {
        BoardSquare boardSquare = gameBoard.getBoardSquareByCoordinates(new Coordinates(0,0));
        List<BoardSquare> expected = new ArrayList<>();
        expected.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(0,1)));
        expected.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(0,2)));
        List<BoardSquare> boardSquaresByCardinalDirection = gameBoard.getBoardSquaresByCardinalDirection(boardSquare, CardinalDirection.NORTH);
        assertTrue(expected.containsAll(boardSquaresByCardinalDirection));
        boardSquare = gameBoard.getBoardSquareByCoordinates(new Coordinates(0,1));
        boardSquaresByCardinalDirection = gameBoard.getBoardSquaresByCardinalDirection(boardSquare, CardinalDirection.SOUTH);
        assertEquals(gameBoard.getBoardSquareByCoordinates(new Coordinates(0,0)), boardSquaresByCardinalDirection.get(0));
        assertEquals(1, boardSquaresByCardinalDirection.size());

        expected.clear();
        expected.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(1,0)));
        expected.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(2,0)));
        expected.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(3,0)));

        boardSquare = gameBoard.getBoardSquareByCoordinates(new Coordinates(0,0));
        boardSquaresByCardinalDirection = gameBoard.getBoardSquaresByCardinalDirection(boardSquare, CardinalDirection.EAST);
        assertTrue(boardSquaresByCardinalDirection.containsAll(expected));

        expected.clear();
        expected.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(1,0)));
        expected.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(2,0)));
        expected.add(gameBoard.getBoardSquareByCoordinates(new Coordinates(0,0)));
        boardSquare = gameBoard.getBoardSquareByCoordinates(new Coordinates(3,0));
        boardSquaresByCardinalDirection = gameBoard.getBoardSquaresByCardinalDirection(boardSquare, CardinalDirection.WEST);
        assertTrue(boardSquaresByCardinalDirection.containsAll(expected));

    }

    @Test
    public void getCoordinatesTest() {
        Coordinates expected = new Coordinates(1,1);
        BoardSquare square = gameBoard.getBoardSquareByCoordinates(expected);
        assertEquals(expected, gameBoard.getCoordinates(square));

        assertNotEquals(expected, gameBoard.getCoordinates(null));
    }

    @Test
    public void getPlayerPositionTets() {
        List<Player> players = new ArrayList<>();
        BoardSquare boardSquare = gameBoard.getBoardSquareByCoordinates(new Coordinates(1,1));
        firstPlayer.setPosition(boardSquare);
        secondPlayer.setPosition(boardSquare);
        thirdPlayer.setPosition(boardSquare);
        players.add(firstPlayer);
        players.add(secondPlayer);
        players.add(thirdPlayer);
        try {
            gameBoard.setPlayerPositions(firstPlayer, boardSquare);
            gameBoard.setPlayerPositions(secondPlayer, boardSquare);
            gameBoard.setPlayerPositions(thirdPlayer, boardSquare);
        } catch(OutOfBoundBoardException e) {
            e.printStackTrace();
            fail();
        }
        List<Player> playerList = gameBoard.getPlayersOnGameBoard();
        assertTrue(playerList.containsAll(players));
    }
}