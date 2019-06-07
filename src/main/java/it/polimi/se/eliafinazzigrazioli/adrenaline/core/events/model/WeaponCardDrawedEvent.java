package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Room;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.Card;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.List;
import java.util.Map;

public class WeaponCardDrawedEvent extends AbstractModelEvent {

    private Coordinates coordinates; // to identify spawn that contains this card

    private String weaponName;

    private Map<String, String> effectsDescription;

    public WeaponCardDrawedEvent(String player, WeaponCard weaponCard, Coordinates coordinates) {
        super(player);
        privateEvent = false;
        weaponName = weaponCard.getWeaponName();
        effectsDescription = weaponCard.getEffectsDescription();
        this.coordinates = coordinates;
    }


    public String getWeaponName() {
        return weaponName;
    }

    public Map<String, String> getEffectsDescription() {
        return effectsDescription;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }
}
