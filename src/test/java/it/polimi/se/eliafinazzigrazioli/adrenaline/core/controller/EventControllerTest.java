package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.CollectPlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.MovePlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.MatchBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.logging.Logger;


public class EventControllerTest {

    private static final Logger LOGGER = Logger.getLogger(EventControllerTest.class.getName());

    private EventController eventController;

    private CollectPlayEvent collectPlayEvent;
    private MovePlayEvent movePlayEvent;

    @Test
    public void visitorTest() {

        MatchController matchController = new MatchController(new MatchBuilder());

        eventController = matchController.getEventController();

        collectPlayEvent = new CollectPlayEvent(0, "UserA!", new ArrayList<>());


        System.out.println("ended");
    }
}
