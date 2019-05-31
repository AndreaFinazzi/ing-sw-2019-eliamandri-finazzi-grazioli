package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

public class PlayerMovedByWeaponEvent extends AbstractModelEvent {

    private String movingWeapon;
    private String movedPlayer;
    private Coordinates finalPosition;

    public PlayerMovedByWeaponEvent(String player, String movingWeapon, String movedPlayer) {
        super(player);
        this.movingWeapon = movingWeapon;
        this.movedPlayer = movedPlayer;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}
