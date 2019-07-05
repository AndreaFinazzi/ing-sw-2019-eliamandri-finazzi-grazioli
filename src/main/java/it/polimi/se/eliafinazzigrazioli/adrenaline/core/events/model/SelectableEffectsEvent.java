package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

import java.util.List;

/**
 * The type Selectable effects event.
 */
public class SelectableEffectsEvent extends AbstractModelEvent {

    private String weapon;
    private List<String> callableEffects;

    /**
     * Instantiates a new Selectable effects event.
     *
     * @param player the player
     * @param weapon the weapon
     * @param callableEffects the callable effects
     */
    public SelectableEffectsEvent(Player player, String weapon, List<String> callableEffects) {
        super(true, true, player);
        this.weapon = weapon;
        this.callableEffects = callableEffects;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets callable effects.
     *
     * @return the callable effects
     */
    public List<String> getCallableEffects() {
        return callableEffects;
    }

    /**
     * Gets weapon.
     *
     * @return the weapon
     */
    public String getWeapon() {
        return weapon;
    }
}
