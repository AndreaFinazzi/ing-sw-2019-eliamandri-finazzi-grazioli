package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller.CardController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller.EventController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller.MatchController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller.PlayerController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.EffectSelectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.WeaponToUseSelectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.WeaponFileNotFoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.AbstractClientHandlerTest;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.MatchBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;


public class WeaponCardTest {

    private WeaponsDeck weaponsDeck;
    private MatchBuilder matchBuilder;
    private MatchController matchController;
    private EventController eventController;
    private CardController cardController;
    private PlayerController playerController;
    private Match match;
    private Player first;


    @Before
    public void setUp() throws Exception {
        weaponsDeck = new WeaponsDeck();
        matchBuilder = new MatchBuilder();
        matchController = new MatchController(matchBuilder);
        eventController = new EventController(matchController);
        matchBuilder.signNewClient(new AbstractClientHandlerTest(null));
        matchBuilder.signNewClient(new AbstractClientHandlerTest(null));
        matchBuilder.signNewClient(new AbstractClientHandlerTest(null));
        playerController = new PlayerController(eventController, matchController);
        match = new Match();
        cardController = new CardController(eventController, match);
        match.setGameBoard(MapType.FOUR);
        BoardSquare boardSquare = match.getGameBoard().getBoardSquareByCoordinates(new Coordinates(1,1));
        first = new Player(0, "tony");
        match.addPlayer(first);
        first.setPosition(boardSquare);
        match.getGameBoard().setPlayerPositions(first, boardSquare);
        Player second = new Player(1, "finaz");
        second.setPosition(boardSquare);
        match.addPlayer(second);
        match.getGameBoard().setPlayerPositions(second, boardSquare);
        Player third = new Player(2, "graz");
        third.setPosition(boardSquare);
        match.addPlayer(third);
        match.getGameBoard().setPlayerPositions(third, boardSquare);
    }

    @Test
    public void shootTest() {
        try {
            WeaponCard weaponCard = WeaponCard.jsonParser("Furnace.json");
            try {
                first.addWeapon(weaponCard);
                weaponCard.setActiveEffect(weaponCard.getCallableEffects().get(0));
            } catch(Exception e) {
                e.printStackTrace();
            }
            weaponCard.initialize();
            weaponCard.setLoaded(true);
            weaponCard.setActiveEffect(weaponCard.getCallableEffects().get(0));
            WeaponToUseSelectedEvent event = new WeaponToUseSelectedEvent(0, "tony", weaponCard.getWeaponName(), new ArrayList<>());
            EffectSelectedEvent selectedEvent = new EffectSelectedEvent(0, "tony", weaponCard.getCallableEffects().get(0), new ArrayList<>());
                try {
                cardController.handleEvent(event);
                cardController.handleEvent(selectedEvent);

                weaponCard.executeStep(match.getGameBoard(), first);

            } catch(HandlerNotImplementedException e) {
                e.printStackTrace();
            }
        } catch(WeaponFileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDescriptionTest() {
        try {
            WeaponCard weaponCard = WeaponCard.jsonParser("Furnace.json");
            Map<String, String> effectsDescription = weaponCard.getEffectsDescription();
            assertEquals(2, effectsDescription.size());
        } catch(WeaponFileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }
}
