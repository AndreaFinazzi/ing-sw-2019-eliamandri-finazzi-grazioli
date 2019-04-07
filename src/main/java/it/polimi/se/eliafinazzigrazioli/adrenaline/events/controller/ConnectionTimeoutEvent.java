package it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller;

public class ConnectionTimeoutEvent implements ControllerEventInterface {
    private String message;
    private String player;


    public String getMessage() {
        return message;
    }

    @Override
    public String getPlayer() {
        return player;
    }
}
