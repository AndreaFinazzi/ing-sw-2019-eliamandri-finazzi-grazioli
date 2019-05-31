package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalModel {

    //Private information
    private List<String> weaponCards;
    private List<PowerUpCard> powerUpCards;
    private List<AmmoCard> ammoCards;

    //Public information
    private GameBoard gameBoard;
    private List<String> players;
    private Map<String, PlayerBoard> playerBoards;
    private Map<String, BoardSquare> playersPosition;

    public LocalModel() {
        weaponCards = new ArrayList<>();
        powerUpCards = new ArrayList<>();
        ammoCards = new ArrayList<>();

        players = new ArrayList<>();
        playerBoards = new HashMap<>();
    }

    public void updateWeapons(String collectedWeapon, String dropOfWeapon) {

        if (!weaponCards.remove(dropOfWeapon))
            System.out.println("Invalid drop");

        if (weaponCards.size() < Rules.PLAYER_CARDS_MAX_WEAPONS && !weaponCards.contains(collectedWeapon))
            weaponCards.add(collectedWeapon);
        else
            System.out.println("Invalid collect");
    }

    public void updatePowerUpCards() {
        //TODO
    }

    public void updatePlayersPosition(String player, Coordinates finalDestination) {
        BoardSquare position = gameBoard.getBoardSquareByCoordinates(finalDestination);
        playersPosition.put(player, position);
    }


}
