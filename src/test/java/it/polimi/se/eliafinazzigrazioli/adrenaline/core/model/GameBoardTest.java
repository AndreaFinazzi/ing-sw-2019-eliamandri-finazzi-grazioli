package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameBoardTest {
    private GameBoard gameBoard;

    @Before
    public void setUp() {
        gameBoard = new GameBoard(MapType.ONE);
    }

    /*
        @Test
        public void getBoardSquareByCoordinatesTest() {
            BoardSquare boardSquare = gameBoard.getBoardSquareByCoordinates (new Coordinates (0,0));
            assertTrue (boardSquare instanceof GenericBoardSquare);
            assertEquals (Room.RED, boardSquare.getRoom ());
            assertEquals (boardSquare.getCoordinates (), new Coordinates (0,0));
            assertEquals (InterSquareLink.WALL, boardSquare.getNorth ());
            assertEquals (InterSquareLink.SAMEROOM, boardSquare.getSouth ());
            assertEquals (InterSquareLink.DOOR, boardSquare.getEast ());
            assertEquals (InterSquareLink.WALL, boardSquare.getWest ());
        }
    */
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
}