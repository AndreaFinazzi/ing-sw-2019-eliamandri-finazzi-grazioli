package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ConnectionResponseEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.LoginResponseEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.MatchStartedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.LoginRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ViewEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.AvatarNotAvailableException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.AbstractClientHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.logging.Logger;

public class MatchController implements ViewEventsListenerInterface, Runnable {

    private static final Logger LOGGER = Logger.getLogger(MatchController.class.getName());

    private Match match;
    private PlayerController playerController;
    private CardController cardController;
    private EventController eventController;
    private Timer timer;

    private Map<Integer, String> clientIDToPlayerMap = new HashMap<>();

    private HashMap<MapType, Integer> votesMaps;

//    private BlockingQueue<AbstractViewEvent> eventsQueue;

    public MatchController() {
        match = new Match();
        match.setPhase(MatchPhase.RECRUITING);

        eventController = new EventController(this);
        playerController = new PlayerController(eventController, this);
        cardController = new CardController(eventController, this);

        eventController.addViewEventsListener(LoginRequestEvent.class, this);

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
    public synchronized void voteMap(int chosenMap) {
        Integer choise;
        switch (chosenMap) {
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


    @Override
    public void handleEvent(LoginRequestEvent event) throws HandlerNotImplementedException {
        LoginResponseEvent responseEvent = new LoginResponseEvent(event.getClientID());
        try {
            Player player = match.addPlayer(event.getPlayer(), event.getChosenAvatar());
            responseEvent.setSuccess(true);
            responseEvent.setAssignedAvatar(player.getAvatar());
            responseEvent.setMessage("Welcome to Adrenaline, " + event.getPlayer());

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
    public void run() {
        match.notifyObservers(new MatchStartedEvent());
    }
}