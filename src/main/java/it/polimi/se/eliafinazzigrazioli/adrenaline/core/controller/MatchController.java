package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Match;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MatchPhase;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatchController implements EventListenerInterface {

    private static final Logger LOGGER = Logger.getLogger(MatchController.class.getName());

    private Match match;
    private PlayerController playerController;
    private CardController cardController;
    private EventController eventController;
    private Timer timer;

    public MatchController() {
        match = new Match();

        eventController = new EventController(this);
        playerController = new PlayerController(eventController, this);
        cardController = new CardController(eventController, this);

    }

    public void initMatch() {
        //TODO: to implement
    }

    public EventController getEventController() {
        return eventController;
    }

    public void addPlayer(String player) {
        try {
            match.addPlayer(player);
        } catch (MaxPlayerException e) {
            // TODO: define handling strategy
            LOGGER.log(Level.INFO, e.toString(), e);
        } catch (PlayerAlreadyPresentException e) {
            // TODO: define handling strategy
            LOGGER.log(Level.INFO, e.toString(), e);
        }
    }

    public void removePlayer(String nickname) {
        match.removePlayer (nickname);
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
}

