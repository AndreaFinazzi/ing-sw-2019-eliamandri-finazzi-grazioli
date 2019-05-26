package it.polimi.se.eliafinazzigrazioli.adrenaline.server;

// Server main class

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller.MatchController;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Config;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Messages;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


//TODO CHECK CONCURRENCY ISSUES OF THE WHOLE LOGIC!!!

public class Server {

    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    private ServerSocketManager serverSocketManager;
    private HashMap<String, MatchController> playerToMatchMap;
    private HashMap<String, AbstractClientHandler> playerToClientHandler;
    private MatchController nextMatch;
    private Registry registry;
    private Timer timer;
    private int currentClientID;
    private HashMap<MapType, Integer> votesMaps;

    private Server() {
        LOGGER.info("Creating Server"); //TODO move to messages
        timer = new Timer();
        playerToMatchMap = new HashMap<>();
        nextMatch = new MatchController();
        currentClientID = 0;
        votesMaps = new HashMap<>();
        try {
            registry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        for(MapType key : MapType.values())
            votesMaps.put(key, 0);
    }

    public void startServerSocket() {
        LOGGER.info("Starting socket manager");
        serverSocketManager = new ServerSocketManager(this, Config.CONFIG_SERVER_SOCKET_PORT);
        serverSocketManager.startServerSocket();
    }

    public void startServerRMI() {
        LOGGER.info("Starting RMI manager");

    }

    private void mapPlayerToMatch(String player, MatchController matchController) {
        playerToMatchMap.put(player, matchController);
    }

    //TODO It may not be the best choice to block Server...
    public synchronized void addPlayer(String player) throws MaxPlayerException, PlayerAlreadyPresentException {
        stopTimer();

        nextMatch.addPlayer(player);
        mapPlayerToMatch(player, nextMatch);

        if (nextMatch.isFull()) {
            startNextMatch();
        } else if (nextMatch.isReady()) {
            startTimer();
        }
    }

    public synchronized void addPlayer(String player, AbstractClientHandler clientHandler) throws MaxPlayerException, PlayerAlreadyPresentException{
        stopTimer();

        nextMatch.addPlayer(player);
        mapPlayerToMatch(player, nextMatch);
        playerToClientHandler.put(player, clientHandler);

        if (nextMatch.isFull()) {
            startNextMatch();
        } else if (nextMatch.isReady()) {
            startTimer();
        }
    }

    private synchronized void startNextMatch() {
        LOGGER.info("Next match initialization: start");
        // Add next match EventController as an Observer of RemoteViews

        ArrayList<String> nextPlayingPlayers = nextMatch.getPlayersNicknames();
        for (String player : nextPlayingPlayers) {
            playerToClientHandler.get(player).bindViewToEventController(nextMatch.getEventController());
        }

        nextMatch.initMatch();
        //TODO: move to Messages
        LOGGER.info("Game started");
        nextMatch = new MatchController();
    }

    public synchronized void addPlayer(String player, MatchController matchController) throws MaxPlayerException,
            PlayerAlreadyPresentException{
        nextMatch.addPlayer(player);
        mapPlayerToMatch(player, matchController);
    }

    public void removePlayer(String player) {
        playerToMatchMap.remove(player);
    }

    public void removePlayer(Player player) {
        playerToMatchMap.remove(player.getPlayerNickname());
    }

    public void startTimer() {
        LOGGER.info("Timer tarted"); //TODO move to messages
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                LOGGER.info("New game timeout occurred, starting next match"); //TODO move to messages
                startNextMatch();
            }
        }, Config.CONFIG_SERVER_NEW_GAME_TIMEOUT);
    }

    public void stopTimer() {
        timer.cancel();
    }

    public synchronized Registry getRegistry() {
        return registry;
    }

    public synchronized int getCurrentClientID() {
        currentClientID++;
        return currentClientID;
    }

    public synchronized MatchController getNextMatch() {
        return nextMatch;
    }

    // requires 0 <= chosenMap <Rules.GAME_MAX_MAPS
    public synchronized void voteMap(int chosenMap) {
        Integer choise;
        switch(chosenMap) {
            case 1:
                choise = votesMaps.get(MapType.ONE);
                choise++;
                votesMaps.put(MapType.ONE, choise);
                break;

            case 2:
                choise = votesMaps.get(MapType.TWO);
                choise++;
                votesMaps.put(MapType.TWO, choise);
                break;

            case 3:
                choise = votesMaps.get(MapType.THREE);
                choise++;
                votesMaps.put(MapType.THREE, choise);
                break;

            case 4:
                choise = votesMaps.get(MapType.FOUR);
                choise++;
                votesMaps.put(MapType.FOUR, choise);
                break;
        }
    }

    public synchronized MapType chosenMaps(){
        int tempMax=0, max=0;
        ArrayList<MapType> votes = new ArrayList<>();
        for(MapType key : votesMaps.keySet()){
            tempMax = votesMaps.get(key);
            if(tempMax == max) {
                votes.add(key);
            }
            else if(tempMax > max){
                votes = new ArrayList<>();
                votes.add(key);
            }

        }
        if(votes.size() > 1){
            return votes.get((int)Math.random() * (votes.size()-1));
        }
        else return votes.get(0);
    }

    public static void main(String[] args) {
        Server server = new Server();

        new Thread(new ServerSocketManager(server, Config.CONFIG_SERVER_SOCKET_PORT)).start();

        new Thread(new ServerRmiManager(server)).start();

        //server.startServerSocket ();
        //server.startServerRMI ();


        //nextMatch.startRecruiting();

        LOGGER.info(Messages.MESSAGE_LOGGING_INFO_SERVER_STARTED);
    }
}
