package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Match;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MatchPhase;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class MatchController implements EventListenerInterface, Runnable {

    private static final Logger LOGGER = Logger.getLogger(MatchController.class.getName());

    private Match match;
    private PlayerController playerController;
    private CardController cardController;
    private EventController eventController;
    private Timer timer;

    private HashMap<MapType, Integer> votesMaps;

    private BlockingQueue<AbstractViewEvent> eventsQueue;

    public MatchController() {
        match = new Match();
        eventController = new EventController(this);
        playerController = new PlayerController(eventController, this);
        cardController = new CardController(eventController, this);

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
        return eventController;
    }

    public void addPlayer(String player) throws MaxPlayerException, PlayerAlreadyPresentException {
        match.addPlayer(player);
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
    public void run() {
        AbstractViewEvent nextEvent;
        try {
            while (!match.isEnded()) {
                nextEvent = eventsQueue.take();
                eventController.update(nextEvent);
            }
        } catch (InterruptedException e) {
            //TODO handle
        } finally {

        }
    }
}

