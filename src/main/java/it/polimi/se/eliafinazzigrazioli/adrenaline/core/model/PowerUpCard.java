package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;

import java.io.InputStreamReader;
import java.lang.reflect.Type;

/**
 * The type Power up card.
 */
public class PowerUpCard {

    private final String id;
    private final String type;
    private final Ammo ammo;
    private final String description;
    private final boolean toPay;

    /**
     * Instantiates a new Power up card.
     *
     * @param type the type
     * @param ammo the ammo
     */
    public PowerUpCard(String type, Ammo ammo) {
        this.type = type;
        this.ammo = ammo;
        id = null;
        description = null;
        toPay = false;
    }

    /**
     * Instantiates a new Power up card.
     *
     * @param id the id
     * @param type the type
     * @param ammo the ammo
     * @param effect the effect
     * @param description the description
     * @param toPay the to pay
     */
    public PowerUpCard(String id, String type, Ammo ammo, WeaponEffect effect, String description, boolean toPay) {
        this.id = id;
        this.type = type;
        this.ammo = ammo;
        this.description = description;
        this.toPay = toPay;
    }

    /**
     * Client copy to server power up card.
     *
     * @param powerUpCardClient the power up card client
     * @return the power up card
     */
    public static PowerUpCard clientCopyToServer(PowerUpCardClient powerUpCardClient) {
        String filePath = "/jsonFiles/powerUpsDeck/" + powerUpCardClient.getId() + ".json";
        InputStreamReader fileInputStreamReader = new InputStreamReader(PowerUpCard.class.getResourceAsStream(filePath));
        Gson gson = new Gson();
        Type powerUpType = new TypeToken<PowerUpCard>(){}.getType();
        return gson.fromJson(fileInputStreamReader, powerUpType);
    }

    /**
     * Gets ammo.
     *
     * @return the ammo
     */
    public Ammo getAmmo() {
        return ammo;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Is to pay boolean.
     *
     * @return the boolean
     */
    public boolean isToPay() {
        return toPay;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "PowerUpCard{" +
                "ammo=" + ammo +
                '}';
    }
}
