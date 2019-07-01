package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLIUtils;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Ammo;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.Avatar;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.DamageMark;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.MapType;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalModel implements Serializable {

    //Private information

    private List<WeaponCardClient> weaponCards; //user's weapon
    private boolean weaponHandFull;
    private List<PowerUpCardClient> powerUpCards;
    private List<Ammo> ammos;
    private List<DamageMark> damages;

    private List<DamageMark> marks;

    private int skulls;
    private List<Integer> deathScores;
    private boolean death;
    private boolean overkill;
    private int movementsAllowed;
    private String playerName;

    //Public information
    private MapType mapType;

    private GameBoardClient gameBoard;
    //private List<String> players;
    private Map<String, PlayerClient> opponentsInfo;
    private Map<String, Avatar> playerToAvatarMap;
    private List<SpawnBoardSquareClient> listSpawn;

    // temp field used in reconnection update

    private Map<Coordinates, List<WeaponCardClient>> serverWeaponCardsSetup;
    private Map<Coordinates, AmmoCardClient> serverAmmoCardsSetup;
    private Map<String, Coordinates> serverPlayerPositions;

    private final static int WIDTH = 30;

    private final static int HEIGHT = 12;
    public LocalModel() {
        weaponCards = new ArrayList<>();
        powerUpCards = new ArrayList<>();
        ammos = new ArrayList<>();

        damages = new ArrayList<>();
        marks = new ArrayList<>();

        //players = new ArrayList<>();
        opponentsInfo = new HashMap<>();
        playerToAvatarMap = new HashMap<>();
        deathScores = new ArrayList<>();

        weaponHandFull = false;
        //todo
        deathScores = Rules.PLAYER_BOARD_DEATH_SCORES;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setWeaponCards(List<WeaponCardClient> weaponCards) {
        this.weaponCards = weaponCards;
    }

    public void setPowerUpCards(List<PowerUpCardClient> powerUpCards) {
        this.powerUpCards = powerUpCards;
    }

    public void setAmmos(List<Ammo> ammos) {
        this.ammos = ammos;
    }

    public void setDamages(List<DamageMark> damages) {
        this.damages = damages;
    }

    public void setMarks(List<DamageMark> marks) {
        this.marks = marks;
    }

    public void setSkulls(int skulls) {
        this.skulls = skulls;
    }

    public void setDeathScores(List<Integer> deathScores) {
        this.deathScores = deathScores;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public void setGameBoard(GameBoardClient gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void setOpponentsInfo(Map<String, PlayerClient> opponentsInfo) {
        this.opponentsInfo = opponentsInfo;
    }

    public void setListSpawn(List<SpawnBoardSquareClient> listSpawn) {
        this.listSpawn = listSpawn;
    }

    public void generatesGameBoard(MapType mapType) {
        gameBoard = new GameBoardClient(mapType);
        listSpawn = gameBoard.getSpawnBoardSquareClient();
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
        weapon.setSlotPosition(null, index);
        weaponCards.add(weapon);
    }

    public WeaponCardClient removeWeapon(String weapon) {
        WeaponCardClient toRemove = null;
        for (WeaponCardClient weaponCardClient: weaponCards) {
            if (weapon.equals(weaponCardClient.getWeaponName()))
                toRemove = weaponCardClient;
        }
        weaponCards.remove(toRemove);
        toRemove.setSlotPosition(null, -1);
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
        return new ArrayList<>(powerUpCards);
    }

    public PowerUpCardClient getPowerUpCardById(String powerUpId) {
        for (PowerUpCardClient powerUpCard : powerUpCards) {
            if (powerUpCard.getId().equals(powerUpId))
                return powerUpCard;
        }

        return null;
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

    /*
    public List<String> getPlayers() {
        return players;
    }
     */
    public Map<String, Avatar> getPlayersAvatarMap() {
        return playerToAvatarMap;
    }
    public List<DamageMark> getDamages() {
        return damages;
    }

    public List<DamageMark> getMarks() {
        return marks;
    }

    public void setPlayerToAvatarMap(Map<String, Avatar> playerAvatarMap) {
        this.playerToAvatarMap = playerAvatarMap;
    }

    public boolean isWeaponHandFull() {
        return weaponHandFull;
    }

    public void setWeaponHandFull() {
        weaponHandFull = true;
    }

    public Map<Coordinates, List<WeaponCardClient>> getWeaponCardsSetup() {

        Map<Coordinates, List<WeaponCardClient>> coordinatesWeaponsMap = new HashMap<>();
        for (BoardSquareClient square : listSpawn) {
            coordinatesWeaponsMap.put(square.getCoordinates(), square.getWeaponCards());
        }
        return coordinatesWeaponsMap;
    }

    public Map<Coordinates, List<WeaponCardClient>> getServerWeaponCardsSetup() {
        return serverWeaponCardsSetup;
    }

    public Map<Coordinates, AmmoCardClient> getServerAmmoCardsSetup() {
        return serverAmmoCardsSetup;
    }

    public WeaponCardClient getWeaponCardByNameOnMap(String weaponName) {
        for(BoardSquareClient square : listSpawn){
            List<WeaponCardClient> list = square.getWeaponCards();
            if(list != null) {
                for(int i=0; i<list.size(); i++) {
                    if(list.get(i).getWeaponName().equals(weaponName))
                        return list.get(i);
                }
            }
        }
        return null;
    }

    public WeaponCardClient getWeaponCardByIdOnMap(String weaponId) {
        for (BoardSquareClient square : listSpawn) {
            List<WeaponCardClient> list = square.getWeaponCards();
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getId().equals(weaponId))
                        return list.get(i);
                }
            }
        }
        return null;
    }

    public WeaponCardClient getWeaponByIdInHand(String id) {
        for (WeaponCardClient weaponCard : weaponCards) {
            if (weaponCard.getId().equals(id)) return weaponCard;
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

    public List<PlayerClient> getOpponentsList() {
        List<PlayerClient> opponents = new ArrayList<>();
        for(Map.Entry<String, PlayerClient> entry : opponentsInfo.entrySet()) {
            opponents.add(entry.getValue());
        }
        return opponents;
    }

    public void addOpponent(String player) {
        opponentsInfo.put(player, new PlayerClient(playerToAvatarMap.get(player)));
    }

    public List<SpawnBoardSquareClient> getListSpawn() {
        return listSpawn;
    }

    public WeaponCardClient getWeaponByName(String weapon) {
        for (WeaponCardClient weaponCardClient: weaponCards) {
            if (weapon.equals(weaponCardClient.getWeaponName()))
                return weaponCardClient;
        }
        return null;
    }

    public void performDamage(String self, String damagedPlayer, List<DamageMark> damages, List<DamageMark> marks, List<DamageMark> removedMarks) {
        if (self.equals(damagedPlayer)) {
            this.damages.addAll(damages);
            this.marks.removeAll(removedMarks);
            this.marks.addAll(marks);
        }
        else {
            getOpponentInfo(damagedPlayer).addDamages(damages);
            getOpponentInfo(damagedPlayer).removeMarks(removedMarks);
            getOpponentInfo(damagedPlayer).addMarks(marks);
        }
    }

    public String[][] drawCard() {
        String[][] matrix = CLIUtils.drawEmptyBox(WIDTH, HEIGHT, Color.damageMarkToColor(playerToAvatarMap.get(playerName).getDamageMark()));
        String string = "Avatar: " + playerToAvatarMap.get(playerName).getName();
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][1] = String.valueOf(string.charAt(i));
        }
        string = "Skulls: " + skulls;
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][2] = String.valueOf(string.charAt(i));
        }
        string = "Death score: " + deathScores.get(0);
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][3] = String.valueOf(string.charAt(i));
        }
        string = "Death: " + (death ? "Yes" : "No");
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][4] = String.valueOf(string.charAt(i));
        }
        string = "ammos: ";
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][5] = String.valueOf(string.charAt(i));
        }
        int poseX = string.length()+2;
        for(int i=0; i<ammos.size(); i++) {
            matrix[poseX+i][5] = ammos.get(i).toString();
            poseX++;
        }
        string = "Damages: ";
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][6] = String.valueOf(string.charAt(i));
        }
        poseX = 2;
        for(int i=0; i<damages.size(); i++) {
            matrix[poseX+i][7] = damages.get(i).toString();
            poseX++;
        }
        string = "Marks: ";
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][8] = String.valueOf(string.charAt(i));
        }
        poseX = 2;
        for(int i=0; i<marks.size(); i++) {
            matrix[poseX+i][9] = marks.get(i).toString();
            poseX++;
        }
        string = "Number of powerUp: " + powerUpCards.size();
        for(int i=0; i<string.length(); i++) {
            matrix[i+2][10] = String.valueOf(string.charAt(i));
        }

        return  matrix;
    }

    public void setOverkill(boolean overkill) {
        this.overkill = overkill;
    }

    public MapType getMapType() {
        return mapType;
    }

    public void setMapType(MapType mapType) {
        this.mapType = mapType;
    }

    public void setWeaponCardsSetup(Map<Coordinates, List<WeaponCardClient>> weaponCardsSetup) {
        this.serverWeaponCardsSetup = weaponCardsSetup;
    }

    public void setAmmoCardsSetup(Map<Coordinates, AmmoCardClient> ammoCardsSetup) {
        this.serverAmmoCardsSetup = ammoCardsSetup;
    }

    public Map<String, Coordinates> getServerPlayerPositions() {
        return serverPlayerPositions;
    }

    public void setServerPlayerPositions(Map<String, Coordinates> serverPlayerPositions) {
        this.serverPlayerPositions = serverPlayerPositions;
    }
}
