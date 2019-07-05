package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

import java.util.List;

/**
 * The type Usable power ups event.
 */
public class UsablePowerUpsEvent extends AbstractModelEvent {

    /**
     * The Usable types.
     */
    List<String> usableTypes;
    /**
     * The Target.
     */
    String target;

    /**
     * Instantiates a new Usable power ups event.
     *
     * @param player the player
     * @param usableTypes the usable types
     * @param target the target
     */
    public UsablePowerUpsEvent(Player player, List<String> usableTypes, String target) {
        super(true, true, player);
        this.usableTypes = usableTypes;
        this.target = target;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets usable types.
     *
     * @return the usable types
     */
    public List<String> getUsableTypes() {
        return usableTypes;
    }

    /**
     * Gets target.
     *
     * @return the target
     */
    public String getTarget() {
        return target;
    }
}
