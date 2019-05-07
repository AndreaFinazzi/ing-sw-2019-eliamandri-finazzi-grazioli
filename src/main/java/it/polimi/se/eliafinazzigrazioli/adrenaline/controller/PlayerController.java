package it.polimi.se.eliafinazzigrazioli.adrenaline.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.MovePlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.PlayerConnectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Coordinates;

import java.util.HashMap;
import java.util.Map;

public class PlayerController implements EventListenerInterface {
    private Map<String, Player> players = new HashMap<>();
    private MatchController matchController;

    public PlayerController(EventController eventController, MatchController matchController) {
        this.matchController = matchController;
        //listen to interesting events
        eventController.addEventListener(PlayerConnectedEvent.class, this);
        eventController.addEventListener(MovePlayEvent.class, this);
    }

    @Override
    public void handleEvent(MovePlayEvent event) {
        Player currentPlayer = players.get(event.getPlayer());

        if (event.getPath() != null && event.getPath().size() > 0) {
            for (Coordinates nextSquare : event.getPath()) {
                //currentPlayer.setPosition(); //TODO complete moving player to new position
            }
        }
    }

    public void handleEvent(PlayerConnectedEvent event) {
        matchController.addPlayer(event.getPlayer());
    }
}