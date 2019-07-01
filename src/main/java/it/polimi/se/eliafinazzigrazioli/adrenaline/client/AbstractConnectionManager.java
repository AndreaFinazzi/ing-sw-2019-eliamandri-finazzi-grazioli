package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.GenericViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public abstract class AbstractConnectionManager implements Observer {

    protected static final Logger LOGGER = Logger.getLogger(AbstractConnectionManager.class.getName());

    //TODO should be placed in config file
    protected static final int CONNECTION_ATTEMPT_DELAY = 500;
    protected static final int CONNECTION_MAX_ATTEMPTS = 20;

    protected int connection_attempts = 0;

    protected Client client;

    private ExecutorService handlersExecutor = Executors.newCachedThreadPool();

    public AbstractConnectionManager(Client client) {
        this.client = client;
        //this.playerName = playerName; Useless assignment TODO check necessity of name in the constructor
        client.getView().addObserver(this);
    }

    public abstract void send(AbstractViewEvent event);

    public abstract void init();

    public abstract void performRegistration();

    public abstract void disconnect();

    public void received(AbstractModelEvent event) {
        if (client.getEventsQueue() == null) {
            LOGGER.info("Trying to directly update eventController");
            update(event);
        } else if (!client.getEventsQueue().offer(event)) {
            //TODO specific event type needed?
            send(new GenericViewEvent(client.getClientID(), client.getPlayerName(), "Events generation failed."));
        }
    }

    public String getPlayerName() {
        return client.getPlayerName();
    }

    // Outgoing events
    @Override
    public void update(AbstractViewEvent event) {
        send(event);
    }


    // Incoming events
    @Override
    public void update(AbstractModelEvent event) {
        //Visitor pattern
        //handlersExecutor.execute(() -> {
            try {
                event.handle(client.getView());
            } catch (HandlerNotImplementedException e) {
                e.printStackTrace();
            }
        //});
    }

}
