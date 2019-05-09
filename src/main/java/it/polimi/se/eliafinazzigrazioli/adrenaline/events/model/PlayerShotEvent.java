package it.polimi.se.eliafinazzigrazioli.adrenaline.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.controller.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.events.HandlerNotImplementedException;

public class PlayerShotEvent extends AbstractModelEvent {

    private String shotPlayer;
    private String shootingWeapon;

    public PlayerShotEvent(String player, String shotPlayer, String shootingWeapon) {
        super(player);
        this.shotPlayer = shotPlayer;
        this.shootingWeapon = shootingWeapon;
    }

    @Override
    public void handle(EventListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

}
