package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponEffect;

import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class PowerUpCard {

    private final String id;
    private final String type;
    private final Ammo ammo;
    private final String description;
    private final boolean toPay;

    public PowerUpCard(String type, Ammo ammo) {
        this.type = type;
        this.ammo = ammo;
        id = null;
        description = null;
        toPay = false;
    }

    public PowerUpCard(String id, String type, Ammo ammo, WeaponEffect effect, String description, boolean toPay) {
        this.id = id;
        this.type = type;
        this.ammo = ammo;
        this.description = description;
        this.toPay = toPay;
    }

    public static PowerUpCard clientCopyToServer(PowerUpCardClient powerUpCardClient) {
        String filePath = "/jsonFiles/powerUpsDeck/" + powerUpCardClient.getId() + ".json";
        InputStreamReader fileInputStreamReader = new InputStreamReader(PowerUpCard.class.getResourceAsStream(filePath));
        Gson gson = new Gson();
        Type powerUpType = new TypeToken<PowerUpCard>(){}.getType();
        return gson.fromJson(fileInputStreamReader, powerUpType);
    }

    public Ammo getAmmo() {
        return ammo;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public boolean isToPay() {
        return toPay;
    }

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
