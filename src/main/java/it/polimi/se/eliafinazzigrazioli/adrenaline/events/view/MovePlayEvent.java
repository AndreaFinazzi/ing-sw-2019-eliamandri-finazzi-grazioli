package it.polimi.se.eliafinazzigrazioli.adrenaline.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Coordinates;

import java.util.List;

public class MovePlayEvent implements ViewEventInterface {
    private String player;
    private List<Coordinates> path;

    @Override
    public String getPlayer() {
        return player;
    }

    public List<Coordinates> getPath() {
        return path;
    }
}
