package it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller;

public class EndTurnEvent implements ControllerEventInterface {
    private String player;

    @Override
    public String getPlayer() {
        return player;
    }
}
