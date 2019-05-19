package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

import java.util.List;

public class LightModel extends AbstractModelEvent {

    private List<String> playerAlreadyPresent;

    public LightModel(String player, String message) {
        super(player, message);
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {

    }

    public List<String> getPlayerAlreadyPresent() {
        return playerAlreadyPresent;
    }

    public void setPlayerAlreadyPresent(List<String> playerAlreadyPresent) {
        this.playerAlreadyPresent = playerAlreadyPresent;
    }
}
