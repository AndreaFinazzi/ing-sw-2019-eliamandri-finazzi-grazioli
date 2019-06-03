package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ConnectionResponseEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.LoginResponseEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.LoginRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ViewEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Match;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MatchPhase;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;
import it.polimi.se.eliafinazzigrazioli.adrenaline.server.AbstractClientHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class MatchController implements ViewEventsListenerInterface {

    private static final Logger LOGGER = Logger.getLogger(MatchController.class.getName());

    private Match match;
    private PlayerController playerController;
    private CardController cardController;
    private EventController eventController;
    private Timer timer;

    private Map<Integer, String> clientIDToPlayerMap = new HashMap<>();

    private HashMap<MapType, Integer> votesMaps;

    private BlockingQueue<AbstractViewEvent> eventsQueue;

    public MatchController() {
        match = new Match();
        eventController = new EventController(this);
        playerController = new PlayerController(eventController, this);
        cardController = new CardController(eventController, this);

        eventController.addViewEventsListener(LoginRequestEvent.class, this);

        match.addObserver(eventController);
    }

    public void initMatch(MapType choosenMap) {
        //TODO: to implement
        match.setPhase(MatchPhase.PLAYING);

        match.setGameBoard(choosenMap);

        //TODO verify
        match.increaseTurn();

        match.beginTurn();
    }

    public EventController getEventController() {
        LOGGER.info("Getting eventController");
        return eventController;
    }

    public void addPlayer(String player) throws MaxPlayerException, PlayerAlreadyPresentException {
        match.addPlayer(player);
    }

    public void addPlayer(int clientID, String player) throws MaxPlayerException, PlayerAlreadyPresentException {
        match.addPlayer(player);
    }

    public void signClient(Integer clientID, AbstractClientHandler clientHandler) {
        eventController.addVirtualView(clientHandler);
        clientIDToPlayerMap.put(clientID, null);
        eventController.update(new ConnectionResponseEvent(clientID, "Username required."));
    }

    public void removePlayer(String nickname) {
        match.removePlayer(nickname);
    }

    public void startRecruiting() {
        match.setPhase(MatchPhase.RECRUITING);
    }

    public boolean isReady() {
        return match.getPlayers().size() >= Rules.GAME_MIN_PLAYERS;
    }

    public boolean isFull() {
        return match.getPlayers().size() == Rules.GAME_MAX_PLAYERS;
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
        LoginResponseEvent responseEvent = new LoginResponseEvent(event.getSourceClientID());
        responseEvent.setClientID(event.getClientID());

        try {
            addPlayer(event.getClientID(), event.getPlayer());
            responseEvent.setSuccess(true);
            responseEvent.setMessage("Welcome to Adrenaline, " + event.getPlayer());

        } catch (MaxPlayerException e) {
            responseEvent.setSuccess(false);
            responseEvent.setMessage("MaxPlayerException thrown");

        } catch (PlayerAlreadyPresentException e) {
            responseEvent.setSuccess(false);
            responseEvent.setMessage("Username already in game, try with a different nick!");
        }
        eventController.update(responseEvent);
    }
}

