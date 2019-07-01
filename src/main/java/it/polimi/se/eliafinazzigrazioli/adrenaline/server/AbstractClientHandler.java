package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.GenericEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ClientDisconnectionEvent;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public abstract class AbstractClientHandler implements Runnable {
    protected static final Logger LOGGER = Logger.getLogger(AbstractClientHandler.class.getName());

    protected transient Server server;

    protected int clientID;

    protected BlockingQueue<AbstractViewEvent> eventsQueue;

    public AbstractClientHandler(Server server) {
        this.server = server;
    }

    public abstract void send(AbstractModelEvent event);

    // register starting match

    public int getClientID() {
        return clientID;
    }

    public void setEventsQueue(BlockingQueue<AbstractViewEvent> eventsQueue) {
        this.eventsQueue = eventsQueue;
    }

    protected void received(AbstractViewEvent event) {
        if (!eventsQueue.offer(event)) {
            //TODO specific event type needed?
            send(new GenericEvent(event.getPlayer(), "Events generation failed."));
        }
    }

    public void unregister() {
        received(new ClientDisconnectionEvent(this));
    }

    //TODO: add message constants to dedicated class
    @Override
    public void run() {
        LOGGER.info("ClientHandler initialized");
    }
}