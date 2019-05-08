package it.polimi.se.eliafinazzigrazioli.adrenaline.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.CollectPlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.MovePlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.TargetSelectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.ListenerNotFoundException;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;


public class EventControllerTest {

    private static final Logger LOGGER = Logger.getLogger(EventControllerTest.class.getName());

    private EventController eventController;

    private CollectPlayEvent collectPlayEvent;
    private TargetSelectedEvent targetSelectedEvent;
    private MovePlayEvent movePlayEvent;

    @Test
    public void testCasting() {

        MatchController matchController = new MatchController();

        eventController = matchController.getEventController();

        collectPlayEvent = new CollectPlayEvent("UserA!");
        targetSelectedEvent = new TargetSelectedEvent("Mariooooo!", null);
        movePlayEvent = new MovePlayEvent(null, null);

        eventController.update(collectPlayEvent);
        eventController.update(targetSelectedEvent);
        eventController.update(movePlayEvent);

        try {
            eventController.removeEventListener(TargetSelectedEvent.class, matchController);
        } catch (ListenerNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        eventController.update(targetSelectedEvent);
        System.out.println("ended");
    }
}
