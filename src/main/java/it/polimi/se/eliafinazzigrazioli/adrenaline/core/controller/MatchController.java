package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ConnectionResponseEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.LoginResponseEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.MatchStartedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.LoginRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.MapVoteEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ViewEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.AvatarNotAvailableException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.AbstractClientHandler;

import java.util.*;
import java.util.logging.Logger;

public class MatchController implements ViewEventsListenerInterface, Runnable {

    private static final Logger LOGGER = Logger.getLogger(MatchController.class.getName());

    private Match match;
    private PlayerController playerController;
    private CardController cardController;
    private EventController eventController;
    private Timer timer;

    private Map<Integer, String> clientIDToPlayerMap = new HashMap<>();

    private Map<MapType, Integer> mapVotesCounter;
    private int mapVotes = 0;

//    private BlockingQueue<AbstractViewEvent> eventsQueue;

    public MatchController() {
        match = new Match();
        match.setPhase(MatchPhase.RECRUITING);

        eventController = new EventController(this);
        playerController = new PlayerController(eventController, this);
        cardController = new CardController(eventController, this);

        eventController.addViewEventsListener(LoginRequestEvent.class, this);
        eventController.addViewEventsListener(MapVoteEvent.class, this);

        match.addObserver(eventController);
    }

    public void initMatch(MapType chosenMap) {
        //TODO: to implement
        match.setPhase(MatchPhase.PLAYING);

        match.setGameBoard(chosenMap);

        //TODO verify
        match.increaseTurn();

        match.beginTurn();
    }

    public EventController getEventController() {
        return eventController;
    }

    public void addPlayer(String player, Avatar avatar) throws MaxPlayerException, PlayerAlreadyPresentException, AvatarNotAvailableException {
        match.addPlayer(player, avatar);
    }

    public void signClient(Integer clientID, AbstractClientHandler clientHandler) {
        eventController.addVirtualView(clientHandler);
        clientIDToPlayerMap.put(clientID, null);
        eventController.update(new ConnectionResponseEvent(clientID, "Username and Avatar required.", match.getAvailableAvatars()));
    }

    public void removePlayer(String nickname) {
        match.removePlayer(nickname);
    }

    public boolean isReady() {
        return clientIDToPlayerMap.size() >= Rules.GAME_MIN_PLAYERS;
    }

    public boolean isFull() {
        return clientIDToPlayerMap.size() == Rules.GAME_MAX_PLAYERS;
    }

    //Getter
    public Player.AbstractPlayerList getPlayers() {
        return match.getPlayers();
    }

    public Match getMatch() {
        return match;
    }

    public ArrayList<String> getPlayersNicknames() {
        return match.getPlayersNicknames();
    }

    // requires 0 <= chosenMap <Rules.GAME_MAX_MAPS
    public synchronized void voteMap_(int chosenMap) {
        Integer choise;
        switch (chosenMap) {
            case 1:
                choise = mapVotesCounter.get(MapType.ONE);
                choise++;
                mapVotesCounter.put(MapType.ONE, choise);
                break;

            case 2:
                choise = mapVotesCounter.get(MapType.TWO);
                choise++;
                mapVotesCounter.put(MapType.TWO, choise);
                break;

            case 3:
                choise = mapVotesCounter.get(MapType.THREE);
                choise++;
                mapVotesCounter.put(MapType.THREE, choise);
                break;

            case 4:
                choise = mapVotesCounter.get(MapType.FOUR);
                choise++;
                mapVotesCounter.put(MapType.FOUR, choise);
                break;
        }
    }

    private synchronized void voteMap(MapType mapType) {
        mapVotes++;
        int votesForMap = mapVotesCounter.getOrDefault(mapType, 0);
        mapVotesCounter.put(mapType, ++votesForMap);
    }

    private MapType getWinningMap() {
        ArrayList<MapType> winningMaps = new ArrayList<>();
        int higherVotesCount = 0;

        for (MapType mapType : mapVotesCounter.keySet()) {
            if (mapVotesCounter.get(mapType) > higherVotesCount) {
                higherVotesCount = mapVotesCounter.get(mapType);
                winningMaps.clear();
                winningMaps.add(mapType);
            } else if (mapVotesCounter.get(mapType) == higherVotesCount) {
                winningMaps.add(mapType);
            }
        }

        return winningMaps.get(new Random().nextInt(winningMaps.size()));
    }

    @Override
    public void handleEvent(LoginRequestEvent event) throws HandlerNotImplementedException {
        LoginResponseEvent responseEvent = new LoginResponseEvent(event.getClientID());
        try {
            Player player = match.addPlayer(event.getPlayer(), event.getChosenAvatar());
            responseEvent.setSuccess(true);
            responseEvent.setPlayer(player.getPlayerNickname());
            responseEvent.setAssignedAvatar(player.getAvatar());
            responseEvent.setMessage("Welcome to Adrenaline, " + event.getPlayer());
            clientIDToPlayerMap.put(player.getClientID(), player.getPlayerNickname());

        } catch (MaxPlayerException e) {
            responseEvent.setSuccess(false);
            responseEvent.setAvailableAvatars(match.getAvailableAvatars());
            responseEvent.setMessage("MaxPlayerException thrown");

        } catch (PlayerAlreadyPresentException e) {
            responseEvent.setSuccess(false);
            responseEvent.setAvailableAvatars(match.getAvailableAvatars());
            responseEvent.setMessage("Username already in game, try with a different nick!");
        } catch (AvatarNotAvailableException e) {
            e.printStackTrace();
        }
        eventController.update(responseEvent);
    }

    @Override
    public void handleEvent(MapVoteEvent event) throws HandlerNotImplementedException {
        voteMap(event.getVotedMap());
        if (mapVotes == getPlayers().size()) {
            initMatch(getWinningMap());
        }
    }


    @Override
    public void run() {
        HashMap<String, Avatar> playerToAvatarMap = new HashMap<>();
        for (Player player : match.getPlayers()) {
            playerToAvatarMap.put(player.getPlayerNickname(), player.getAvatar());
        }

        mapVotesCounter = new EnumMap<>(MapType.class);

        match.notifyObservers(new MatchStartedEvent(playerToAvatarMap, new ArrayList<>(Arrays.asList(MapType.values()))));
    }
}