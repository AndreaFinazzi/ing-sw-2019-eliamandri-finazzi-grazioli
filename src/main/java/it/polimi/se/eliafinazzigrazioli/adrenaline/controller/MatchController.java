package it.polimi.se.eliafinazzigrazioli.adrenaline.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Match;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.MatchPhase;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MatchController implements EventListenerInterface {

    private static final Logger LOGGER = Logger.getLogger(MatchController.class.getName());

    private Match match;
    private PlayerController playerController;
    private CardController cardController;
    private EventController eventController;

    public MatchController() {
        match = new Match();

        eventController = new EventController(this);
        playerController = new PlayerController(eventController);
        cardController = new CardController(eventController);

    }

    private void initMatch() {

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

    public void startRecruiting() {
        match.setPhase(MatchPhase.RECRUITING);
    }
}
