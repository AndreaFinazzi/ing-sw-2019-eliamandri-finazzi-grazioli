package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalModel {

    //Private information
    private List<WeaponCardClient> weaponCards;
    private List<PowerUpCardClient> powerUpCards;
    private List<AmmoCardClient> ammoCards;

    //Public information
    private ClientGameBoard gameBoard;
    private List<String> players;
    private Map<String, PlayerBoard> playerBoards;
    private Map<String, BoardSquareClient> playersPosition;
    private List<BoardSquareClient> listSpawn;

    public LocalModel() {
        weaponCards = new ArrayList<>();
        powerUpCards = new ArrayList<>();
        ammoCards = new ArrayList<>();

        players = new ArrayList<>();
        playerBoards = new HashMap<>();
    }

    public void generatesGameBoard(MapType mapType) {
        gameBoard = new ClientGameBoard(mapType);
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

    public List<WeaponCardClient> getWeaponCards() {
        return weaponCards;
    }

    public List<PowerUpCardClient> getPowerUpCards() {
        return powerUpCards;
    }

    public void updateWeaponsSpawn(WeaponCardClient weaponCardClient, Room room) {

    }

    public List<AmmoCardClient> getAmmoCards() {
        return ammoCards;
    }

    public List<String> getPlayers() {
        return players;
    }

    public Map<String, PlayerBoard> getPlayerBoards() {
        return playerBoards;
    }

    public Map<String, BoardSquareClient> getPlayersPosition() {
        return playersPosition;
    }

    public void updatePowerUpCards() {
        //TODO
    }

    public ClientGameBoard getGameBoard() {
        return gameBoard;
    }
}
