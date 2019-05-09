package it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.DamageMark;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.GameBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Player;

import java.util.ArrayList;
import java.util.List;

public class WeaponCard extends Card {

    private String weaponName;
    private Ammo cardColor;
    private List<Ammo> loader;
    private boolean loaded;
    private ArrayList<WeaponEffect> effects;
    private WeaponEffect activeEffect;
    private DamageMark deliveredMark;       //to be set when the card is assigned to a player, avoids the need for a reference to the current player
    private List<WeaponEffect> callableEffects;


    public WeaponCard(WeaponEffect activeEffect) {
        this.activeEffect = activeEffect;
    }

    public WeaponCard(){
        //TODO to be built from json file
    }


    public WeaponEffect getActiveEffect() {
        return activeEffect;
    }

    public DamageMark getDeliveredMark() {
        return deliveredMark;
    }

    public void setDeliveredMark(DamageMark deliveredMark) {
        this.deliveredMark = deliveredMark;
    }

    public void executeStep(GameBoard gameBoard, Player currentPlayer) {
        activeEffect.execute(this, gameBoard, currentPlayer);
    }

    public void playCard() {
        //TODO
    }

    public void reload() {
        //TODO
    }

    private void unload() {
        //TODO
    }

    public List<WeaponEffect> getCallableEffects(){
        return callableEffects;
    }

}