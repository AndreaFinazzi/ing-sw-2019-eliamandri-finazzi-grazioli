package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import java.util.List;

public class Player {
    private String playerNickname;
    private BoardSquare position;
    private PlayerBoard playerBoard;
    private boolean suspended;
    private boolean connected;
    private List<WeaponCard> weaponsDeck;
    private List<PowerUpCard> powerUpsDeck;

    //TODO Costruttore

    public List<WeaponCard> getWeapons(){
        //TODO
    }

    public WeaponCard removeWeapon(int index){
        //TODO
    }

    public addWeapon(WeaponCard weapon){
        //TODO
    }

    public List<PowerUpcard> getPowerUps(){
        //TODO
    }

    public addPowerUp(PowerUpCard powerUpCard){
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


}
