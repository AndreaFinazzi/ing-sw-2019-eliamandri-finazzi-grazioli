package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.PlayerBoard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalModel {

    //Private information
    private List<WeaponCardClient> weaponCards; //user's weapon
    private List<PowerUpCardClient> powerUpCards;
    private List<Ammo> ammos;

    //Public information
    private GameBoardClient gameBoard;
    private List<String> players;
    private Map<String, PlayerBoard> playerBoards;
    private Map<String, Avatar> playerToAvatarMap;
    private List<BoardSquareClient> listSpawn;

    public LocalModel() {
        weaponCards = new ArrayList<>();
        powerUpCards = new ArrayList<>();
        ammos = new ArrayList<>();

        players = new ArrayList<>();
        playerBoards = new HashMap<>();
        playerToAvatarMap = new HashMap<>();
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
        if(powerUpCards.size() < Rules.PLAYER_CARDS_MAX_POWER_UPS && !powerUpCards.contains(card)){
            powerUpCards.add(card);
            return true;
        }
        return false;
    }

    public void addAmmo(Ammo ammo) {
        ammos.add(ammo);
    }

    public List<WeaponCardClient> getWeaponCards() {
        return weaponCards;
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

    public Map<String, PlayerBoard> getPlayerBoards() {
        return playerBoards;
    }

    public Map<String, Avatar> getPlayersAvatarMap() {
        return playerToAvatarMap;
    }

    public void setPlayerToAvatarMap(Map<String, Avatar> playerAvatarMap) {
        this.playerToAvatarMap = playerAvatarMap;
    }

    public void updatePowerUpCards() {
        //TODO
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

    public GameBoardClient getGameBoard() {
        return gameBoard;
    }
}
