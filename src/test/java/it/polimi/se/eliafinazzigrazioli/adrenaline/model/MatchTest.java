package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model.PlayerAlreadyPresentException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MatchTest {
    private Match match;

    @Before
    public void setUp() throws Exception {
        match = new Match ();
    }

    @Test
    public void constructorTest() {
        assertTrue (match.getPhase () == MatchPhase.INITIALIZATION);
        assertTrue (match.getPlayers ().size () == 0);
        assertTrue (match.getCurrentPlayer () == null);
        assertTrue (match.getFirstPlayer () == null);
        assertTrue (match.getTurn () == 0);
        //assertTrue (match.getMap () == null);
    }

    @Test
    public void addPlayersTest() {
        Player firstPlayer = new Player ("one");
        Player secondPlayer = new Player ("two");
        Player thirdPlayer = new Player ("three");
        Player fourthPlayer = new Player ("four");
        Player fifthPlayer = new Player ("five");
        try {
            match.addPlayer(firstPlayer);
        }catch (MaxPlayerException e) {
            e.printStackTrace ();
            fail ();
        }catch (PlayerAlreadyPresentException e) {
            e.printStackTrace ();
            fail ();
        }
        assertTrue (match.getCurrentPlayer () == firstPlayer);
        assertTrue (match.getFirstPlayer () == firstPlayer);
        assertTrue (match.getPlayers ().size () == 1);
        try {
            match.addPlayer(secondPlayer);
            match.addPlayer(thirdPlayer);
            match.addPlayer(fourthPlayer);
            match.addPlayer(fifthPlayer);
        }catch (MaxPlayerException e) {
            e.printStackTrace ();
            fail ();
        }catch (PlayerAlreadyPresentException e) {
            e.printStackTrace ();
            fail ();
        }
        assertTrue (match.getCurrentPlayer () == firstPlayer);
        assertTrue (match.getFirstPlayer () == firstPlayer);
        assertTrue (match.getPlayers ().size () == 5);
        assertTrue (match.getPlayers ().get (0) == firstPlayer);
        assertTrue (match.getPlayers ().get (1) == secondPlayer);
        assertTrue (match.getPlayers ().get (2) == thirdPlayer);
        assertTrue (match.getPlayers ().get (3) == fourthPlayer);
        assertTrue (match.getPlayers ().get (4) == fifthPlayer);
    }

    @Test(expected = MaxPlayerException.class)
    public void addMaxPlayerExcepionTest() throws MaxPlayerException {
        Player firstPlayer = new Player ("one");
        Player secondPlayer = new Player ("two");
        Player thirdPlayer = new Player ("three");
        Player fourthPlayer = new Player ("four");
        Player fifthPlayer = new Player ("five");
        try {
            match.addPlayer(firstPlayer);
            match.addPlayer(secondPlayer);
            match.addPlayer(thirdPlayer);
            match.addPlayer(fourthPlayer);
            match.addPlayer(fifthPlayer);
        }catch (PlayerAlreadyPresentException e) {
            e.printStackTrace ();
        }
        Player sixthPlayer = new Player ("six");
        try {
            match.addPlayer(sixthPlayer);
        }catch (PlayerAlreadyPresentException e) {
            e.printStackTrace ();
        }finally {
            assertTrue (match.getCurrentPlayer () == firstPlayer);
            assertTrue (match.getFirstPlayer () == firstPlayer);
            assertTrue (match.getPlayers ().size () == 5);
            assertTrue (match.getPlayers ().get (0) == firstPlayer);
            assertTrue (match.getPlayers ().get (1) == secondPlayer);
            assertTrue (match.getPlayers ().get (2) == thirdPlayer);
            assertTrue (match.getPlayers ().get (3) == fourthPlayer);
            assertTrue (match.getPlayers ().get (4) == fifthPlayer);
        }

    }


    @Test(expected = PlayerAlreadyPresentException.class)
    public void addPresentPlayerExceptionTest() throws PlayerAlreadyPresentException {
        Player firstPlayer = new Player ("one");
        Player secondPlayer = new Player ("two");
        Player thirdPlayer = new Player ("three");
        Player fourthPlayer = new Player ("four");
        Player fifthPlayer = new Player ("five");
        try {
            match.addPlayer(firstPlayer);
            match.addPlayer(secondPlayer);
            match.addPlayer(thirdPlayer);
            match.addPlayer(fourthPlayer);
        }catch (MaxPlayerException e) {
            e.printStackTrace ();
        }
        try {
            match.addPlayer(firstPlayer);
        }catch (MaxPlayerException e) {
            e.printStackTrace ();
        }finally {
            assertTrue (match.getCurrentPlayer () == firstPlayer);
            assertTrue (match.getFirstPlayer () == firstPlayer);
            assertTrue (match.getPlayers ().size () == 4);
            assertTrue (match.getPlayers ().get (0) == firstPlayer);
            assertTrue (match.getPlayers ().get (1) == secondPlayer);
            assertTrue (match.getPlayers ().get (2) == thirdPlayer);
            assertTrue (match.getPlayers ().get (3) == fourthPlayer);
        }
    }

    @Test
    public void nextCurrentPlayerTest() {
        Player firstPlayer = new Player ("one");
        Player secondPlayer = new Player ("two");
        Player thirdPlayer = new Player ("three");
        Player fourthPlayer = new Player ("four");
        Player fifthPlayer = new Player ("five");
        try {
            match.addPlayer(firstPlayer);
            match.addPlayer(secondPlayer);
            match.addPlayer(thirdPlayer);
            match.addPlayer(fourthPlayer);
            match.addPlayer(fifthPlayer);
        }catch (MaxPlayerException e) {
            e.printStackTrace ();
            fail ();
        }catch (PlayerAlreadyPresentException e) {
            e.printStackTrace ();
            fail ();
        }

        assertTrue (match.getCurrentPlayer () == firstPlayer);
        match.nextCurrentPlayer ();
        assertTrue (match.getCurrentPlayer () == secondPlayer);
        match.nextCurrentPlayer ();
        assertTrue (match.getCurrentPlayer () == thirdPlayer);
        match.nextCurrentPlayer ();
        assertTrue (match.getCurrentPlayer () == fourthPlayer);
        match.nextCurrentPlayer ();
        assertTrue (match.getCurrentPlayer () == fifthPlayer);
        match.nextCurrentPlayer ();
        assertTrue (match.getCurrentPlayer () == firstPlayer);
        match.nextCurrentPlayer ();
        assertTrue (match.getCurrentPlayer () == secondPlayer);
        match.nextCurrentPlayer ();
        assertTrue (match.getCurrentPlayer () == thirdPlayer);
        match.nextCurrentPlayer ();
        assertTrue (match.getCurrentPlayer () == fourthPlayer);
        match.nextCurrentPlayer ();
        assertTrue (match.getCurrentPlayer () == fifthPlayer);
    }

    @Test
    public void setPhaseTest() {
        match.setPhase (MatchPhase.RECRUITING);
        assertTrue (match.getPhase () == MatchPhase.RECRUITING);
    }

    @Test
    public void increaseTurnTest() {
        int oldTurn = match.getTurn ();
        for (int i=0; i<100; i++) {
            match.increaseTurn ();
            assertEquals (oldTurn+1, match.getTurn ());
            oldTurn = match.getTurn ();
        }
    }
}

