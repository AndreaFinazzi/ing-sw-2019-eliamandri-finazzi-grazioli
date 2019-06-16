package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller.MatchController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;

import java.util.ArrayList;
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
    private int currentMatchID;

    // Match-threads pools
    private ExecutorService matchesExecutor = Executors.newCachedThreadPool();
    private ExecutorService consumersExecutor = Executors.newCachedThreadPool();

    private Timer timer = new Timer();

    public MatchBuilder() {
        instantiateNewMatch();
    }

    public synchronized int getCurrentMatchID() {
        currentMatchID++;
        return currentMatchID;
    }

    // ####################### TIMER #######################
    public void startTimer(MatchController match) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!match.isStarted()) {
                    LOGGER.info("New game timeout occurred, starting next match"); //TODO move to messages
                    startMatch(match);
                }
            }
        }, Config.CONFIG_SERVER_NEW_GAME_TIMEOUT);

        LOGGER.info("Timer started"); //TODO move to messages
    }

    public void stopTimer() {
        timer.cancel();
        timer.purge();
        timer = new Timer();

        LOGGER.info("Timer stopped"); //TODO move to messages
    }

    private void instantiateNewMatch() {
        LOGGER.info("Next match initialization started.");

        // initialize next starting match
        nextMatch = new MatchController(this);
        matchToQueueMap.put(nextMatch, new LinkedBlockingQueue<>());

        nextMatch.setMatchID(getCurrentMatchID());

        ServerEventsConsumer nextConsumer = new ServerEventsConsumer(matchToQueueMap.get(nextMatch), nextMatch);

        consumersExecutor.execute(nextConsumer);
    }

    public synchronized void signNewClient(AbstractClientHandler clientHandler) {
        clientHandler.setEventsQueue(matchToQueueMap.get(nextMatch));
        nextMatch.signClient(clientHandler);
    }

    public synchronized void playerLogged(MatchController match) {
        if (match.isFull()) {
            stopTimer();
            startMatch(match);
        } else if (match.isReady()) {
            stopTimer();
            startTimer(match);
        }
    }

    private synchronized void startMatch(MatchController match) {


        //TODO: move to Messages
        LOGGER.info("Game starting");

        ArrayList<AbstractClientHandler> notLoggedClients = match.popNotLoggedClients();
        // Kick-off next game
        matchesExecutor.execute(match);
        match.setStarted(true);

        instantiateNewMatch();

        for (AbstractClientHandler clientHandler : notLoggedClients) signNewClient(clientHandler);
    }
}
