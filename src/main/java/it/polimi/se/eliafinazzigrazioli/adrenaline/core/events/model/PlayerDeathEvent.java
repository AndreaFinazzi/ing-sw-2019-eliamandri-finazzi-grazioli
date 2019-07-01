package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

public class PlayerDeathEvent extends AbstractModelEvent {

    private String deadPlayer;

    public PlayerDeathEvent(Player player, Player deadPlayer) {
        super(player);
        this.deadPlayer = deadPlayer.getPlayerNickname();
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public String getDeadPlayer() {
        return deadPlayer;
    }
}
