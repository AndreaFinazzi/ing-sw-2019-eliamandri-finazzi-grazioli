package it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.BoardSquare;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Player;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Selectable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeaponEffect {

    private List<Ammo> price;
    private ArrayList<EffectState> effectStates;
    private EffectState currentState;
    private Iterator<EffectState> stateIterator;

    private List<Selectable> intermediateSelectionList; //List used to implement composite selections, it initially contains all players
    private ArrayList<Selectable> selected;
    private List<Player> toAffect;
    private BoardSquare movementDestination;
    private List<Player> shotPlayers;
    private List<Player> movedPlayers;


    public void updateIntermediateSelectionList(List<Selectable> newSelection){
        for (Selectable element:intermediateSelectionList){
            if (!newSelection.contains(element))
                intermediateSelectionList.remove(element);
        }
    }

    public Selectable getSelected(int selectionOrder) { return selected.get(selectionOrder); }

    public BoardSquare getMovementDestination() {
        return movementDestination;
    }

    public List<Player> getToAffect() {
        return new ArrayList<Player>(toAffect);
    }

    public void addShotPlayers(List<Player> toAdd){
        shotPlayers.addAll(toAdd);
    }

    public void addMovedPlayers(List<Player> toAdd){
        movedPlayers.addAll(toAdd);
    }

    public void execute(WeaponCard invoker) {
        currentState.execute(invoker);
        currentState = stateIterator.next();
    }

}
