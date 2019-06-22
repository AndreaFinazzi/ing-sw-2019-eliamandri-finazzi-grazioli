package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;

public class WeaponToUseSelectedEvent extends AbstractViewEvent {

    private String weaponName;
    private List<Coordinates> path;

    public WeaponToUseSelectedEvent(int clientID, String player, String weaponName, List<Coordinates> path) {
        super(clientID, player);
        this.weaponName = weaponName;
        this.path = path;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public String getWeaponName() {
        return weaponName;
    }

    public List<Coordinates> getPath() {
        return path;
    }
}
