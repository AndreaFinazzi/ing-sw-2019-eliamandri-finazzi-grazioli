package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.GenericEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public abstract class AbstractClientHandler implements Runnable {
    protected transient Server server;
    protected static final Logger LOGGER = Logger.getLogger(AbstractClientHandler.class.getName());
    protected AbstractViewEvent nextReceivedEvent;

    protected BlockingQueue<AbstractViewEvent> eventsQueue;

    public AbstractClientHandler(Server server) {
        this.server = server;
    }

    public abstract void sendToAll(AbstractModelEvent event);

    public abstract void sendTo(int clientID, AbstractModelEvent event);

    // register starting match
    public void setEventsQueue(BlockingQueue<AbstractViewEvent> eventsQueue) {
        this.eventsQueue = eventsQueue;
//        eventController.addModelEventsListener(this);
    }

//    protected void signPlayerToNextMatch(AbstractClientHandler clientHandler) {
//        server.addClient(clientHandler);
//    }

    protected void received(AbstractViewEvent event) {
        if (eventsQueue == null) {
            LOGGER.info("Trying to directly update eventController");
        } else if (!eventsQueue.offer(event)) {
            //TODO specific event type needed?
            sendTo(event.getClientID(), new GenericEvent(event.getPlayer(), "Events generation failed."));
        }
    }

    private void setup() {

    }

    //TODO: this should do something useful
    //TODO: add message constants to dedicated class
    @Override
    public void run() {
        LOGGER.info("ClientHandler initialized");
        setup();
    }
}