package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

// Server main class

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.MatchController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Config;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Messages;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    ServerSocketManager serverSocketManager;
    HashMap<String, MatchController> playerToMatchMap;
    MatchController nextMatch;
    private Timer timer;


    private Server() {
        timer = new Timer();
        playerToMatchMap = new HashMap<>();
        nextMatch = new MatchController();
    }

    public void addPlayer(String player) {
        stopTimer();

        nextMatch.addPlayer(player);
        playerToMatchMap.put(player, nextMatch);

        if (nextMatch.isFull()) {
            nextMatch.initMatch();
            //TODO: move to Messages
            LOGGER.info("Game started");
            nextMatch = new MatchController();
        } else if (nextMatch.isReady()) {
            startTimer();
        }
    }

    public void addPlayer(String player, MatchController matchController) {
        nextMatch.addPlayer(player);
        playerToMatchMap.put(player, matchController);
    }

    public void removePlayer(String player) {
        playerToMatchMap.remove(player);
    }

    public void removePlayer(Player player) {
        playerToMatchMap.remove(player.getPlayerNickname());
    }

    public void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextMatch.initMatch();
                //TODO: move to Messages
                LOGGER.info("Game started");
                nextMatch = new MatchController();
            }
        }, Config.CONFIG_SERVER_NEW_GAME_TIMEOUT);
    }

    public void stopTimer() {
        timer.cancel();
    }

    public void main(String[] args) {
        Server server = new Server();

        serverSocketManager = new ServerSocketManager(server, Config.CONFIG_SERVER_SOCKET_PORT);
        serverSocketManager.startServerSocket();

        nextMatch.startRecruiting();

        LOGGER.info(Messages.MESSAGE_LOGGING_INFO_SERVER_STARTED);
    }
}
