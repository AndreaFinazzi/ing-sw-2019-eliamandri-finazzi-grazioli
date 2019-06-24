package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

public class PlayerMovedByWeaponEvent extends AbstractModelEvent {

    private String movingWeapon;
    private String movedPlayer;
    private Coordinates finalPosition;

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

    public String getMovingWeapon() {
        return movingWeapon;
    }

    public String getMovedPlayer() {
        return movedPlayer;
    }

    public Coordinates getFinalPosition() {
        return finalPosition;
    }
}
