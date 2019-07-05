package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller.MatchController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ServerEventsConsumer picks events on the queue and notifies them to the right EventController instance.
 * It permits to avoid threads locking on update calls.
 * <p>
 * The consumer keeps updating until the specific match is set to ended. In case of error and at the end of its life, It calls the math closing method on the controller.
 */
public class ServerEventsConsumer implements Runnable {

    /**
     * Static reference to LOGGER
     */
    private static final Logger LOGGER = Logger.getLogger(ServerEventsConsumer.class.getName());

    /**
     * Incoming events queue reference
     */
    private BlockingQueue<AbstractViewEvent> eventsQueue;

    /**
     * MatchController reference
     */
    private MatchController matchController;

    /**
     * Instantiates a new Server events consumer.
     *
     * @param eventsQueue the events queue storing incoming events
     * @param matchController the match controller which should receive the incoming events
     */
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
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            matchController.closeMatch();
            Thread.currentThread().interrupt();
        } finally {
            matchController.closeMatch();
            Thread.currentThread().interrupt();
        }
    }
}
