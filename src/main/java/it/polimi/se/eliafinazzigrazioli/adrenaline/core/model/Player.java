package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.BeginTurnEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.EndTurnEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.PowerUpCollectedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.AmmoNotAvailableException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.PowerUpsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.List;

public class Player implements Selectable {
    private int clientID;


    private String playerNickname;
    private Avatar avatar;

    private int points;

    private BoardSquare position;

    private PlayerBoard playerBoard;
    private DamageMark damageMarkDelivered; //Is the type of damage placeholder used by the player
    private boolean suspended;
    private boolean connected;
    private boolean placed;
    private List<WeaponCard> weapons;
    private List<PowerUpCard> powerUps;

    private boolean finalFrenzyDoubleAction = false;

    // Define additional methods, granting access to players list by nickname (unique key)
    // These are implemented in anonymous class in Match
    public abstract static class AbstractPlayerList extends ArrayList<Player> {

        public abstract boolean contains(String nickname);

        public abstract Player get(String nickname);

        public abstract Player add(String nickname);

        public abstract Player add(int clientID, String nickname);

        public abstract Player remove(String nickname);

    }

    public Player(String playerNickname) {
        this.playerNickname = playerNickname;
        weapons = new ArrayList<>();
        powerUps = new ArrayList<>();
    }

    public Player(int clientID, String playerNickname) {
        this.playerNickname = playerNickname;
        this.clientID = clientID;
        weapons = new ArrayList<>();
        powerUps = new ArrayList<>();
        playerBoard = new PlayerBoard();
        points = 0;
    }

    public Player(String playerNickname, DamageMark damageMarkDelivered) {
        this.playerNickname = playerNickname;
        this.damageMarkDelivered = damageMarkDelivered;
        weapons = new ArrayList<>();
        powerUps = new ArrayList<>();
    }

    public void enableDoubleAction() {
        finalFrenzyDoubleAction = true;
    }

    public boolean isFinalFrenzyDoubleActionEnabled() {
        return finalFrenzyDoubleAction;
    }

    public void addPoints(int newPoints) {
        points += newPoints;
    }

    public int getPoints() {
        return points;
    }

    public void resuscitate() {
        playerBoard.resuscitate();
    }

    public boolean isDead() {
        return playerBoard.isDeath();
    }

