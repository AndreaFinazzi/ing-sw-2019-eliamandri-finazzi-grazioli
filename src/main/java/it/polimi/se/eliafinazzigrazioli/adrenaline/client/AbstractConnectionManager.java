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

/**
 * The type Abstract connection manager.
 */
public abstract class AbstractConnectionManager implements Observer {

    /**
     * The constant LOGGER.
     */
    protected static final Logger LOGGER = Logger.getLogger(AbstractConnectionManager.class.getName());

    /**
     * The Connection attempts.
     */
    protected int connection_attempts = 0;
    private Timer turnTimer = new Timer();
    /**
     * The Client.
     */
    protected Client client;

    /**
     * The Port.
     */
    protected int port;

    private ExecutorService handlersExecutor = Executors.newCachedThreadPool();

    /**
     * Instantiates a new Abstract connection manager.
     *
     * @param client the client
     */
    public AbstractConnectionManager(Client client) {
        this.client = client;
        //this.playerName = playerName; Useless assignment TODO check necessity of name in the constructor
        client.getView().addObserver(this);
    }

    /**
     * Send.
     *
     * @param event the event
     */
    public abstract void send(AbstractViewEvent event);

    /**
     * Init.
     */
    public abstract void init();

    /**
     * Perform registration.
     */
    public abstract void performRegistration();

    /**
     * Disconnect.
     */
    public abstract void disconnect();

    /**
     * Received.
     *
     * @param event the event
     */
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

    /**
     * Unregister.
     */
    protected void unregister() {
        send(new ClientDisconnectionEvent(client.getClientID(), client.getPlayerName()));
    }

    /**
     * Gets player name.
     *
     * @return the player name
     */
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
