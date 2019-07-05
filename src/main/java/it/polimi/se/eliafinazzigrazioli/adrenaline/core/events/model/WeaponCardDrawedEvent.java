package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.Map;

/**
 * The type Weapon card drawed event.
 */
public class WeaponCardDrawedEvent extends AbstractModelEvent {

    private Coordinates coordinates; // to identify spawn that contains this card

    private String weaponName;

    private Map<String, String> effectsDescription;

    /**
     * Instantiates a new Weapon card drawed event.
     *
     * @param player the player
     * @param weaponCard the weapon card
     * @param coordinates the coordinates
     */
    public WeaponCardDrawedEvent(String player, WeaponCard weaponCard, Coordinates coordinates) {
        super(player);
        privateEvent = false;
        weaponName = weaponCard.getWeaponName();
        effectsDescription = weaponCard.getEffectsDescription();
        this.coordinates = coordinates;
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
     * Gets effects description.
     *
     * @return the effects description
     */
    public Map<String, String> getEffectsDescription() {
        return effectsDescription;
    }

    /**
     * Gets coordinates.
     *
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
