package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller.MatchController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.LoginRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

// Necessary to handle events queue
public class MatchBuilder {

    private static final Logger LOGGER = Logger.getLogger(MatchBuilder.class.getName());

    private MatchController nextMatch;
    private Map<MatchController, BlockingQueue<AbstractViewEvent>> matchToQueueMap = new HashMap<>();
    private int currentMatchID;

    //TODO never populated
    private Map<String, MatchController> disconnectedPlayerToMatchMap = new HashMap<>();
    private List<String> loggedPlayers = new ArrayList<>();

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
        nextMatch.signNewClient(clientHandler);
    }

    private synchronized void signClient(AbstractClientHandler clientHandler) {
        clientHandler.setEventsQueue(matchToQueueMap.get(nextMatch));
        nextMatch.signClient(clientHandler);
    }

    public synchronized void playerLogged(String player, MatchController match) {
        loggedPlayers.add(player);
        if (match.isFull()) {
            stopTimer();
            startMatch(match);
        } else if (match.isReady()) {
            stopTimer();
            startTimer(match);
        }
    }

    public void disconnectClient(String player, MatchController match) {
        disconnectedPlayerToMatchMap.put(player, match);
    }

    public void reconnectClient(String player, MatchController match) {
        disconnectedPlayerToMatchMap.remove(player, match);
    }

    public boolean validateLoginRequestEvent(LoginRequestEvent event, MatchController match) throws PlayerAlreadyPresentException {
        MatchController previousMatch = disconnectedPlayerToMatchMap.get(event.getPlayer());
        if (previousMatch != null && !previousMatch.equals(match)) {
            AbstractClientHandler clientHandler = match.popClient(event.getClientID());
            clientHandler.setEventsQueue(matchToQueueMap.get(previousMatch));
            previousMatch.getEventController().addVirtualView(clientHandler);
            previousMatch.getEventController().update(event);
            return false;
        } else if (loggedPlayers.contains(event.getPlayer())) {
            throw new PlayerAlreadyPresentException();
        }

        return true;
    }

    private synchronized void startMatch(MatchController match) {

        //TODO: move to Messages
        LOGGER.info("Game starting");

        List<AbstractClientHandler> notLoggedClients = match.popNotLoggedClients();
        // Kick-off next game
        matchesExecutor.execute(match);
        match.setStarted(true);

        instantiateNewMatch();

        for (AbstractClientHandler clientHandler : notLoggedClients) signClient(clientHandler);
    }
}
