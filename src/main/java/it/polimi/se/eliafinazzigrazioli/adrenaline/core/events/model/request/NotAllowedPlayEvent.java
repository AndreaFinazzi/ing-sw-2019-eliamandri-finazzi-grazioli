package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view.AbstractViewEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

public class NotAllowedPlayEvent extends AbstractModelEvent {

    private AbstractViewEvent event;

    public NotAllowedPlayEvent(String player) {
        super(true, player);
    }

    public NotAllowedPlayEvent(Player player) {
        super(true, player.getPlayerNickname(), player.getClientID());
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public AbstractViewEvent getEvent() {
        return event;
    }
}
