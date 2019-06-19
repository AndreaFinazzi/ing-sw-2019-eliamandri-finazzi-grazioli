package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;

public class WeaponCollectionEvent extends AbstractViewEvent {

    private List<Coordinates> path;
    private String weaponCollected;
    private String weaponDropped;
    private List<PowerUpCardClient> powerUpsToPay;

    public WeaponCollectionEvent(int clientID, String player, List<Coordinates> path, WeaponCardClient weaponCollected, WeaponCardClient weaponDropped, List<PowerUpCardClient> powerUpsToPay) {
        super(clientID, player);
        this.path = path;
        this.weaponCollected = weaponCollected.getWeaponName();
        if (weaponDropped != null)
            this.weaponDropped = weaponDropped.getWeaponName();
        else
            this.weaponDropped = null;
        this.powerUpsToPay = powerUpsToPay;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public List<Coordinates> getPath() {
        return path;
    }

    public String getWeaponCollected() {
        return weaponCollected;
    }

    public String getWeaponDropped() {
        return weaponDropped;
    }

    public List<PowerUpCardClient> getPowerUpsToPay() {
        return powerUpsToPay;
    }
}
