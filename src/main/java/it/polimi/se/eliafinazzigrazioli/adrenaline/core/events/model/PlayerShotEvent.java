package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.DamageMark;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

import java.util.List;

/**
 * The type Player shot event.
 */
public class PlayerShotEvent extends AbstractModelEvent {

    private String target;
    private List<DamageMark> damages;
    private List<DamageMark> marks;
    private List<DamageMark> marksDelivered;

    /**
     * Instantiates a new Player shot event.
     *
     * @param player the player
     * @param target the target
     * @param damages the damages
     * @param marks the marks
     * @param marksDelivered the marks delivered
     */
    public PlayerShotEvent(Player player, String target, List<DamageMark> damages, List<DamageMark> marks, List<DamageMark> marksDelivered) {
        super(player);
        this.target = target;
        this.damages = damages;
        this.marks = marks;
        this.marksDelivered = marksDelivered;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets target.
     *
     * @return the target
     */
    public String getTarget() {
        return target;
    }

    /**
     * Gets damages.
     *
     * @return the damages
     */
    public List<DamageMark> getDamages() {
        return damages;
    }

    /**
     * Gets marks.
     *
     * @return the marks
     */
    public List<DamageMark> getMarks() {
        return marks;
    }

    /**
     * Gets marks delivered.
     *
     * @return the marks delivered
     */
    public List<DamageMark> getMarksDelivered() {
        return marksDelivered;
    }
}
