package it.polimi.se.eliafinazzigrazioli.adrenaline.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Match;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.MatchPhase;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Rules;

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
        if (match.getPlayers().size() >= Rules.GAME_MIN_PLAYERS)
            return true;
        return false;
    }

    public boolean isFull() {
        if (match.getPlayers().size() == Rules.GAME_MAX_PLAYERS)
            return true;
        return false;
    }

    //Getter


    public Match getMatch() {
        return match;
    }
}
