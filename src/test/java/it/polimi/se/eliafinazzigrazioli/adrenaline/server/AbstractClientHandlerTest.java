package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import org.junit.Before;

import java.util.concurrent.BlockingQueue;


/**
 * The type Abstract client handler test.
 */
public class AbstractClientHandlerTest extends AbstractClientHandler {

    private AbstractClientHandler abstractClientHandler;

    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        AbstractClientHandler abstractClientHandler = new AbstractClientHandlerTest(null);
    }

    /**
     * Instantiates a new Abstract client handler test.
     *
     * @param server the server
     */
    public AbstractClientHandlerTest(Server server) {
        super(server);
    }

    @Override
    public void send(AbstractModelEvent event) {
        System.out.println("ho spedito evento (abstractClientHandler)");
    }

    @Override
    public int getClientID() {
        return super.getClientID();
    }

    @Override
    public void setEventsQueue(BlockingQueue<AbstractViewEvent> eventsQueue) {
        super.setEventsQueue(eventsQueue);
    }

    @Override
    protected void received(AbstractViewEvent event) {
        System.out.println(event);
    }

    @Override
    public void unregister() {
        super.unregister();
    }

    @Override
    public void run() {
        super.run();
    }
}