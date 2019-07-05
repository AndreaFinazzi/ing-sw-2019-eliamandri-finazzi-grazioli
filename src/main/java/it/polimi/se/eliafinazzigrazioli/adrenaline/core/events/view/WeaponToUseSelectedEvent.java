package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.view;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;

/**
 * The type Weapon to use selected event.
 */
public class WeaponToUseSelectedEvent extends AbstractViewEvent {

    private String weaponName;
    private List<Coordinates> path;

    /**
     * Instantiates a new Weapon to use selected event.
     *
     * @param clientID the client id
     * @param player the player
     * @param weaponName the weapon name
     * @param path the path
     */
    public WeaponToUseSelectedEvent(int clientID, String player, String weaponName, List<Coordinates> path) {
        super(clientID, player);
        this.weaponName = weaponName;
        this.path = path;
    }

    @Override
    public void handle(ViewEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    /**
     * Gets weapon name.
     *
     * @return the weapon name
     */
    public String getWeaponName() {
        return weaponName;
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public List<Coordinates> getPath() {
        return path;
    }
}
