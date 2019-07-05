package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.WeaponFileNotFoundException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Player;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.*;

//todo check room selection logic for furnace and complete json
//todo study the last thing wrote in the notes, that's how to potentially split the moves. at the moment it's split into to single move identical effects

public class WeaponCard extends Card {

    /*
     * This block of attributes characterizes the card and it's the part
     * saved in the json file.
     */
    private String weaponName;
    private String id;
    private Ammo cardColor;
    private List<Ammo> loader;
    private List<WeaponEffect> effects;
    private List<String> callableEffects;
    private String notes;

    /*
     * State attributes.
     */
    private WeaponEffect activeEffect = null;
    private boolean loaded = true;


    //TODO factory method for cards
    public static WeaponCard jsonParser(String cardName) throws WeaponFileNotFoundException {
        String filePath = "/jsonFiles/weaponCardJsons/" + cardName;
        String jsonString;
        InputStreamReader fileInputStreamReader = new InputStreamReader(WeaponCard.class.getResourceAsStream(filePath));
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(EffectState.effectStateRuntimeTypeAdapterFactory)
                .create();
        Type weaponCardType = new TypeToken<WeaponCard>(){}.getType();
        return gson.fromJson(fileInputStreamReader, weaponCardType);
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

    public String getId() {
        return id;
    }

    public WeaponEffect getActiveEffect() {
        return activeEffect;
    }

    public List<Ammo> getLoader() {
        return loader;
    }

    public List<WeaponEffect> getEffects() {
        return effects;
    }

    public String getNotes() {
        return notes;
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

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
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

    public Map<String, String> getEffectsDescription(){
        Map<String, String> effectsDescription = new HashMap<>();
        for(WeaponEffect weaponEffect : effects){
            effectsDescription.put(weaponEffect.getEffectName(), weaponEffect.getEffectDescription());
        }
        return effectsDescription;
    }

    public List<String> getCallableEffects(){
        return callableEffects;
    }

    public Ammo getCardColor() {
        return cardColor;
    }


    @Override
    public String toString() {
        return "Name: " + weaponName + "\nColor: " + cardColor +"\nLoader: " + loader.toString() + "\nEffects: " + effects ;
    }
}