package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller.MatchController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class ServerEventsConsumer implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ServerEventsConsumer.class.getName());

    private BlockingQueue<AbstractViewEvent> eventsQueue;
    private MatchController matchController;

    public ServerEventsConsumer(BlockingQueue<AbstractViewEvent> eventsQueue, MatchController matchController) {
        this.eventsQueue = eventsQueue;
        this.matchController = matchController;

    }

    @Override
    public void run() {
        AbstractViewEvent nextEvent;
        try {
            while (!matchController.getMatch().isEnded()) {
                nextEvent = eventsQueue.take();
                matchController.getEventController().update(nextEvent);
            }
        } catch (InterruptedException e) {
            //TODO handle
        } finally {

        }

        LOGGER.severe("EVENTS QUEUE INTERRUPTED FOREVER!!!");
    }
}
