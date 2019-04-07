package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

public class CollectPlayEvent implements ViewEventInterface {
    private String player;

    @Override
    public String getPlayer() {
        return player;
    }
}
