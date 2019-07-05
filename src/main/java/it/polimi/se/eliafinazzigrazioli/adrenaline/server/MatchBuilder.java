package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller.MatchController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.LoginRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

/**
 * MatchBuilder handles everything that's related to match generation and player dis/re/connection. It also guarantees player's nickname uniqueness.
 */
public class MatchBuilder {

    /**
     * Static reference of LOGGER
     */
    private static final Logger LOGGER = Logger.getLogger(MatchBuilder.class.getName());

    /**
     * Reference to the next starting match.
     */
    private MatchController nextMatch;

    /**
     * Map between matches and corresponding events queues.
     */
    private Map<MatchController, BlockingQueue<AbstractViewEvent>> matchToQueueMap = new HashMap<>();

    /**
     * Match ID counter
     */
    private int currentMatchID;

    /**
     * Keeps track of disconnected players.
     */
    private Map<String, MatchController> disconnectedPlayerToMatchMap = new HashMap<>();

    /**
     * List of logged players.
     */
    private List<String> loggedPlayers = new ArrayList<>();

    /**
     * Thread pool for matches.
     */
    private ExecutorService matchesExecutor = Executors.newCachedThreadPool();

    /**
     * Thread pool for queue consumers.
     */
    private ExecutorService consumersExecutor = Executors.newCachedThreadPool();

    /**
     * Match starting timer. Activated when the number of logged players reaches the minimum permitted.
     */
    private Timer timer = new Timer();

    /**
     * Instantiates a new Match builder.
     */
    public MatchBuilder() {
        instantiateNewMatch();
    }

    /**
     * Gets current match id.
     *
     * @return the current match id
     */
    public synchronized int getCurrentMatchID() {
        currentMatchID++;
        return currentMatchID;
    }

    /**
     * Starts timer.
     *
     * @param match the match
     */
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

    /**
     * Stops timer.
     */
    public void stopTimer() {
        timer.cancel();
        timer.purge();
        timer = new Timer();

        LOGGER.info("Timer stopped"); //TODO move to messages
    }

    /**
     * generates a new MatchController instance and prepares events queue and its respective consumer.
     */
    private void instantiateNewMatch() {
        LOGGER.info(Messages.MESSAGE_LOGGING_INFO_MATCH_NEW);

        // initialize next starting match
        nextMatch = new MatchController(this);
        matchToQueueMap.put(nextMatch, new LinkedBlockingQueue<>());

        nextMatch.setMatchID(getCurrentMatchID());

        ServerEventsConsumer nextConsumer = new ServerEventsConsumer(matchToQueueMap.get(nextMatch), nextMatch);

        consumersExecutor.execute(nextConsumer);
    }

    /**
     * Binds clientHandler to match's events queue and signs the client to the next starting match.
     *
     * @param clientHandler the client handler
     */
    public synchronized void signNewClient(AbstractClientHandler clientHandler) {
        clientHandler.setEventsQueue(matchToQueueMap.get(nextMatch));
        nextMatch.signNewClient(clientHandler);
    }

    /**
     * Exactly as signNewClient(), signClient() binds events queue with client handler and signs the latter on the first available match.
     * In this case however, the client was already signed on a previous match, which he wasn't able to log in.
     *
     * @param clientHandler
     */
    private synchronized void signClient(AbstractClientHandler clientHandler) {
        clientHandler.setEventsQueue(matchToQueueMap.get(nextMatch));
        nextMatch.signClient(clientHandler);
    }

    /**
     * As a client successfully performs login on a match, MatchController notifies the event to MatchBuilder.
     * If the minimum number of players is reached, the timer starts.
     * If the maximum number of players is reached, the match is kicked off.
     *
     * @param player the player
     * @param match the match
     */
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

    /**
     * adds player to disconnected players map.
     *
     * @param player the player
     * @param match the match
     */
    public void disconnectClient(String player, MatchController match) {
        disconnectedPlayerToMatchMap.put(player, match);
    }

    /**
     * removes player to disconnected players map.
     *
     * @param player the player
     * @param match the match
     */
    public void reconnectClient(String player, MatchController match) {
        disconnectedPlayerToMatchMap.remove(player, match);
    }

    /**
     * Checks if the logging player can log in the starting match.
     * If the nickname is associated to a different match, and the player results as disconnected, the reconnection procedure begins.
     *
     * @param event the login event
     * @param match the match inn which the player is trying to log in.
     * @return true if the logging procedure is permitted.
     * @throws PlayerAlreadyPresentException the player already present exception
     */
    public boolean validateLoginRequestEvent(LoginRequestEvent event, MatchController match) throws PlayerAlreadyPresentException {
        MatchController previousMatch = disconnectedPlayerToMatchMap.get(event.getPlayer());
        if (previousMatch != null && !match.equals(previousMatch)) {
            // The player was playing in another match, pop it from the new one.
            AbstractClientHandler clientHandler = match.popClient(event.getClientID());
            clientHandler.setEventsQueue(matchToQueueMap.get(previousMatch));
            previousMatch.getEventController().addVirtualView(clientHandler);
            previousMatch.getEventController().update(event);
            return false;
        } else if (match.equals(previousMatch)) {
            // he's now logging back to his original match
            disconnectedPlayerToMatchMap.remove(event.getPlayer());
            return true;
        } else if (loggedPlayers.contains(event.getPlayer())) {
            throw new PlayerAlreadyPresentException();
        }

        return true;
    }

    /**
     * Starts the specified match, instantiating a new match and redirecting not logged clients into it.
     *
     * @param match
     */
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

    /**
     * Removes leaving players from players lists.
     *
     * @param match the match
     */
    public synchronized void matchEnded(MatchController match) {
        List<String> leavingPlayers = match.getMatch().getPlayersNickname();

        loggedPlayers.removeAll(leavingPlayers);
        disconnectedPlayerToMatchMap.keySet().removeAll(leavingPlayers);

        matchToQueueMap.remove(match);
    }
}
