package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller.MatchController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

// Necessary to handle events queue
public class MatchBuilder {

    private static final Logger LOGGER = Logger.getLogger(MatchBuilder.class.getName());

    private MatchController nextMatch;

    private HashMap<MatchController, BlockingQueue<AbstractViewEvent>> matchToQueueMap = new HashMap<>();

    private ServerEventsConsumer nextConsumer;

    // Match-threads pools
    private ExecutorService matchesExecutor = Executors.newCachedThreadPool();
    private ExecutorService consumersExecutor = Executors.newCachedThreadPool();

    private Timer timer = new Timer();

    public MatchBuilder() {
        instantiateNewMatch();
    }

    // ####################### TIMER #######################
    public void startTimer() {
        LOGGER.info("Timer started"); //TODO move to messages
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                LOGGER.info("New game timeout occurred, starting next match"); //TODO move to messages
                startNextMatch();
            }
        }, Config.CONFIG_SERVER_NEW_GAME_TIMEOUT);
    }

    public void stopTimer() {
        LOGGER.info("Timer stopped"); //TODO move to messages

        timer.cancel();
        timer = new Timer();
    }

    private void instantiateNewMatch() {
        LOGGER.info("Next match initialization started.");

        // initialize next starting match
        nextMatch = new MatchController();
        matchToQueueMap.put(nextMatch, new LinkedBlockingQueue<>());

        nextConsumer = new ServerEventsConsumer(matchToQueueMap.get(nextMatch), nextMatch);

        consumersExecutor.execute(nextConsumer);
    }

    public synchronized void signNewClient(int clientID, AbstractClientHandler clientHandler) {
        stopTimer();

        nextMatch.signClient(clientID, clientHandler);
        clientHandler.setEventsQueue(matchToQueueMap.get(nextMatch));

        //TODO verify
        if (nextMatch.isFull()) {
            startNextMatch();
        } else if (nextMatch.isReady()) {
            startTimer();
        }

    }

    private synchronized void startNextMatch() {

        //TODO: move to Messages
        LOGGER.info("Game starting");

        // Kick-off next game
        matchesExecutor.execute(nextMatch);

        instantiateNewMatch();
    }
}
