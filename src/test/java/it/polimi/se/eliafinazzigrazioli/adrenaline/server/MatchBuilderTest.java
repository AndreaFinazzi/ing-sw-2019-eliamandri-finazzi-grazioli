package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller.MatchController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * The type Match builder test.
 */
public class MatchBuilderTest {

    private MatchBuilder matchBuilder;

    private AbstractClientHandlerTest abstractClientHandlerTest;

    private MatchController matchController;

    /**
     * Sets up.
     *
     * @throws Exception the exception
     */
    @Before
    public void setUp() throws Exception {
        matchBuilder = new MatchBuilder();
        abstractClientHandlerTest = new AbstractClientHandlerTest(null);
        matchController = new MatchController(matchBuilder);
    }

    /**
     * Gets current match id.
     */
    @Test
    public void getCurrentMatchID() {
        assertEquals(2, matchBuilder.getCurrentMatchID());
        for(int i = 0; i<5; i++) {
            assertEquals(i+3,matchBuilder.getCurrentMatchID());
        }
    }

    /**
     * Start timer.
     */
    @Test
    public void startTimer() {
        matchBuilder.startTimer(matchController);
    }

    /**
     * Stop timer.
     */
    @Test
    public void stopTimer() {
        matchBuilder.stopTimer();
    }

    /**
     * Sign new client.
     */
    @Test
    public void signNewClient() {
        matchBuilder.signNewClient(abstractClientHandlerTest);
        AbstractClientHandlerTest abstractClientHandlerTest1 = new AbstractClientHandlerTest(null);
        AbstractClientHandlerTest abstractClientHandlerTest2 = new AbstractClientHandlerTest(null);
        AbstractClientHandlerTest abstractClientHandlerTest3 = new AbstractClientHandlerTest(null);
        AbstractClientHandlerTest abstractClientHandlerTest4 = new AbstractClientHandlerTest(null);
        matchBuilder.signNewClient(abstractClientHandlerTest2);
        matchBuilder.signNewClient(abstractClientHandlerTest3);
        matchBuilder.signNewClient(abstractClientHandlerTest4);
        matchBuilder.signNewClient(abstractClientHandlerTest1);
        matchBuilder.startTimer(matchController);
    }

    /**
     * Player logged.
     */
    @Test
    public void playerLogged() {
        matchBuilder.playerLogged("tony", matchController);
    }
}