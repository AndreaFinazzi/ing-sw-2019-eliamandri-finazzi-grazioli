package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class PlayerConnectedEvent extends AbstractViewEvent {

    private int chosenMap;

    public PlayerConnectedEvent(String player) {
        super(player);
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public int getChosenMap() {
        return chosenMap;
    }

    public void setChosenMap(int chosenMap) {
        this.chosenMap = chosenMap;
    }
}

