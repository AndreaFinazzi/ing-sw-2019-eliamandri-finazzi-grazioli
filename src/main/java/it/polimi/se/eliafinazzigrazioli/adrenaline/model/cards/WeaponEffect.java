package it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Ammo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeaponEffect {

    private List<Ammo> price;
    private ArrayList<EffectState> effectStates;
    private EffectState currentState;
    private Iterator<EffectState> stateIterator;

    public void execute(WeaponCard invoker){
        currentState.execute(invoker);
        currentState = stateIterator.next();
    }


}
