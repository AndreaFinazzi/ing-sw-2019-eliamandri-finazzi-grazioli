package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalModel {

    //Private information
    private List<WeaponCardClient> weaponCards; //user's weapon
    private boolean weaponHandFull;
    private List<PowerUpCardClient> powerUpCards;
    private List<Ammo> ammos;

    //Public information
    private GameBoardClient gameBoard;
    private List<String> players;
    private Map<String, PlayerClient> opponentsInfo;
    private Map<String, Avatar> playerToAvatarMap;
    private List<BoardSquareClient> listSpawn;

    public LocalModel() {
        weaponCards = new ArrayList<>();
        powerUpCards = new ArrayList<>();
        ammos = new ArrayList<>();

        players = new ArrayList<>();
        opponentsInfo = new HashMap<>();
        playerToAvatarMap = new HashMap<>();

        weaponHandFull = false;
    }

    public void generatesGameBoard(MapType mapType) {
        gameBoard = new GameBoardClient(mapType);
        listSpawn = gameBoard.getSpawnBoardSquareClient();
    }

    public void updateWeapons(WeaponCardClient collectedWeapon, WeaponCardClient dropOfWeapon) {

        if (!weaponCards.remove(dropOfWeapon))
            System.out.println("Invalid drop");

        if (weaponCards.size() < Rules.PLAYER_CARDS_MAX_WEAPONS && !weaponCards.contains(collectedWeapon))
            weaponCards.add(collectedWeapon);
        else
            System.out.println("Invalid collect");
    }

    public boolean addPowerUp(PowerUpCardClient card) {
        List<Integer> slotPositions = new ArrayList<>();
        int index = 0;
        for (PowerUpCardClient powerUpCardClient: powerUpCards)
            slotPositions.add(powerUpCardClient.getSlotPosition());
        while (slotPositions.contains(index))
            index++;
        if(powerUpCards.size() < Rules.PLAYER_CARDS_MAX_POWER_UPS){
            powerUpCards.add(card);
            card.setSlotPosition(index);
            return true;
        }
        return false;
    }

    public void addAmmo(Ammo ammo) {
        ammos.add(ammo);
    }

    public void removeAmmo(Ammo ammo) {
        ammos.remove(ammo);
    }

    public void addWeapon(WeaponCardClient weapon) {
        List<Integer> slotPositions = new ArrayList<>();
        int index = 0;
        for (WeaponCardClient weaponCardClient: weaponCards)
            slotPositions.add(weaponCardClient.getSlotPosition());
        while (slotPositions.contains(index))
            index++;
        weapon.setSlotPosition(index);
        weaponCards.add(weapon);
    }

    public WeaponCardClient removeWeapon(String weapon) {
        WeaponCardClient toRemove = null;
        for (WeaponCardClient weaponCardClient: weaponCards) {
            if (weapon.equals(weaponCardClient.getWeaponName()))
                toRemove = weaponCardClient;
        }
        weaponCards.remove(toRemove);
        toRemove.setSlotPosition(-1);
        return toRemove;
    }

    public List<WeaponCardClient> getWeaponCards() {
        return weaponCards;
    }

    public void removePowerUp(PowerUpCardClient powerUpCardClient) {
        powerUpCardClient.setSlotPosition(-1);
        powerUpCards.remove(powerUpCardClient);
    }

    public List<PowerUpCardClient> getPowerUpCards() {
        return powerUpCards;
    }

    public boolean updateWeaponsSpawn(WeaponCardClient weaponCardClient, Coordinates pose) {
        BoardSquareClient boardSquareClient = getGameBoard().getBoardSquareByCoordinates(pose);
        if(boardSquareClient != null && boardSquareClient.isSpawnBoard()) {
            return boardSquareClient.addWeaponCard(weaponCardClient);
        }
        return false;
    }

    public List<Ammo> getAmmos() {
        return ammos;
    }

    public List<String> getPlayers() {
        return players;
    }

    public Map<String, Avatar> getPlayersAvatarMap() {
        return playerToAvatarMap;
    }

    public void setPlayerToAvatarMap(Map<String, Avatar> playerAvatarMap) {
        this.playerToAvatarMap = playerAvatarMap;
    }

    public boolean isWeaponHandFull() {
        return weaponHandFull;
    }

    public void updatePowerUpCards() {
        //TODO
    }

    public void setWeaponHandFull() {
        weaponHandFull = true;
    }


    public WeaponCardClient getWeaponCardByNameOnMap(String weaponName) {
        for(BoardSquareClient square : listSpawn){
            List<WeaponCardClient> list = square.getWeapons();
            if(list != null) {
                for(int i=0; i<list.size(); i++) {
                    if(list.get(i).getWeaponName().equals(weaponName))
                        return list.get(i);
                }
            }
        }
        return null;
    }

    //In this version it controls that the player can pay the price in any way
    public boolean canPay(List<Ammo> price) {
        List<Ammo> priceCopy = new ArrayList<>(price);

        for (Ammo ammo: ammos) {
            if (priceCopy.contains(ammo))
                priceCopy.remove(ammo);
        }
        for (PowerUpCardClient powerUpCard: powerUpCards) {
            if (priceCopy.contains(powerUpCard.getEquivalentAmmo()))
                priceCopy.remove(powerUpCard.getEquivalentAmmo());
        }
        if (priceCopy.size() == 0)
            return true;
        else
            return false;
    }

    public boolean canPay(List<Ammo> price, List<PowerUpCardClient> powerUpsToPay) {
        List<Ammo> priceCopy = new ArrayList<>(price);

        for (PowerUpCardClient powerUpCard: powerUpsToPay)
            priceCopy.remove(powerUpCard.getEquivalentAmmo());

        for (Ammo ammo: ammos) {
            if (priceCopy.contains(ammo))
                priceCopy.remove(ammo);
        }
        if (priceCopy.size() == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public GameBoardClient getGameBoard() {
        return gameBoard;
    }

    public PlayerClient getOpponentInfo(String player) {
        return opponentsInfo.get(player);
    }

    public void addOpponent(String player) {
        opponentsInfo.put(player, new PlayerClient());
    }
}
