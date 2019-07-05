package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

import java.util.List;

public class UsablePowerUpsEvent extends AbstractModelEvent {

    List<String> usableTypes;
    String target;

    public UsablePowerUpsEvent(Player player, List<String> usableTypes, String target) {
        super(true, true, player);
        this.usableTypes = usableTypes;
        this.target = target;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public List<String> getUsableTypes() {
        return usableTypes;
    }

    public String getTarget() {
        return target;
    }
}
