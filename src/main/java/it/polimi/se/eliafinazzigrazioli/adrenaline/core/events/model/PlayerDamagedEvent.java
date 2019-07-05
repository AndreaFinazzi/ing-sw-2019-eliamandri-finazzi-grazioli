package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.DamageMark;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

import java.util.List;

/**
 * The type Player damaged event.
 */
public class PlayerDamagedEvent extends AbstractModelEvent {

    private String shooter;
    private String target;
    private List<DamageMark> damages;
    private List<DamageMark> marks;

    /**
     * Instantiates a new Player damaged event.
     *
     * @param player the player
     * @param shooter the shooter
     * @param target the target
     * @param damages the damages
     * @param marks the marks
     */
    public PlayerDamagedEvent(Player player, String shooter, String target, List<DamageMark> damages, List<DamageMark> marks) {
        super(player);
        this.shooter = shooter;
        this.target = target;
        this.damages = damages;
        this.marks = marks;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets shooter.
     *
     * @return the shooter
     */
    public String getShooter() {
        return shooter;
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
}
