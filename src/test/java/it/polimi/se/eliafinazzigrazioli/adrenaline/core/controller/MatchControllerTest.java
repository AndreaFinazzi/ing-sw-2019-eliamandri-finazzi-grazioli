package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.AvatarNotAvailableException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Match;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.MatchBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MatchControllerTest {
    private MatchController matchController;
    private Match match;


    @Before
    public void setUp() throws Exception {
        matchController = new MatchController(new MatchBuilder());
        match = matchController.getMatch();
    }

    @Test(expected = AvatarNotAvailableException.class)
    public void addPlayerTest() throws PlayerAlreadyPresentException, MaxPlayerException, AvatarNotAvailableException {
        matchController.addPlayer("playerOne", null);
        List<Player> playerList = match.getPlayers();
        assertTrue(playerList.contains(new Player("playerOne")));
    }

    @Test(expected = AvatarNotAvailableException.class)
    public void removePlayerTest() throws PlayerAlreadyPresentException, MaxPlayerException, AvatarNotAvailableException {
        matchController.addPlayer("playerOne", null);
        matchController.addPlayer("playerTwo", null);
        matchController.addPlayer("playerThree", null);
        matchController.addPlayer("playerFour", null);
        matchController.addPlayer("playerFive", null);

        List<Player> playerList = match.getPlayers();
        assertEquals(5, playerList.size());
        matchController.removePlayer("playerOne");
        playerList = match.getPlayers();
        assertEquals(4, playerList.size());
        assertFalse(playerList.contains(new Player("playerOne")));

    }

    @Test(expected = AvatarNotAvailableException.class)
    public void isReadyTest() throws PlayerAlreadyPresentException, MaxPlayerException, AvatarNotAvailableException {
        matchController.addPlayer("playerOne", null);
        assertFalse(matchController.isReady());
        matchController.addPlayer("playerTwo", null);
        assertFalse(matchController.isReady());
        matchController.addPlayer("playerThree", null);
        assertTrue(matchController.isReady());
        matchController.addPlayer("playerFour", null);
        assertTrue(matchController.isReady());
        matchController.addPlayer("playerFive", null);
        assertTrue(matchController.isReady());
    }

    @Test(expected = AvatarNotAvailableException.class)
    public void isFullTest() throws PlayerAlreadyPresentException, MaxPlayerException, AvatarNotAvailableException {
        matchController.addPlayer("playerOne", null);
        assertFalse(matchController.isFull());
        matchController.addPlayer("playerTwo", null);
        assertFalse(matchController.isFull());
        matchController.addPlayer("playerThree", null);
        assertFalse(matchController.isFull());
        matchController.addPlayer("playerFour", null);
        assertFalse(matchController.isFull());
        matchController.addPlayer("playerFive", null);
        assertTrue(matchController.isFull());
    }

    @Test(expected = MaxPlayerException.class)
    public void addPlayerMaxTest() throws PlayerAlreadyPresentException, MaxPlayerException, AvatarNotAvailableException {

        matchController.addPlayer("playerOne", Avatar.BANSHEE);
        matchController.addPlayer("playerTwo", Avatar.DESTRUCTOR);
        matchController.addPlayer("playerThree", Avatar.DOZER);
        matchController.addPlayer("playerFour", Avatar.SPROG);
        matchController.addPlayer("playerFive", Avatar.VIOLET);
        List<Player> playerList = match.getPlayers();
        assertEquals(5, playerList.size());

        try {
            matchController.addPlayer("playerSix", Avatar.VIOLET);
        } catch (AvatarNotAvailableException e) {

        }

        playerList = match.getPlayers();
        assertEquals(5, playerList.size());
        assertFalse(playerList.contains(new Player("playerSix")));
    }

    @Test(expected = PlayerAlreadyPresentException.class)
    public void addPlayerAlreadyPresentTest() throws PlayerAlreadyPresentException, MaxPlayerException, AvatarNotAvailableException {
        matchController.addPlayer("playerOne", Avatar.VIOLET);
        matchController.addPlayer("playerOne", Avatar.SPROG);

        List<Player> playerList = match.getPlayers();
        assertEquals(1, playerList.size());
        assertTrue(playerList.contains(new Player("playerOne")));

    }
}