package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller.GenericEvent;

import java.util.logging.Logger;

public abstract class AbstractClientHandler implements Runnable {
    protected Server server;
    protected static final Logger LOGGER = Logger.getLogger(ClientHandlerSocket.class.getName());
    protected AbstractEvent nextReceivedEvent;
    private EventController eventController;

    abstract void send(AbstractEvent event);

    abstract AbstractEvent receive();

    //TODO: this should do something useful
    //TODO: add message constants to dedicated class
    @Override
    public void run() {
        LOGGER.info("Client initialized: waiting for player's nickname");
        send(new GenericEvent("Waiting for nickname"));
        nextReceivedEvent = receive();
        LOGGER.info (nextReceivedEvent.getMessage ());
        if (nextReceivedEvent.getMessage ().equals ("tony"))
            send (new GenericEvent ("test tony "));
        else
            send (new GenericEvent ("test finazzi"));
        //server.addPlayer(nextReceivedEvent.getMessage());
    }
}
