package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;

import java.util.List;

public class Player {
    private String playerNickname;
    private BoardSquare position;
    private PlayerBoard playerBoard;
    private boolean suspended;
    private boolean connected;
    private boolean placed;
    private List<WeaponCard> weapons;
    private List<PowerUpCard> powerUps;

    //TODO Costruttore

    public List<WeaponCard> getWeapons(){
        //TODO
        return null;
    }

    public WeaponCard removeWeapon(int index){
        //TODO
        return null;
    }

    public void addWeapon(WeaponCard weapon){
        //TODO
    }

    public List<PowerUpCard> getPowerUps(){
        //TODO
        return null;
    }

    public void addPowerUp(PowerUpCard powerUpCard){
        //TODO

    }

    public void connect(){
        //TODO
    }

    public void disconnect(){
        //TODO
    }

    public void suspend(){
        //TODO
    }

    public void unsuspend(){
        //TODO
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }
}
