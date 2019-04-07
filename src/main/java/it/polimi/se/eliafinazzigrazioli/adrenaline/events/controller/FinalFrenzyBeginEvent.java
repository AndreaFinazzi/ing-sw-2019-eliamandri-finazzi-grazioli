package it.polimi.se.eliafinazzigrazioli.adrenaline.events.controller;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.PlayerBoard;

public class FinalFrenzyBeginEvent implements ControllerEventInterface{
    private String player;
    private PlayerBoard frenzyBoard;

    @Override
    public String getPlayer() {
        return player;
    }

    public PlayerBoard getFrenzyBoard() {
        return frenzyBoard;
    }
}
