package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.DamageMark;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

import java.util.List;

public class PlayerShotEvent extends AbstractModelEvent {

    private String target;
    private List<DamageMark> damages;
    private List<DamageMark> marks;

    public PlayerShotEvent(Player player, String target, List<DamageMark> damages, List<DamageMark> marks) {
        super(player);
        this.target = target;
        this.damages = damages;
        this.marks = marks;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public String getTarget() {
        return target;
    }

    public List<DamageMark> getDamages() {
        return damages;
    }

    public List<DamageMark> getMarks() {
        return marks;
    }
}
