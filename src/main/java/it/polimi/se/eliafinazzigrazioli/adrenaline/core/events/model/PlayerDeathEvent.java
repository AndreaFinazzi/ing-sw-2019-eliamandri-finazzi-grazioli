package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

/**
 * The type Player death event.
 */
public class PlayerDeathEvent extends AbstractModelEvent {

    private String deadPlayer;

    /**
     * Instantiates a new Player death event.
     *
     * @param player the player
     * @param deadPlayer the dead player
     */
    public PlayerDeathEvent(Player player, Player deadPlayer) {
        super(player);
        this.deadPlayer = deadPlayer.getPlayerNickname();
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets dead player.
     *
     * @return the dead player
     */
    public String getDeadPlayer() {
        return deadPlayer;
    }
}
