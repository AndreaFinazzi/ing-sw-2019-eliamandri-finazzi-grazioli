package it.polimi.se.eliafinazzigrazioli.adrenaline.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Match;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MatchControllerTest {
    private MatchController matchController;
    private Match match;


    @Before
    public void setUp() throws Exception {
        matchController = new MatchController ();
        match = matchController.getMatch ();
    }

    @Test
    public void addPlayerTest() throws PlayerAlreadyPresentException, MaxPlayerException {
        matchController.addPlayer ("playerOne");
        List<Player> playerList = match.getPlayers ();
        assertTrue (playerList.contains (new Player ("playerOne")));
    }

    @Test
    public void removePlayerTest() throws PlayerAlreadyPresentException, MaxPlayerException{
        matchController.addPlayer ("playerOne");
        matchController.addPlayer ("playerTwo");
        matchController.addPlayer ("playerThree");
        matchController.addPlayer ("playerFour");
        matchController.addPlayer ("playerFive");

        List<Player> playerList = match.getPlayers ();
        assertEquals (5, playerList.size ());
        matchController.removePlayer ("playerOne");
        playerList = match.getPlayers ();
        assertEquals (4, playerList.size ());
        assertFalse (playerList.contains (new Player ("playerOne")));

    }

    @Test
    public void isReadyTest() throws PlayerAlreadyPresentException, MaxPlayerException{
        matchController.addPlayer ("playerOne");
        assertFalse (matchController.isReady ());
        matchController.addPlayer ("playerTwo");
        assertFalse (matchController.isReady ());
        matchController.addPlayer ("playerThree");
        assertTrue (matchController.isReady ());
        matchController.addPlayer ("playerFour");
        assertTrue (matchController.isReady ());
        matchController.addPlayer ("playerFive");
        assertTrue (matchController.isReady ());
    }

    @Test
    public void isFullTest() throws PlayerAlreadyPresentException, MaxPlayerException{
        matchController.addPlayer ("playerOne");
        assertFalse (matchController.isFull ());
        matchController.addPlayer ("playerTwo");
        assertFalse (matchController.isFull ());;
        matchController.addPlayer ("playerThree");
        assertFalse (matchController.isFull ());
        matchController.addPlayer ("playerFour");
        assertFalse (matchController.isFull ());
        matchController.addPlayer ("playerFive");
        assertTrue (matchController.isFull ());
    }

    @Test
    public void addPlayerMaxTest() throws PlayerAlreadyPresentException, MaxPlayerException{
        matchController.addPlayer ("playerOne");
        matchController.addPlayer ("playerTwo");
        matchController.addPlayer ("playerThree");
        matchController.addPlayer ("playerFour");
        matchController.addPlayer ("playerFive");
        matchController.addPlayer ("playerSix");
        List<Player> playerList = match.getPlayers ();
        assertEquals (5, playerList.size ());
        assertFalse (playerList.contains (new Player ("playerSix")));
    }

    @Test
    public void addPlayerAlreadyPresentTest() throws PlayerAlreadyPresentException, MaxPlayerException{
        matchController.addPlayer ("playerOne");
        matchController.addPlayer ("playerOne");
        List<Player> playerList = match.getPlayers ();
        assertEquals (1, playerList.size ());
        assertTrue (playerList.contains (new Player ("playerOne")));

    }
}