package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.AbstractConnectionManager;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.AbstractConnectionManagerTest;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLI;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.ClientTest;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.AbstractClientHandlerTest;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.MatchBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Logger;



public class EventControllerTest {

    private static final Logger LOGGER = Logger.getLogger(EventControllerTest.class.getName());

    private EventController eventController;

    private CollectPlayEvent collectPlayEvent;
    private TargetSelectedEvent targetSelectedEvent;
    private MovePlayEvent movePlayEvent;

    private MatchBuilder matchBuilder;

    private AbstractClientHandlerTest abstractClientHandlerTest;

    private AbstractConnectionManager connectionManager;

    private MatchController matchController;

    private ClientTest clientTest;

    private CLI cliTest;

    @Before
    public void setUp() {
        matchBuilder = new MatchBuilder();
        abstractClientHandlerTest = new AbstractClientHandlerTest(null);
        matchController = new MatchController(matchBuilder);
        matchBuilder.signNewClient(abstractClientHandlerTest);
        clientTest = new ClientTest();
        cliTest = new CLI(clientTest);
        clientTest.setView(cliTest);
        connectionManager = new AbstractConnectionManagerTest(clientTest);


    }
    @Test
    public void visitorTest() {

        MatchController matchController = new MatchController(new MatchBuilder());

        eventController = matchController.getEventController();
        eventController.addVirtualView(abstractClientHandlerTest);

        AbstractViewEvent event = new LoginRequestEvent(0, "Tony", Avatar.SPROG);
        eventController.update(event);
        event = new LoginRequestEvent(1, "pippo", Avatar.VIOLET);
        eventController.update(event);
        try {
            Thread.sleep(6000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        /*
        event = new MapVoteEvent(0, "Tony", MapType.FOUR);
        eventController.update(event);
        event = new MapVoteEvent(1, "pippo", MapType.FOUR);
        eventController.update(event);

         */


        collectPlayEvent = new CollectPlayEvent(0, "UserA!", new ArrayList<>());
        targetSelectedEvent = new TargetSelectedEvent(1, "Mariooooo!", new ArrayList<>());
        movePlayEvent = new MovePlayEvent(0,"UserA!", new ArrayList<>());


       // eventController.update(collectPlayEvent);
        eventController.update(targetSelectedEvent);
        //eventController.update(movePlayEvent);



        System.out.println("ended");
    }
}
