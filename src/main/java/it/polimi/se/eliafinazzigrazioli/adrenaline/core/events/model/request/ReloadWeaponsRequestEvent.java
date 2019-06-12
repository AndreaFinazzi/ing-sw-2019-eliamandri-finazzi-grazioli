package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.ModelEventsListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

public class ReloadWeaponsRequestEvent extends AbstractModelEvent {

    public ReloadWeaponsRequestEvent(String player) {
        super(player, true);
    }

    public ReloadWeaponsRequestEvent(Player player) {
        super(true, player.getPlayerNickname(), player.getClientID());
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
