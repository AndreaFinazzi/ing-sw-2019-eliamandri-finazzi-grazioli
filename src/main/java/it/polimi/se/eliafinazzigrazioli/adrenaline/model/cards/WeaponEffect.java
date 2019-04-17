package it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.BoardSquare;
import it.polimi.se.eliafinazzigrazioli.adrenaline.model.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeaponEffect {

    private List<Ammo> price;
    private ArrayList<EffectState> effectStates;
    private EffectState currentState;
    private Iterator<EffectState> stateIterator;
    private ArrayList<Player> selectedPlayers;
    private ArrayList<BoardSquare> selectedSquares;
    private List<Player> toAffect;
    private BoardSquare movementDestination;
    private List<Player> shotPlayers;
    private List<Player> movedPlayers;

    public BoardSquare getMovementDestination() {
        return movementDestination;
    }

    public List<Player> getToAffect() {
        return new ArrayList<Player>(toAffect);
    }

    public ArrayList<Player> getSelectedPlayers() {
        return new ArrayList<Player>(selectedPlayers);
    }

    public ArrayList<BoardSquare> getSelectedSquares() {
        return  new ArrayList<BoardSquare>(selectedSquares);
    }

    public void addSelectedPlayers(ArrayList<Player> selected){
        selectedPlayers.addAll(selected);
    }

    public void addSelectedSquares(ArrayList<BoardSquare> selected){
        selectedSquares.addAll(selected);
    }

    public void addShotPlayers(List<Player> toAdd){
        shotPlayers.addAll(toAdd);
    }

    public void addMovedPlayers(List<Player> toAdd){
        movedPlayers.addAll(toAdd);
    }



    public void execute(WeaponCard invoker){
        currentState.execute(invoker);
        currentState = stateIterator.next();
    }

}
