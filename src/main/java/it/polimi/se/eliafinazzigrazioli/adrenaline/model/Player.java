package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private static final int maxWeapons = 3;
    private static final int maxPowerUps = 3;
    private String playerNickname;
    private BoardSquare position;
    private PlayerBoard playerBoard;
    private boolean suspended;
    private boolean connected;
    private boolean placed;
    private List<WeaponCard> weapons;
    private List<PowerUpCard> powerUps;


    public Player(String playerNickname) {
        this.playerNickname = playerNickname;
        connected = true;
        weapons = new ArrayList<> ();
        powerUps = new ArrayList<> ();
    }

    public List<WeaponCard> getWeapons(){
        return weapons;
    }
    //TODO define type excpetion
    public WeaponCard removeWeapon(WeaponCard weapon) throws Exception{
        int index = weapons.indexOf (weapon);
        if (index == -1)
            throw new Exception ();
        WeaponCard tempWeapon = weapons.get (index);
        weapons.remove (index);
        return tempWeapon;
    }
    //TODO define type excpetion
    public void addWeapon(WeaponCard weapon) throws Exception{
        if (weapons.size () == maxWeapons)
            throw new Exception ();
        weapons.add (weapon);
    }

    public List<PowerUpCard> getPowerUps(){
        return powerUps;
    }

    //TODO define type excpetion
    public void addPowerUp(PowerUpCard powerUpCard) throws Exception{
        if (powerUps.size () == maxPowerUps)
            throw new Exception ();
        powerUps.add (powerUpCard);
    }

    public String getPlayerNickname() {
        return playerNickname;
    }

    public BoardSquare getPosition() {
        return position;
    }

    public void setPosition(BoardSquare position) {
        this.position = position;
    }

    public boolean isConnected() {
        return connected;
    }

    public void connect(){
        connected = true;
    }

    public void disconnect(){
        connected = false;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void suspend(){
        suspended = true;
    }

    public void unsuspend(){
        suspended = false;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }
}
