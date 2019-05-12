package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.WeaponFileNotFoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.DamageMark;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.RuntimeTypeAdapterFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeaponCard extends Card {

    /*
     * This block of attributes characterizes the card and it's the part
     * saved in the json file.
     */
    private String weaponName;
    private Ammo cardColor;
    private List<Ammo> loader;
    private List<WeaponEffect> effects;
    private List<String> callableEffects;

    /*
     * State attributes.
     */
    private WeaponEffect activeEffect = null;
    private boolean loaded = true;


    //TODO factory method for cards
    public static WeaponCard jsonParser(String cardName) throws WeaponFileNotFoundException {
        String filePath = "src\\main\\resources\\weaponCardJsons\\" + cardName + ".json";
        String jsonString;
        try {
            jsonString = new String(Files.readAllBytes(Paths.get(filePath)));
        }
        catch (IOException e){
            throw new WeaponFileNotFoundException();
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(EffectState.effectStateRuntimeTypeAdapterFactory)
                .create();
        Type weaponCardType = new TypeToken<WeaponCard>(){}.getType();
        return gson.fromJson(jsonString, weaponCardType);
    }

    public WeaponCard(WeaponEffect activeEffect) {
        this.activeEffect = activeEffect;
    }

    public WeaponCard(){
        weaponName = "Lock Refile";
        cardColor = Ammo.BLUE;
        loader = new ArrayList<>(Arrays.asList(Ammo.BLUE));
        effects = null;
    }

    public String getWeaponName() {
        return weaponName;
    }

    public WeaponEffect getActiveEffect() {
        return activeEffect;
    }

    public void setActiveEffect(String effectName){
        activeEffect = getEffectByName(effectName);
    }

    public List<AbstractModelEvent> executeStep(GameBoard gameBoard, Player currentPlayer) {
        return activeEffect.execute(this, gameBoard, currentPlayer);
    }

    public void playCard() {        // Card's attributes initialization when card is selected
        //TODO
    }

    public void reload() {
        //TODO
    }

    private void unload() {
        //TODO
    }

    public boolean isLoaded(){
        return loaded;
    }

    public WeaponEffect getEffectByName(String effectName){
        for (WeaponEffect effect: effects){
            if (effect.getEffectName().equals(effectName))
                return effect;
        }
        return null;
    }

    public void initialize(){
        activeEffect = null;
        for (WeaponEffect effect: effects){
            effect.initialize();
        }
    }

    public List<String> getCallableEffects(){
        return callableEffects;
    }

    @Override
    public String toString() {
        return "Name: " + weaponName + "\nColor: " + cardColor +"\nLoader: " + loader.toString() + "\nEffects: " + effects ;
    }

}