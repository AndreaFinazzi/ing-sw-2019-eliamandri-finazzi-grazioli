package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.TestSupportClasses;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.LocalModel;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MatchTest {
    private Match match;

    @Before
    public void setUp() throws Exception {
        match = new Match();
    }

    @Test
    public void constructorTest() {
        assertTrue(match.getPhase() == MatchPhase.INITIALIZATION);
        assertTrue(match.getPlayers().size() == 0);
        assertTrue(match.getCurrentPlayer() == null);
        assertTrue(match.getFirstPlayer() == null);
        assertTrue(match.getTurn() == 0);
        //assertTrue (match.getGameBoard () == null);
    }

    @Test
    public void addPlayersTest() {
        Player firstPlayer = new Player("one");
        Player secondPlayer = new Player("two");
        Player thirdPlayer = new Player("three");
        Player fourthPlayer = new Player("four");
        Player fifthPlayer = new Player("five");
        try {
            match.addPlayer(firstPlayer);
        } catch (MaxPlayerException e) {
            e.printStackTrace();
            fail();
        } catch (PlayerAlreadyPresentException e) {
            e.printStackTrace();
            fail();
        }
        assertTrue(match.getCurrentPlayer() == firstPlayer);
        assertTrue(match.getFirstPlayer() == firstPlayer);
        assertTrue(match.getPlayers().size() == 1);
        try {
            match.addPlayer(secondPlayer);
            match.addPlayer(thirdPlayer);
            match.addPlayer(fourthPlayer);
            match.addPlayer(fifthPlayer);
        } catch (MaxPlayerException e) {
            e.printStackTrace();
            fail();
        } catch (PlayerAlreadyPresentException e) {
            e.printStackTrace();
            fail();
        }
        assertTrue(match.getCurrentPlayer() == firstPlayer);
        assertTrue(match.getFirstPlayer() == firstPlayer);
        assertTrue(match.getPlayers().size() == 5);
        assertTrue(match.getPlayers().get(0) == firstPlayer);
        assertTrue(match.getPlayers().get(1) == secondPlayer);
        assertTrue(match.getPlayers().get(2) == thirdPlayer);
        assertTrue(match.getPlayers().get(3) == fourthPlayer);
        assertTrue(match.getPlayers().get(4) == fifthPlayer);
    }

    @Test(expected = MaxPlayerException.class)
    public void addMaxPlayerExcepionTest() throws MaxPlayerException {
        Player firstPlayer = new Player("one");
        Player secondPlayer = new Player("two");
        Player thirdPlayer = new Player("three");
        Player fourthPlayer = new Player("four");
        Player fifthPlayer = new Player("five");
        try {
            match.addPlayer(firstPlayer);
            match.addPlayer(secondPlayer);
            match.addPlayer(thirdPlayer);
            match.addPlayer(fourthPlayer);
            match.addPlayer(fifthPlayer);
        } catch (PlayerAlreadyPresentException e) {
            e.printStackTrace();
        }
        Player sixthPlayer = new Player("six");
        try {
            match.addPlayer(sixthPlayer);
        } catch (PlayerAlreadyPresentException e) {
            e.printStackTrace();
        } finally {
            assertTrue(match.getCurrentPlayer() == firstPlayer);
            assertTrue(match.getFirstPlayer() == firstPlayer);
            assertTrue(match.getPlayers().size() == 5);
            assertTrue(match.getPlayers().get(0) == firstPlayer);
            assertTrue(match.getPlayers().get(1) == secondPlayer);
            assertTrue(match.getPlayers().get(2) == thirdPlayer);
            assertTrue(match.getPlayers().get(3) == fourthPlayer);
            assertTrue(match.getPlayers().get(4) == fifthPlayer);
        }

    }


    @Test(expected = PlayerAlreadyPresentException.class)
    public void addPresentPlayerExceptionTest() throws PlayerAlreadyPresentException {
        Player firstPlayer = new Player("one");
        Player secondPlayer = new Player("two");
        Player thirdPlayer = new Player("three");
        Player fourthPlayer = new Player("four");
        Player fifthPlayer = new Player("five");
        try {
            match.addPlayer(firstPlayer);
            match.addPlayer(secondPlayer);
            match.addPlayer(thirdPlayer);
            match.addPlayer(fourthPlayer);
        } catch (MaxPlayerException e) {
            e.printStackTrace();
        }
        try {
            match.addPlayer(firstPlayer);
        } catch (MaxPlayerException e) {
            e.printStackTrace();
        } finally {
            assertTrue(match.getCurrentPlayer() == firstPlayer);
            assertTrue(match.getFirstPlayer() == firstPlayer);
            assertTrue(match.getPlayers().size() == 4);
            assertTrue(match.getPlayers().get(0) == firstPlayer);
            assertTrue(match.getPlayers().get(1) == secondPlayer);
            assertTrue(match.getPlayers().get(2) == thirdPlayer);
            assertTrue(match.getPlayers().get(3) == fourthPlayer);
        }
    }

    @Test
    public void nextCurrentPlayerTest() {
        Player firstPlayer = new Player("one");
        Player secondPlayer = new Player("two");
        Player thirdPlayer = new Player("three");
        Player fourthPlayer = new Player("four");
        Player fifthPlayer = new Player("five");
        try {
            match.addPlayer(firstPlayer);
            match.addPlayer(secondPlayer);
            match.addPlayer(thirdPlayer);
            match.addPlayer(fourthPlayer);
            match.addPlayer(fifthPlayer);
        } catch (MaxPlayerException e) {
            e.printStackTrace();
            fail();
        } catch (PlayerAlreadyPresentException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(match.getCurrentPlayer() == firstPlayer);
        match.nextCurrentPlayer();
        assertTrue(match.getCurrentPlayer() == secondPlayer);
        match.nextCurrentPlayer();
        assertTrue(match.getCurrentPlayer() == thirdPlayer);
        match.nextCurrentPlayer();
        assertTrue(match.getCurrentPlayer() == fourthPlayer);
        match.nextCurrentPlayer();
        assertTrue(match.getCurrentPlayer() == fifthPlayer);
        match.nextCurrentPlayer();
        assertTrue(match.getCurrentPlayer() == firstPlayer);
        match.nextCurrentPlayer();
        assertTrue(match.getCurrentPlayer() == secondPlayer);
        match.nextCurrentPlayer();
        assertTrue(match.getCurrentPlayer() == thirdPlayer);
        match.nextCurrentPlayer();
        assertTrue(match.getCurrentPlayer() == fourthPlayer);
        match.nextCurrentPlayer();
        assertTrue(match.getCurrentPlayer() == fifthPlayer);
    }

    @Test
    public void setPhaseTest() {
        match.setPhase(MatchPhase.RECRUITING);
        assertTrue(match.getPhase() == MatchPhase.RECRUITING);
    }

    @Test
    public void increaseTurnTest() {
        Player firstPlayer = new Player("one");
        Player secondPlayer = new Player("two");
        Player thirdPlayer = new Player("three");
        Player fourthPlayer = new Player("four");
        Player fifthPlayer = new Player("five");
        try {
            match.addPlayer(firstPlayer);
            match.addPlayer(secondPlayer);
            match.addPlayer(thirdPlayer);
            match.addPlayer(fourthPlayer);
            match.addPlayer(fifthPlayer);
        } catch (MaxPlayerException e) {
            e.printStackTrace();
            fail();
        } catch (PlayerAlreadyPresentException e) {
            e.printStackTrace();
            fail();
        }
        int oldTurn = match.getTurn();
        for (int i = 0; i < 100; i++) {
            match.nextTurn();
            assertEquals(oldTurn + 1, match.getTurn());
            oldTurn = match.getTurn();
        }
    }

    /**
     *
     */
    @Test
    public void matchInstantiationTest() {
        TestSupportClasses.instanceMatch(MapType.ONE, 3);
        Match match = TestSupportClasses.match;
        GameBoard gameBoard = match.getGameBoard();
        assertEquals(3, match.getPlayers().size());

        gameBoard.spawnPlayer(match.getPlayer("SGrez"), new PowerUpCard(null, Ammo.BLUE), true);
        assertEquals(new Coordinates(2, 2), gameBoard.getPlayerPosition(match.getPlayer("SGrez")).getCoordinates());

        gameBoard.spawnPlayer(match.getPlayer("ToniIlBello"), new PowerUpCard(null, Ammo.RED), true);
        assertEquals(new Coordinates(0, 1), gameBoard.getPlayerPosition(match.getPlayer("ToniIlBello")).getCoordinates());


        gameBoard.spawnPlayer(match.getPlayer("FinazIlDuro-X("), new PowerUpCard(null, Ammo.YELLOW), true);
        assertEquals(new Coordinates(3, 0), gameBoard.getPlayerPosition(match.getPlayer("FinazIlDuro-X(")).getCoordinates());
    }

    @Test
    public void generateClientModelTest() {
        match.setGameBoard(MapType.FOUR);
        Player player = new Player(0,"Tony");
        player.addAmmo(Ammo.YELLOW);
        player.setPosition(match.getGameBoard().getBoardSquareByCoordinates(new Coordinates(1,1)));
        LocalModel localModel = match.generateClientModel(player);
        assertEquals(1, localModel.getAmmos().size());
        assertEquals(Ammo.YELLOW, localModel.getAmmos().get(0));
        assertNotNull(localModel);

    }
}

