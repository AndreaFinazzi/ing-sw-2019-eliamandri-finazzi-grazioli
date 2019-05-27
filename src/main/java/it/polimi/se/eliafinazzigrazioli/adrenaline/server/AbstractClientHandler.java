package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.GenericEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public abstract class AbstractClientHandler implements Runnable, ModelEventsListenerInterface {
    protected Server server;
    protected static final Logger LOGGER = Logger.getLogger(ClientHandlerSocket.class.getName());
    protected AbstractViewEvent nextReceivedEvent;

    private BlockingQueue<AbstractViewEvent> eventsQueue;

    // register starting match
    public void setEventsQueue(BlockingQueue<AbstractViewEvent> eventsQueue) {
        this.eventsQueue = eventsQueue;
//        eventController.addModelEventsListener(this);
    }

    abstract void send(AbstractEvent event);

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
        LOGGER.info("Client initialized: waiting for player's nickname");

        setup();

//        // SAMPLE, changeit!
//        //TODO check if correct
//        LOGGER.info(nextReceivedEvent.getMessage());
//        view.notifyObservers(nextReceivedEvent);
//        if (nextReceivedEvent.getMessage().equals("tony"))
//            send(new GenericEvent(null, "test tony "));
//        else
//            send(new GenericEvent(null, "test finazzi"));
        //server.addPlayer(nextReceivedEvent.getMessage());
    }

    @Override
    public void handleEvent(AbstractModelEvent event) throws HandlerNotImplementedException {
        send(event);

    }
}