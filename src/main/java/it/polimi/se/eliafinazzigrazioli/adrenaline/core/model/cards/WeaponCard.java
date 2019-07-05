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

/**
 * The type Weapon card.
 */
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


    /**
     * Json parser weapon card.
     *
     * @param cardName the card name
     * @return the weapon card
     * @throws WeaponFileNotFoundException the weapon file not found exception
     */
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

    /**
     * Instantiates a new Weapon card.
     *
     * @param activeEffect the active effect
     */
    public WeaponCard(WeaponEffect activeEffect) {
        this.activeEffect = activeEffect;
    }

    /**
     * Instantiates a new Weapon card.
     */
    public WeaponCard(){
        weaponName = "Lock Refile";
        cardColor = Ammo.BLUE;
        loader = new ArrayList<>(Arrays.asList(Ammo.BLUE));
        effects = null;
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
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets active effect.
     *
     * @return the active effect
     */
    public WeaponEffect getActiveEffect() {
        return activeEffect;
    }

    /**
     * Gets loader.
     *
     * @return the loader
     */
    public List<Ammo> getLoader() {
        return loader;
    }

    /**
     * Gets effects.
     *
     * @return the effects
     */
    public List<WeaponEffect> getEffects() {
        return effects;
    }

    /**
     * Gets notes.
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Set active effect.
     *
     * @param effectName the effect name
     */
    public void setActiveEffect(String effectName){
        activeEffect = getEffectByName(effectName);
    }

    /**
     * Execute step list.
     *
     * @param gameBoard the game board
     * @param currentPlayer the current player
     * @return the list
     */
    public List<AbstractModelEvent> executeStep(GameBoard gameBoard, Player currentPlayer) {
        return activeEffect.execute(this, gameBoard, currentPlayer);
    }

    public void playCard() {        // Card's attributes initialization when card is selected
        //TODO
    }

    /**
     * Reload.
     */
    public void reload() {
        //TODO
    }

    private void unload() {
        //TODO
    }

    /**
     * Is loaded boolean.
     *
     * @return the boolean
     */
    public boolean isLoaded(){
        return loaded;
    }

    /**
     * Sets loaded.
     *
     * @param loaded the loaded
     */
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    /**
     * Get effect by name weapon effect.
     *
     * @param effectName the effect name
     * @return the weapon effect
     */
    public WeaponEffect getEffectByName(String effectName){
        for (WeaponEffect effect: effects){
            if (effect.getEffectName().equals(effectName))
                return effect;
        }
        return null;
    }

    /**
     * Initialize.
     */
    public void initialize(){
        activeEffect = null;
        for (WeaponEffect effect: effects){
            effect.initialize();
        }
    }

    /**
     * Get effects description map.
     *
     * @return the map
     */
    public Map<String, String> getEffectsDescription(){
        Map<String, String> effectsDescription = new HashMap<>();
        for(WeaponEffect weaponEffect : effects){
            effectsDescription.put(weaponEffect.getEffectName(), weaponEffect.getEffectDescription());
        }
        return effectsDescription;
    }

    /**
     * Get callable effects list.
     *
     * @return the list
     */
    public List<String> getCallableEffects(){
        return callableEffects;
    }

    /**
     * Gets card color.
     *
     * @return the card color
     */
    public Ammo getCardColor() {
        return cardColor;
    }


    @Override
    public String toString() {
        return "Name: " + weaponName + "\nColor: " + cardColor +"\nLoader: " + loader.toString() + "\nEffects: " + effects ;
    }
}