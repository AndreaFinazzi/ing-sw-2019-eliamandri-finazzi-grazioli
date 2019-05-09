package it.polimi.se.eliafinazzigrazioli.adrenaline.core.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.MovePlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.PlayerConnectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.ViewEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MovementNotAllowedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerController implements EventListenerInterface {
    //private Map<String, Player> players = new HashMap<>();
    private MatchController matchController;

    public PlayerController(EventController eventController, MatchController matchController) {
        this.matchController = matchController;
        //listen to interesting events
        eventController.addViewEventsListener(PlayerConnectedEvent.class, this);
        eventController.addViewEventsListener(MovePlayEvent.class, this);
    }

    @Override
    public void handleEvent(MovePlayEvent event) {
        Player currentPlayer = matchController.getPlayers ().get(event.getPlayer());
        System.out.println (currentPlayer);
        List<Coordinates> path = event.getPath();
        if (path != null && path.size() > 0) {
            try {
                matchController.getMatch().playerMovement(currentPlayer, path);
            } catch (MovementNotAllowedException e){
                //TODO invalid movement handling strategy
            }
        }
    }

    public void handleEvent(PlayerConnectedEvent event) {
        matchController.addPlayer(event.getPlayer());
    }
}