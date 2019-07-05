package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;



public class AbstractClientHandlerTest extends AbstractClientHandler {

    private AbstractClientHandler abstractClientHandler;
    @Before
    public void setUp() throws Exception {
        AbstractClientHandler abstractClientHandler = new AbstractClientHandlerTest(null);
    }

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