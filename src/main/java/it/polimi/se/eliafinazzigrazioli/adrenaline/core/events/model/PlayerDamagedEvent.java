package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.DamageMark;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

import java.util.List;

public class PlayerDamagedEvent extends AbstractModelEvent {

    private String shooter;
    private String target;
    private List<DamageMark> damages;
    private List<DamageMark> marks;

    public PlayerDamagedEvent(Player player, String shooter, String target, List<DamageMark> damages, List<DamageMark> marks) {
        super(player);
        this.shooter = shooter;
        this.target = target;
        this.damages = damages;
        this.marks = marks;
    }

    public String getShooter() {
        return shooter;
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
