package it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.events.HandlerNotImplementedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PowerUpCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class WeaponCollectedEvent extends AbstractModelEvent {

    private String collectedWeapon;
    private String dropOffWeapon;
    private Coordinates collectionSpawnPoint;
    private List<PowerUpCardClient> powerUpsSpent;
    private List<Ammo> ammosSpent;
    private boolean handFull;

    public WeaponCollectedEvent(Player player, WeaponCard collectedWeapon, WeaponCard dropOffWeapon, Coordinates collectionSpawnPoint, List<PowerUpCard> powerUpsSpent, List<Ammo> ammosSpent, boolean handFull) {
        super(player);
        this.collectedWeapon = collectedWeapon.getWeaponName();
        if (dropOffWeapon != null)
            this.dropOffWeapon = dropOffWeapon.getWeaponName();
        else
            this.dropOffWeapon = null;
        this.collectionSpawnPoint = collectionSpawnPoint;
        this.powerUpsSpent = new ArrayList<>();
        for (PowerUpCard powerUpCard: powerUpsSpent)
            this.powerUpsSpent.add(new PowerUpCardClient(powerUpCard));
        this.ammosSpent = ammosSpent;
        this.handFull = handFull;
    }

    @Override
    public void handle(ModelEventsListenerInterface listener) throws HandlerNotImplementedException {
        listener.handleEvent(this);
    }

    public String getCollectedWeapon() {
        return collectedWeapon;
    }

    public String getDropOffWeapon() {
        return dropOffWeapon;
    }

    public Coordinates getCollectionSpawnPoint() {
        return collectionSpawnPoint;
    }

    public List<PowerUpCardClient> getPowerUpsSpent() {
        return powerUpsSpent;
    }

    public List<Ammo> getAmmosSpent() {
        return ammosSpent;
    }

    public boolean isHandFull() {
        return handFull;
    }
}
