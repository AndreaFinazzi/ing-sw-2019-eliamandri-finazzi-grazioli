package it.polimi.se.eliafinazzigrazioli.adrenaline.events.model;

public class PlayerUpdateEvent implements ModelEventInterface{
    private String player;

    @Override
    public String getPlayer() {
        return player;
    }
}
