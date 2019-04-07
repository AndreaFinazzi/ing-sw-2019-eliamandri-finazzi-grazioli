package it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.events.view.ViewEventInterface;

public class NotAllowedPlayEvent implements ControllerEventInterface{
    private String player;
    private ViewEventInterface event;

    @Override
    public String getPlayer() {
        return player;
    }

    public ViewEventInterface getEvent() {
        return event;
    }
}
