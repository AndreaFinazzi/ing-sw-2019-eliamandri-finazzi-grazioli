package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.BeginTurnEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.EndTurnEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.List;

public class Player implements Selectable {
    private String playerNickname;
    private BoardSquare position;
    private PlayerBoard playerBoard;
    private DamageMark damageMarkDelivered; //Is the type of damage placeholder used by the player
    private boolean suspended;
    private boolean connected;
    private boolean placed;
    private List<WeaponCard> weapons;
    private List<PowerUpCard> powerUps;

    // Define additional methods, granting access to players list by nickname (unique key)
    // These are implemented in anonymous class in Match
    public abstract static class AbstractPlayerList extends ArrayList<Player> {
        public abstract boolean contains(String nickname);

        public abstract Player get(String nickname);

        public abstract Player add(String nickname);

        public abstract Player remove(String nickname);
    }

    public Player(String playerNickname) {
        this.playerNickname = playerNickname;
        weapons = new ArrayList<>();
        powerUps = new ArrayList<>();
    }

    @Override
    public List<Selectable> getVisible(SelectableType selType, boolean notVisible, GameBoard gameBoard) {
        return position.getVisible(selType, notVisible, gameBoard);
    }

    @Override
    public List<Selectable> getByDistance(SelectableType selType, int maxDistance, int minDistance, GameBoard gameBoard){
        return position.getByDistance(selType, maxDistance, minDistance ,gameBoard);
    }

    @Override
    public List<Selectable> getByRoom(SelectableType selType, GameBoard gameBoard, List<Player> p) {
        return position.getByRoom(selType ,new GameBoard(MapType.ONE), p);
    }

    @Override
    public List<Selectable> getOnCardinal(SelectableType selType, GameBoard gameBoard) {
        return position.getOnCardinal(selType, gameBoard);
    }

    public DamageMark getDamageMarkDelivered() {
        return damageMarkDelivered;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public List<WeaponCard> getWeapons(){
        return weapons;
    }

    //TODO define type exception
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

    public WeaponCard getWeaponByName(String weaponName){
        for (WeaponCard weaponCard: weapons) {
            if (weaponName.equals(weaponCard.getWeaponName()))
                return weaponCard;
        }
        return null;
    }

    public String getPlayerNickname() {
        return playerNickname;
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

    public void setPlayerBoard(PlayerBoard playerBoard) {
        this.playerBoard = playerBoard;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerNickname='" + playerNickname + '\'' +
                ", position=" + position +
                ", playerBoard=" + playerBoard +
                ", suspended=" + suspended +
                ", connected=" + connected +
                ", placed=" + placed +
                ", weapons=" + weapons +
                ", powerUps=" + powerUps +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player player = (Player) obj;
            return player.getPlayerNickname().equals(this.playerNickname);
        }
        return false;
    }

    public AbstractModelEvent createBeginTurnEvent() {
        return new BeginTurnEvent(playerNickname);
    }

    //TODO should be transformed in initTurn method
    public EndTurnEvent createEndTurnEvent() {
        return new EndTurnEvent(playerNickname);
    }

}
