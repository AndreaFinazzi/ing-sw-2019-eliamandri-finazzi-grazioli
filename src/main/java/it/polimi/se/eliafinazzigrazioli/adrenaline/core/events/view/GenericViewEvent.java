package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

public class GenericViewEvent extends AbstractViewEvent {

    public GenericViewEvent(String player, String message) {
        super(player);
        super.setMessage(message);
    }

    @Override
    public String getPlayer() {
        return super.getPlayer();
    }

    @Override
    public void setPlayer(String player) {
        super.setPlayer(player);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void setMessage(String message) {
        super.setMessage(message);
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
