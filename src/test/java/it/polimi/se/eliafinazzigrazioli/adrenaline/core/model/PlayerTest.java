package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;


import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType.ONE;
import static org.junit.Assert.*;

/**
 * The type Player test.
 */
public class PlayerTest {
    private Player player;
    private PlayerBoard playerBoard;
    private Match match;
    private GameBoard gameBoard;

    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        player = new Player("nickname");
        playerBoard = new PlayerBoard();
        match = new Match();
        gameBoard = new GameBoard(ONE);
        match.addPlayer(new Player("second"));
        match.addPlayer(new Player("third"));
        match.addPlayer(new Player("fourth"));
        match.addPlayer(new Player("fifth"));
    }

    /**
     * Constructor test.
     */
    @Test
    public void constructorTest() {
        assertTrue(player.getPlayerNickname().equals("nickname"));
        assertTrue(player.getPosition() == null);
        assertTrue(player.getPowerUps().size() == 0);
        assertTrue(player.getWeapons().size() == 0);
        assertTrue(player.getPlayerBoard() == null);
        assertFalse(player.isConnected());
        assertFalse(player.isPlaced());
        assertFalse(player.isSuspended());
    }

    /**
     * Sets position test.
     */
    @Test
    public void setPositionTest() {
        BoardSquare position = gameBoard.
                getBoardSquareByCoordinates(new Coordinates(1, 1));
        player.setPosition(position);
        assertEquals(position, player.getPosition());

    }

    /**
     * Sets player board test.
     */
    @Test
    public void setPlayerBoardTest() {
        PlayerBoard playerBoard1 = new PlayerBoard();
        player.setPlayerBoard(playerBoard);
        assertEquals(playerBoard, player.getPlayerBoard());
        player.setPlayerBoard(playerBoard1);
        assertEquals(playerBoard1, player.getPlayerBoard());
    }


}