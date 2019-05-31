package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.GenericEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public abstract class AbstractClientHandler implements Runnable {
    protected Server server;
    protected static final Logger LOGGER = Logger.getLogger(ClientHandlerSocket.class.getName());
    protected AbstractViewEvent nextReceivedEvent;

    private BlockingQueue<AbstractViewEvent> eventsQueue;

    public abstract void send(AbstractModelEvent event);

    public abstract void send(int clientID, AbstractModelEvent event);

    public abstract void send(String player, AbstractModelEvent event);

    // register starting match
    public void setEventsQueue(BlockingQueue<AbstractViewEvent> eventsQueue) {
        this.eventsQueue = eventsQueue;
//        eventController.addModelEventsListener(this);
    }

    protected void signPlayerToNextMatch(AbstractClientHandler clientHandler) {
        server.addPlayer(clientHandler);
    }

    protected void received(AbstractViewEvent event) {
        if (!eventsQueue.offer(event)) {
            //TODO specific event type needed?
            send(new GenericEvent(event.getPlayer(), "Events generation failed."));
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