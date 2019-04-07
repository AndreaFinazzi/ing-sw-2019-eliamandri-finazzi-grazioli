package it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller;

public class BeginTurnEvent implements ControllerEventInterface {
    private String player;

    @Override
    public String getPlayer() {
        return player;
    }
}
