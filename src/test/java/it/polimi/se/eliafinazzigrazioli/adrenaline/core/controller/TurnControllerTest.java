package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.PowerUpsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.MatchBuilder;
import org.junit.Before;
import org.junit.Test;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TurnControllerTest {

    private MatchBuilder matchBuilder;
    private MatchController matchController;
    private Match match;
    private EventController eventController;
    private Player first;
    private Player second;
    private Player third;
    private BoardSquare boardSquare;
    private TurnController turnController;
    private Player fourth;
    private Player fifth;


    @Before
    public void setUp() throws Exception {
        matchBuilder = new MatchBuilder();
        matchController = new MatchController(matchBuilder);
        match = matchController.getMatch();
        match.setGameBoard(MapType.FOUR);
        boardSquare = match.getGameBoard().getBoardSquareByCoordinates(new Coordinates(1,1));
        eventController = new EventController(matchController);
        first = new Player(0, "tony");
        match.getGameBoard().setPlayerPositions(first, boardSquare);
        first.setPosition(boardSquare);
        match.addPlayer(first);
        second = new Player(1, "Peppe");
        match.getGameBoard().setPlayerPositions(second, boardSquare);
        match.addPlayer(second);
        second.setPosition(boardSquare);

        third = new Player(2, "Finazzz");
        match.getGameBoard().setPlayerPositions(third, boardSquare);
        match.addPlayer(third);
        third.setPosition(boardSquare);
        match.getGameBoard().setPlayerPositions(third, boardSquare);
        fourth = new Player(3, "graz");
        match.addPlayer(fourth);
        fourth.setPosition(boardSquare);
        turnController = new TurnController(eventController, match);
    }


    @Test
    public void movePlayEvent() {
        List<Coordinates> path = new ArrayList<>();
        path.add(new Coordinates(1,2));
        path.add(new Coordinates(2,2));
        MovePlayEvent movePlayEvent = new MovePlayEvent(0, "tony", path);
        try {
            turnController.handleEvent(movePlayEvent);
        } catch(HandlerNotImplementedException e) {
            e.printStackTrace();
        }
        assertEquals(new Coordinates(2,2), match.getGameBoard().getPlayerPosition(first).getCoordinates());
    }

    @Test
    public void collectPlayEvent() {
        List<Coordinates> path = new ArrayList<>();
        path.add(new Coordinates(1,2));
        CollectPlayEvent collectPlayEvent = new CollectPlayEvent(0, "tony", path);
        turnController.handleEvent(collectPlayEvent);
        assertNull(match.getGameBoard().getAmmoCardsOnMap().get(new Coordinates(1,2)));
        assertEquals(new Coordinates(1,2), match.getGameBoard().getPlayerPosition(first).getCoordinates());
    }

    @Test
    public void collectWeaponEventTest() {
        match.getGameBoard().weaponCardsSetup(new WeaponsDeck());
        List<Coordinates> path = new ArrayList<>();
        path.add(new Coordinates(1,2));
        List<WeaponCardClient> weaponCards = new ArrayList<>();
        weaponCards = match.getGameBoard().getWeaponCardsOnMap().get(new Coordinates(2,2));

        try {
            PowerUpsDeck powerUpsDeck = new PowerUpsDeck();
            List<PowerUpCardClient> powerUpCardClients = new ArrayList<>();
            for(int i = 0; i < 3; i++) {
                powerUpCardClients.add(new PowerUpCardClient(powerUpsDeck.drawCard()));
            }
            WeaponCollectionEvent collectPlayEvent = new WeaponCollectionEvent(0, "tony", path, weaponCards.get(0), null, powerUpCardClients);
            turnController.handleEvent(collectPlayEvent);
        } catch(URISyntaxException e) {
            e.printStackTrace();
        } catch(HandlerNotImplementedException e) {
            e.printStackTrace();
        }
        assertFalse(match.getGameBoard().getWeaponCardsOnMap().get(new Coordinates(2,2)).contains(weaponCards.get(0)));
        assertEquals(new Coordinates(1,2), match.getGameBoard().getPlayerPosition(first).getCoordinates());
    }

    @Test
    public void spawnPowerUpSelectedEventTest() {
        try {
            PowerUpsDeck powerUpsDeck = new PowerUpsDeck();
            PowerUpCardClient toKeep = new PowerUpCardClient(powerUpsDeck.drawCard());
            PowerUpCardClient spawnCard = new PowerUpCardClient(powerUpsDeck.drawCard());
            SpawnPowerUpSelectedEvent spawnPu = new SpawnPowerUpSelectedEvent(3, "graz", toKeep, spawnCard, true);
            try {
                turnController.handleEvent(spawnPu);
            } catch(HandlerNotImplementedException e) {
                e.printStackTrace();
            }
        } catch(URISyntaxException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void EffectSelectedEventTest() {
        EffectSelectedEvent event = null;
        boolean result = true;
        try {
            event = new EffectSelectedEvent(0, "tony", null, null);
            turnController.handleEvent(event);
        } catch(Exception e) {
            result = false;
        }
        assertTrue(result);

    }
}