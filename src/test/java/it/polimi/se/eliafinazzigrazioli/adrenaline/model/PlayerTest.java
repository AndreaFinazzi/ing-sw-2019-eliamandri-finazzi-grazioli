package it.polimi.se.eliafinazzigrazioli.adrenaline.model;


import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Coordinates;
import org.junit.Before;
import org.junit.Test;

import static it.polimi.se.eliafinazzigrazioli.adrenaline.model.MapType.ONE;
import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;
    private PlayerBoard playerBoard;
    private Match match;
    private GameBoard gameBoard;

    @Before
    public void setUp() throws Exception {
        player = new Player ("nickname");
        playerBoard = new PlayerBoard ();
        match = new Match ();
        gameBoard = new GameBoard (ONE, match);
        match.addPlayer(new Player("second"));
        match.addPlayer(new Player("third"));
        match.addPlayer(new Player("fourth"));
        match.addPlayer(new Player("fifth"));
    }

    @Test
    public void constructorTest() {
        assertTrue (player.getPlayerNickname ().equals ("nickname"));
        assertTrue (player.getPosition () == null);
        assertTrue (player.getPowerUps ().size () == 0);
        assertTrue (player.getWeapons ().size () == 0);
        assertTrue (player.getMatch () == null);
        assertTrue (player.getPlayerBoard () == null);
        assertFalse (player.isConnected ());
        assertFalse (player.isPlaced ());
        assertFalse (player.isSuspended ());
    }

    @Test(expected = Exception.class)
    public void setMatchTest() throws Exception{
        player.setMatch (match);
        assertEquals (match, player.getMatch ());
        player.setMatch (new Match ());
    }

    @Test
    public void setPositionTest() {
        BoardSquare position = gameBoard.
                getBoardSquareByCoordinates (new Coordinates (1,1));
        player.setPosition (position);
        assertEquals (position, player.getPosition ());

    }

    @Test
    public void setPlayerBoardTest() {
        PlayerBoard playerBoard1 = new PlayerBoard ();
        player.setPlayerBoard (playerBoard);
        assertEquals (playerBoard, player.getPlayerBoard ());
        player.setPlayerBoard (playerBoard1);
        assertEquals (playerBoard1, player.getPlayerBoard ());
    }



}