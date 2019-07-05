package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

/**
 * The type Player moved by weapon event.
 */
public class PlayerMovedByWeaponEvent extends AbstractModelEvent {

    private String movingWeapon;
    private String movedPlayer;
    private Coordinates finalPosition;

    /**
     * Instantiates a new Player moved by weapon event.
     *
     * @param player the player
     * @param movingWeapon the moving weapon
     * @param movedPlayer the moved player
     * @param finalPosition the final position
     */
    public PlayerMovedByWeaponEvent(Player player, String movingWeapon, String movedPlayer, Coordinates finalPosition) {
        super(player);
        this.movingWeapon = movingWeapon;
        this.movedPlayer = movedPlayer;
        this.finalPosition = finalPosition;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets moving weapon.
     *
     * @return the moving weapon
     */
    public String getMovingWeapon() {
        return movingWeapon;
    }

    /**
     * Gets moved player.
     *
     * @return the moved player
     */
    public String getMovedPlayer() {
        return movedPlayer;
    }

    /**
     * Gets final position.
     *
     * @return the final position
     */
    public Coordinates getFinalPosition() {
        return finalPosition;
    }
}
