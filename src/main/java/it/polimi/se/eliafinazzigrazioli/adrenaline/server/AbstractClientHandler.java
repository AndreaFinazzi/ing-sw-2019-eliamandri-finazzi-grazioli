package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.GenericEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ClientDisconnectionEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

/**
 * Represents the joint between the different networks implementations. It plays the role of a VirtualView in the MVC client-server pattern.
 */
public abstract class AbstractClientHandler implements Runnable {
    /**
     * Static reference to LOGGER.
     */
    protected static final Logger LOGGER = Logger.getLogger(AbstractClientHandler.class.getName());

    /**
     * Server reference.
     */
    protected transient Server server;

    /**
     * Correspondent client's ID.
     */
    protected int clientID;

    /**
     * Reference to the events queue to which offer incoming events.
     */
    protected BlockingQueue<AbstractViewEvent> eventsQueue;

    /**
     * Instantiates a new Abstract client handler.
     *
     * @param server the server
     */
    public AbstractClientHandler(Server server) {
        this.server = server;
    }

    /**
     * Implementations of this methods should send outgoing events to the respective client.
     *
     * @param event the event
     */
    public abstract void send(AbstractModelEvent event);

    /**
     * Gets client id.
     *
     * @return the client id
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * Sets events queue.
     *
     * @param eventsQueue the events queue
     */
    public void setEventsQueue(BlockingQueue<AbstractViewEvent> eventsQueue) {
        this.eventsQueue = eventsQueue;
    }

    /**
     * Notify incoming event to the set events queue.
     * In case of refusal, it automatically respond with a generic advice of receiving failure.
     *
     * @param event the event
     */
    protected void received(AbstractViewEvent event) {
        if (!eventsQueue.offer(event)) {
            LOGGER.warning(Messages.MESSAGE_LOGGING_INFO_EVENT_FAILURE);
            send(new GenericEvent(event.getPlayer(), Messages.MESSAGE_LOGGING_INFO_EVENT_FAILURE));
        }
    }

    /**
     * notifies a ClientDisconnectionEvent to the set events queue.
     */
    public void unregister() {
        received(new ClientDisconnectionEvent(this));
    }

    @Override
    public void run() {
        LOGGER.info(Messages.MESSAGE_LOGGING_INFO_CLIENT_HANDLER_INITIALIZED);
    }
}