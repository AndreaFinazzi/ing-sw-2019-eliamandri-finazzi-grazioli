package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ClientDisconnectionEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.GenericViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public abstract class AbstractConnectionManager implements Observer {

    protected static final Logger LOGGER = Logger.getLogger(AbstractConnectionManager.class.getName());

    protected int connection_attempts = 0;
    private Timer turnTimer = new Timer();
    protected Client client;

    protected int port;

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
        if (event.isRequest()) {
            turnTimer = new Timer();
            turnTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    client.setDisconnected();
                }
            }, Config.CONFIG_MATCH_TURN_TIMEOUT);
        }

        if (client.getEventsQueue() == null) {
            LOGGER.info("Trying to directly update eventController");
            update(event);
        } else if (!client.getEventsQueue().offer(event)) {
            //TODO specific event type needed?
            send(new GenericViewEvent(client.getClientID(), client.getPlayerName(), "Events generation failed."));
        }
    }

    protected void unregister() {
        send(new ClientDisconnectionEvent(client.getClientID(), client.getPlayerName()));
    }

    public String getPlayerName() {
        return client.getPlayerName();
    }

    // Outgoing events
    @Override
    public void update(AbstractViewEvent event) {
        send(event);
        turnTimer.cancel();
        turnTimer = new Timer();
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
