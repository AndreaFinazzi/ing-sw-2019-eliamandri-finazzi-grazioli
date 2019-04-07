package it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Selectable;

import java.util.ArrayList;
import java.util.List;

public class WeaponCard extends Card{

    private Ammo cardColor;
    private List<Ammo> loader;
    private boolean loaded;
    private List<Selectable> selectedElements;
    private ArrayList<WeaponEffect> effects;
    private WeaponEffect activeEffect;

    public void executeStep(){

        activeEffect.execute(this);
    }

    public void playCard(){
        //TODO
    }

    public void reload(){
        //TODO
    }

    private void unload(){
        //TODO
    }

}
