package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.DamageMark;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

/**
 * The type Skull removal event.
 */
public class SkullRemovalEvent extends AbstractModelEvent {

    /**
     * The Damage mark.
     */
    DamageMark damageMark;
    /**
     * The Dead player.
     */
    String deadPlayer;
    /**
     * The Overkill.
     */
    boolean overkill;
    /**
     * The Track full.
     */
    boolean trackFull;

    /**
     * Instantiates a new Skull removal event.
     *
     * @param player the player
     * @param damageMark the damage mark
     * @param deadPlayer the dead player
     * @param overkill the overkill
     * @param trackFull the track full
     */
    public SkullRemovalEvent(Player player, DamageMark damageMark, String deadPlayer, boolean overkill, boolean trackFull) {
        super(player);
        this.damageMark = damageMark;
        this.deadPlayer = deadPlayer;
        this.overkill = overkill;
        this.trackFull = trackFull;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets damage mark.
     *
     * @return the damage mark
     */
    public DamageMark getDamageMark() {
        return damageMark;
    }

    /**
     * Gets dead player.
     *
     * @return the dead player
     */
    public String getDeadPlayer() {
        return deadPlayer;
    }

    /**
     * Is overkill boolean.
     *
     * @return the boolean
     */
    public boolean isOverkill() {
        return overkill;
    }
}
