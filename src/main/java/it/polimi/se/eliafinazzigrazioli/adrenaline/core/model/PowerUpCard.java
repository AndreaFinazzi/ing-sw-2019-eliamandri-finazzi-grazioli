package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PowerUpCard {

    private final String id;
    private final String type;
    private final Ammo ammo;
    private final PowerUpEffect effect;
    private final String description;
    private final boolean toPay;

    public PowerUpCard(String type, Ammo ammo) {
        this.type = type;
        this.ammo = ammo;
        id = null;
        effect = null;
        description = null;
        toPay = false;
    }

    public PowerUpCard(String id, String type, Ammo ammo, PowerUpEffect effect, String description, boolean toPay) {
        this.id = id;
        this.type = type;
        this.ammo = ammo;
        this.effect = effect;
        this.description = description;
        this.toPay = toPay;
    }

    public static PowerUpCard clientCopyToServer(PowerUpCardClient powerUpCardClient) {
        String filePath = "src/main/resources/jsonFiles/powerUpsDeck/" + powerUpCardClient.getId() + ".json" ;
        String jsonString = null;
        try {
            jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Type powerUpType = new TypeToken<PowerUpCard>(){}.getType();
        return gson.fromJson(jsonString, powerUpType);
    }

    public Ammo getAmmo() {
        return ammo;
    }

    public String getType() {
        return type;
    }

    public void activate() {
        effect.activate();
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
                ", effect=" + effect +
                '}';
    }
}
