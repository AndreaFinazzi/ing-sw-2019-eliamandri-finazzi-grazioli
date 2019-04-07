package it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller;

public class PlayerDamagedEvent implements ControllerEventInterface {
    private String player;
    private String shooter;

    @Override
    public String getPlayer() {
        return player;
    }

    public String getShooter() {
        return shooter;
    }
}
