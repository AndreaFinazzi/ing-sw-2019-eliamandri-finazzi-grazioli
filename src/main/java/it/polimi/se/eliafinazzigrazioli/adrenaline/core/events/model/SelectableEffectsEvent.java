package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

import java.util.List;

public class SelectableEffectsEvent extends AbstractModelEvent {

    private String weapon;
    private List<String> callableEffects;

    public SelectableEffectsEvent(Player player, String weapon, List<String> callableEffects) {
        super(true, true, player);
        this.weapon = weapon;
        this.callableEffects = callableEffects;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public List<String> getCallableEffects() {
        return callableEffects;
    }

    public String getWeapon() {
        return weapon;
    }
}