    public boolean isOverKilled() {
        return playerBoard.isOverkill();
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    @Override
    public List<Selectable> getVisible(SelectableType selType, boolean notVisible, GameBoard gameBoard) {
        return position.getVisible(selType, notVisible, gameBoard);
    }

    @Override
    public List<Selectable> getByDistance(SelectableType selType, int maxDistance, int minDistance, GameBoard gameBoard) {
        return position.getByDistance(selType, maxDistance, minDistance, gameBoard);
    }

    @Override
    public List<Selectable> getByRoom(SelectableType selType, GameBoard gameBoard, List<Player> p) {
        return position.getByRoom(selType, new GameBoard(MapType.ONE), p);
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

    public List<WeaponCard> getWeapons() {
        return weapons;
    }

    //TODO define type exception
    public WeaponCard removeWeapon(String weapon) throws Exception {
        WeaponCard weaponToRemove = null;
        for (WeaponCard weaponCard : weapons) {
            if (weapon.equals(weaponCard.getWeaponName())) {
                weaponToRemove = weaponCard;
            }
        }

        if (weaponToRemove == null)
            throw new Exception();
        else {
            weapons.remove(weaponToRemove);
            return weaponToRemove;
        }

    }

    public boolean weaponHandIsFull() {
        return weapons.size() == Rules.PLAYER_CARDS_MAX_WEAPONS;
    }

    //TODO define type excpetion
    public void addWeapon(WeaponCard weapon) throws Exception {
        if (weapons.size() == Rules.PLAYER_CARDS_MAX_WEAPONS)
            throw new Exception();
        weapons.add(weapon);
    }

    public List<PowerUpCard> getPowerUps() {
        return new ArrayList<>(powerUps);
    }

    public boolean hasDamages() {
        return playerBoard.hasDamages();
    }

    public void removePowerUp(PowerUpCard powerUpCard) {
        powerUps.remove(powerUpCard);
    }

    public PowerUpCollectedEvent addPowerUp(PowerUpCard powerUpCard, PowerUpsDeck deck) {
        if (powerUps.size() < Rules.PLAYER_CARDS_MAX_POWER_UPS && powerUpCard != null) {
            powerUps.add(powerUpCard);
            return new PowerUpCollectedEvent(playerNickname, powerUpCard, true);
        }
        deck.discardPowerUp(powerUpCard);
        return new PowerUpCollectedEvent(playerNickname, powerUpCard, false);
    }

    public void addAmmos(List<Ammo> ammoList) {
        playerBoard.addAmmos(ammoList);
    }

    public boolean addAmmo(Ammo ammo) {
        return playerBoard.addAmmo(ammo);
    }

    /**
     * Given a color of the ammos returns the sum of the ammos and equivalent powerUps of that color.
     *
     * @param ammoType
     * @return
     */
    public int ammosNum(Ammo ammoType) {
        int numOfAmmos = playerBoard.numAmmoType(ammoType);
        for (PowerUpCard powerUpCard : powerUps) {
            if (powerUpCard.getAmmo().equals(ammoType))
                numOfAmmos++;
        }
        return numOfAmmos;
    }

    /**
     * Returns true only if the player can somehow (with ammos or with powerUps) pay the given price.
     *
     * @param price
     * @return
     */
    public boolean canSpend(List<Ammo> price, List<PowerUpCard> powerUps) {
        List<Ammo> priceCopy = new ArrayList<>(price);
        for (PowerUpCard powerUpCard : powerUps) {
            if (priceCopy.contains(powerUpCard.getAmmo()))
                priceCopy.remove(powerUpCard.getAmmo());
            else
                return false;
        }
        for (Ammo ammo : playerBoard.getAmmos())
            if (priceCopy.contains(ammo))
                priceCopy.remove(ammo);
        if (priceCopy.isEmpty())
            return true;
        else
            return false;
    }

    public List<PowerUpCard> getRealModelReferences(List<PowerUpCardClient> powerUpCardsClient) {
        List<String> powerUpIds = new ArrayList<>();
        List<PowerUpCard> realPowerUps = new ArrayList<>();
        for (PowerUpCardClient powerUpCardClient : powerUpCardsClient)
            powerUpIds.add(powerUpCardClient.getId());

        for (PowerUpCard powerUpCard : powerUps)
            if (powerUpIds.contains(powerUpCard.getId())) {
                realPowerUps.add(powerUpCard);
                powerUpIds.remove(powerUpCard.getId());
            }
        return realPowerUps;
    }

    public List<Ammo> spendPrice(List<Ammo> price, List<PowerUpCard> powerUpsToSpend, PowerUpsDeck deck) {
        List<Ammo> priceCopy = new ArrayList<>(price);
        for (PowerUpCard powerUpCard : powerUpsToSpend) {
            priceCopy.remove(powerUpCard.getAmmo());
            powerUps.remove(powerUpCard);
            deck.discardPowerUp(powerUpCard);
        }
        try {
            playerBoard.spendAmmo(priceCopy);
        } catch (AmmoNotAvailableException e) {
            e.printStackTrace();
        }
        return priceCopy;
    }

    public WeaponCard getWeaponByName(String weaponName) {
        for (WeaponCard weaponCard : weapons) {
            if (weaponName.equals(weaponCard.getWeaponName()))
                return weaponCard;
        }
        return null;
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

    public void setAvatar(Avatar avatar) {
        if (avatar != null) {
            this.avatar = avatar;
            damageMarkDelivered = avatar.getDamageMark();
        }
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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public AbstractModelEvent createBeginTurnEvent() {
        return new BeginTurnEvent(playerNickname);
    }

    //TODO should be transformed in initTurn method
    public EndTurnEvent createEndTurnEvent() {
        return new EndTurnEvent(this);
    }

}
