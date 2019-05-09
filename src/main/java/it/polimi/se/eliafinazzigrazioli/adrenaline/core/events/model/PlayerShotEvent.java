package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.EventListenerInterface;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;

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
