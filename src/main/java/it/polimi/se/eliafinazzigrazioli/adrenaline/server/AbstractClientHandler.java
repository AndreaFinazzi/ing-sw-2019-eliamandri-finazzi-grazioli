package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller.EventController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.AbstractEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.controller.GenericEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.view.RemoteView;

import java.util.logging.Logger;

public abstract class AbstractClientHandler implements Runnable {
    protected Server server;
    protected static final Logger LOGGER = Logger.getLogger(ClientHandlerSocket.class.getName());
    protected AbstractEvent nextReceivedEvent;
    private RemoteView view;

    // register starting match
    public void bindViewToEventController(EventController eventController) {

        view.addObserver(eventController);
        eventController.addModelEventsListener(view);

    }

    abstract void send(AbstractEvent event);

    abstract AbstractEvent receive();

    private void setup() {
        send(new GenericEvent("Waiting for nickname"));
        nextReceivedEvent = receive();

        //initialize RemoteView
        view = new RemoteView(nextReceivedEvent.getMessage());

        //Register on server
        server.addPlayer(view.getPlayer(), this);
    }

    //TODO: this should do something useful
    //TODO: add message constants to dedicated class
    @Override
    public void run() {
        LOGGER.info("Client initialized: waiting for player's nickname");

        setup();

        // SAMPLE, changeit!
        //TODO check if correct
        LOGGER.info(nextReceivedEvent.getMessage());
        view.notifyObservers(nextReceivedEvent);
        if (nextReceivedEvent.getMessage().equals("tony"))
            send(new GenericEvent("test tony "));
        else
            send(new GenericEvent("test finazzi"));
        //server.addPlayer(nextReceivedEvent.getMessage());
    }
}