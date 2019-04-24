package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Rules;

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


    public Player(String playerNickname) {
        this.playerNickname = playerNickname;
        connected = true;
        weapons = new ArrayList<>();
        powerUps = new ArrayList<>();
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public List<WeaponCard> getWeapons(){
        return weapons;
    }

    //TODO define type excpetion
    public WeaponCard removeWeapon(WeaponCard weapon) throws Exception {
        int index = weapons.indexOf(weapon);
        if (index == -1)
            throw new Exception();
        return weapons.remove(index);
    }

    //TODO define type excpetion
    public void addWeapon(WeaponCard weapon) throws Exception {
        if (weapons.size() == Rules.PLAYER_CARDS_MAX_WEAPONS)
            throw new Exception();
        weapons.add(weapon);
    }

    public List<PowerUpCard> getPowerUps() {
        return powerUps;
    }

    //TODO define type excpetion
    public void addPowerUp(PowerUpCard powerUpCard) throws Exception {
        if (powerUps.size() == Rules.PLAYER_CARDS_MAX_POWER_UPS)
            throw new Exception();
        powerUps.add(powerUpCard);
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

    public void connect() {
        connected = true;
    }

    public void disconnect() {
        connected = false;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void suspend() {
        suspended = true;
    }

    public void unsuspend() {
        suspended = false;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }
}
