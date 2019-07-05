package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.CLIUtils;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Local model.
 */
public class LocalModel implements Serializable {

    //Private information

    private Map<String, Integer> points;

    private KillTrack killTrack;

    private List<WeaponCardClient> weaponCards; //user's weapon

    private boolean weaponHandFull;

    private List<PowerUpCardClient> powerUpCards;
    private List<Ammo> ammos;
    private List<DamageMark> damages;
    private List<DamageMark> marks;
    private int skulls;

    private List<Integer> deathScores;

    private boolean death;

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
    private boolean disconnected = false;

    /**
     * Instantiates a new Local model.
     */
    public LocalModel() {
        killTrack = new KillTrack(Rules.GAME_MAX_KILL_TRACK_SKULLS);
        points = new HashMap<>();

        weaponCards = new ArrayList<>();
        powerUpCards = new ArrayList<>();
        ammos = new ArrayList<>();

        damages = new ArrayList<>();
        marks = new ArrayList<>();

        opponentsInfo = new HashMap<>();
        playerToAvatarMap = new HashMap<>();
        deathScores = new ArrayList<>();

        weaponHandFull = false;
        //todo
        deathScores = Rules.PLAYER_BOARD_DEATH_SCORES;
    }

    /**
     * Sets player name.
     *
     * @param playerName the player name
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        points.put(playerName, 0);
    }

    /**
     * Sets kill track.
     *
     * @param killTrack the kill track
     */
    public void setKillTrack(KillTrack killTrack) {
        this.killTrack = killTrack;
    }

    /**
     * Sets weapon cards.
     *
     * @param weaponCards the weapon cards
     */
    public void setWeaponCards(List<WeaponCardClient> weaponCards) {
        this.weaponCards = weaponCards;
    }

    /**
     * Sets power up cards.
     *
     * @param powerUpCards the power up cards
     */
    public void setPowerUpCards(List<PowerUpCardClient> powerUpCards) {
        this.powerUpCards = powerUpCards;
    }

    /**
     * Sets ammos.
     *
     * @param ammos the ammos
     */
    public void setAmmos(List<Ammo> ammos) {
        this.ammos = ammos;
    }

    /**
     * Sets damages.
     *
     * @param damages the damages
     */
    public void setDamages(List<DamageMark> damages) {
        this.damages = damages;
    }

    /**
     * Sets marks.
     *
     * @param marks the marks
     */
    public void setMarks(List<DamageMark> marks) {
        this.marks = marks;
    }

    /**
     * Sets skulls.
     *
     * @param skulls the skulls
     */
    public void setSkulls(int skulls) {
        this.skulls = skulls;
    }

    /**
     * Sets death scores.
     *
     * @param deathScores the death scores
     */
    public void setDeathScores(List<Integer> deathScores) {
        this.deathScores = deathScores;
    }

    /**
     * Sets death.
     *
     * @param death the death
     */
    public void setDeath(boolean death) {
        this.death = death;
    }

    /**
     * Sets game board.
     *
     * @param gameBoard the game board
     */
    public void setGameBoard(GameBoardClient gameBoard) {
        this.gameBoard = gameBoard;
    }

    /**
     * Sets opponents info.
     *
     * @param opponentsInfo the opponents info
     */
    public void setOpponentsInfo(Map<String, PlayerClient> opponentsInfo) {
        this.opponentsInfo = opponentsInfo;
    }

    /**
     * Sets list spawn.
     *
     * @param listSpawn the list spawn
     */
    public void setListSpawn(List<SpawnBoardSquareClient> listSpawn) {
        this.listSpawn = listSpawn;
    }

    /**
     * Clear damages.
     */
    public void clearDamages() {
        damages.clear();
    }

    /**
     * Gets kill track.
     *
     * @return the kill track
     */
    public KillTrack getKillTrack() {
        return killTrack;
    }

    /**
     * Gets skulls.
     *
     * @return the skulls
     */
    public int getSkulls() {
        return skulls;
    }

    /**
     * Update points.
     *
     * @param newPoints the new points
     */
    public void updatePoints(Map<String, Integer> newPoints) {
        for (Map.Entry<String, Integer> playerPoints: newPoints.entrySet())
            points.put(playerPoints.getKey(), points.get(playerPoints.getKey()) + playerPoints.getValue());
    }

    /**
     * Generates game board.
     *
     * @param mapType the map type
     */
    public void generatesGameBoard(MapType mapType) {
        gameBoard = new GameBoardClient(mapType);
        listSpawn = gameBoard.getSpawnBoardSquareClient();
    }

    /**
     * Add power up boolean.
     *
     * @param card the card
     * @return the boolean
     */
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

    /**
     * Add ammo.
     *
     * @param ammo the ammo
     */
    public void addAmmo(Ammo ammo) {
        ammos.add(ammo);
    }

    /**
     * Remove ammo.
     *
     * @param ammo the ammo
     */
    public void removeAmmo(Ammo ammo) {
        ammos.remove(ammo);
    }

    /**
     * Add weapon.
     *
     * @param weapon the weapon
     */
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

    /**
     * Remove weapon weapon card client.
     *
     * @param weapon the weapon
     * @return the weapon card client
     */
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

    /**
     * Add skull.
     */
    public void addSkull() {
        skulls++;
    }

    /**
     * Gets weapon cards.
     *
     * @return the weapon cards
     */
    public List<WeaponCardClient> getWeaponCards() {
        return weaponCards;
    }

    /**
     * Remove power up.
     *
     * @param powerUpCardClient the power up card client
     */
    public void removePowerUp(PowerUpCardClient powerUpCardClient) {
        powerUpCardClient.setSlotPosition(-1);
        powerUpCards.remove(powerUpCardClient);
    }

    /**
     * Gets power up cards.
     *
     * @return the power up cards
     */
    public List<PowerUpCardClient> getPowerUpCards() {
        return new ArrayList<>(powerUpCards);
    }

    /**
     * Gets power up card by id.
     *
     * @param powerUpId the power up id
     * @return the power up card by id
     */
    public PowerUpCardClient getPowerUpCardById(String powerUpId) {
        for (PowerUpCardClient powerUpCard : powerUpCards) {
            if (powerUpCard.getId().equals(powerUpId))
                return powerUpCard;
        }

        return null;
    }

    /**
     * Update weapons spawn boolean.
     *
     * @param weaponCardClient the weapon card client
     * @param pose the pose
     * @return the boolean
     */
    public boolean updateWeaponsSpawn(WeaponCardClient weaponCardClient, Coordinates pose) {
        BoardSquareClient boardSquareClient = getGameBoard().getBoardSquareByCoordinates(pose);
        if(boardSquareClient != null && boardSquareClient.isSpawnBoard()) {
            return boardSquareClient.addWeaponCard(weaponCardClient);
        }
        return false;
    }

    /**
     * Gets ammos.
     *
     * @return the ammos
     */
    public List<Ammo> getAmmos() {
        return ammos;
    }

    /*
    public List<String> getPlayers() {
        return players;
    }
     */

    /**
     * Gets players avatar map.
     *
     * @return the players avatar map
     */
    public Map<String, Avatar> getPlayersAvatarMap() {
        return playerToAvatarMap;
    }

    /**
     * Gets damages.
     *
     * @return the damages
     */
    public List<DamageMark> getDamages() {
        return damages;
    }

    /**
     * Gets marks.
     *
     * @return the marks
     */
    public List<DamageMark> getMarks() {
        return marks;
    }

    /**
     * Sets player to avatar map.
     *
     * @param playerAvatarMap the player avatar map
     */
    public void setPlayerToAvatarMap(Map<String, Avatar> playerAvatarMap) {
        this.playerToAvatarMap = playerAvatarMap;
    }

    /**
     * Is weapon hand full boolean.
     *
     * @return the boolean
     */
    public boolean isWeaponHandFull() {
        return weaponHandFull;
    }

    /**
     * Sets weapon hand full.
     */
    public void setWeaponHandFull() {
        weaponHandFull = true;
    }

    /**
     * Gets weapon cards setup.
     *
     * @return the weapon cards setup
     */
    public Map<Coordinates, List<WeaponCardClient>> getWeaponCardsSetup() {

        Map<Coordinates, List<WeaponCardClient>> coordinatesWeaponsMap = new HashMap<>();
        for (BoardSquareClient square : listSpawn) {
            coordinatesWeaponsMap.put(square.getCoordinates(), square.getWeaponCards());
        }
        return coordinatesWeaponsMap;
    }

    /**
     * Gets server weapon cards setup.
     *
     * @return the server weapon cards setup
     */
    public Map<Coordinates, List<WeaponCardClient>> getServerWeaponCardsSetup() {
        return serverWeaponCardsSetup;
    }

    /**
     * Gets server ammo cards setup.
     *
     * @return the server ammo cards setup
     */
    public Map<Coordinates, AmmoCardClient> getServerAmmoCardsSetup() {
        return serverAmmoCardsSetup;
    }

    /**
     * Gets weapon card by name on map.
     *
     * @param weaponName the weapon name
     * @return the weapon card by name on map
     */
    public WeaponCardClient getWeaponCardByNameOnMap(String weaponName) {
        for(BoardSquareClient square : listSpawn){
            List<WeaponCardClient> list = square.getWeaponCards();
            if(list != null) {
                for(int i = 0; i<list.size(); i++) {
                    if(list.get(i).getWeaponName().equals(weaponName))
                        return list.get(i);
                }
            }
        }
        return null;
    }

    /**
     * Gets weapon card by id on map.
     *
     * @param weaponId the weapon id
     * @return the weapon card by id on map
     */
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

    /**
     * Gets weapon by id in hand.
     *
     * @param id the id
     * @return the weapon by id in hand
     */
    public WeaponCardClient getWeaponByIdInHand(String id) {
        for (WeaponCardClient weaponCard : weaponCards) {
            if (weaponCard.getId().equals(id)) return weaponCard;
        }

        return null;
    }

    //In this version it controls that the player can pay the price in any way

    /**
     * Can pay boolean.
     *
     * @param price the price
     * @return the boolean
     */
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

    /**
     * Can pay boolean.
     *
     * @param price the price
     * @param powerUpsToPay the power ups to pay
     * @return the boolean
     */
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
        } else {
            return false;
        }
    }

    /**
     * Gets points.
     *
     * @return the points
     */
    public Map<String, Integer> getPoints() {
        return points;
    }

    /**
     * Gets game board.
     *
     * @return the game board
     */
    public GameBoardClient getGameBoard() {
        return gameBoard;
    }

    /**
     * Gets opponent info.
     *
     * @param player the player
     * @return the opponent info
     */
    public PlayerClient getOpponentInfo(String player) {
        return opponentsInfo.get(player);
    }

    /**
     * Gets opponents list.
     *
     * @return the opponents list
     */
    public List<PlayerClient> getOpponentsList() {
        List<PlayerClient> opponents = new ArrayList<>();
        for(Map.Entry<String, PlayerClient> entry : opponentsInfo.entrySet()) {
            opponents.add(entry.getValue());
        }
        return opponents;
    }

    /**
     * Add opponent.
     *
     * @param player the player
     */
    public void addOpponent(String player) {
        opponentsInfo.put(player, new PlayerClient(playerToAvatarMap.get(player)));
        points.put(player, 0);
    }

    /**
     * Gets list spawn.
     *
     * @return the list spawn
     */
    public List<SpawnBoardSquareClient> getListSpawn() {
        return listSpawn;
    }

    /**
     * Gets weapon by name.
     *
     * @param weapon the weapon
     * @return the weapon by name
     */
    public WeaponCardClient getWeaponByName(String weapon) {
        for (WeaponCardClient weaponCardClient: weaponCards) {
            if (weapon.equals(weaponCardClient.getWeaponName()))
                return weaponCardClient;
        }
        return null;
    }

    /**
     * Perform damage.
     *
     * @param self the self
     * @param damagedPlayer the damaged player
     * @param damages the damages
     * @param marks the marks
     * @param removedMarks the removed marks
     */
    public void performDamage(String self, String damagedPlayer, List<DamageMark> damages, List<DamageMark> marks, List<DamageMark> removedMarks) {
        if (self.equals(damagedPlayer)) {
            this.damages.addAll(damages);
            this.marks.removeAll(removedMarks);
            this.marks.addAll(marks);
        } else {
            getOpponentInfo(damagedPlayer).addDamages(damages);
            getOpponentInfo(damagedPlayer).removeMarks(removedMarks);
            getOpponentInfo(damagedPlayer).addMarks(marks);
        }
    }

    /**
     * Draw card string [ ] [ ].
     *
     * @return the string [ ] [ ]
     */
    public String[][] drawCard() {
        String[][] matrix = CLIUtils.drawEmptyBox(WIDTH, HEIGHT, Color.damageMarkToColor(playerToAvatarMap.get(playerName).getDamageMark()));
        String string = "Avatar: " + playerToAvatarMap.get(playerName).getName();
        for(int i = 0; i<string.length(); i++) {
            matrix[i+2][1] = String.valueOf(string.charAt(i));
        }
        string = "Skulls: " + skulls;
        for(int i = 0; i<string.length(); i++) {
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
        for(int i = 0; i<string.length(); i++) {
            matrix[i+2][8] = String.valueOf(string.charAt(i));
        }
        poseX = 2;
        for(int i = 0; i<marks.size(); i++) {
            matrix[poseX+i][9] = marks.get(i).toString();
            poseX++;
        }
        string = "Number of powerUp: " + powerUpCards.size();
        for(int i = 0; i<string.length(); i++) {
            matrix[i+2][10] = String.valueOf(string.charAt(i));
        }

        return  matrix;
    }

    /**
     * Gets map type.
     *
     * @return the map type
     */
    public MapType getMapType() {
        return mapType;
    }

    /**
     * Sets map type.
     *
     * @param mapType the map type
     */
    public void setMapType(MapType mapType) {
        this.mapType = mapType;
    }

    /**
     * Sets weapon cards setup.
     *
     * @param weaponCardsSetup the weapon cards setup
     */
    public void setWeaponCardsSetup(Map<Coordinates, List<WeaponCardClient>> weaponCardsSetup) {
        this.serverWeaponCardsSetup = weaponCardsSetup;
    }

    /**
     * Sets ammo cards setup.
     *
     * @param ammoCardsSetup the ammo cards setup
     */
    public void setAmmoCardsSetup(Map<Coordinates, AmmoCardClient> ammoCardsSetup) {
        this.serverAmmoCardsSetup = ammoCardsSetup;
    }

    /**
     * Gets server player positions.
     *
     * @return the server player positions
     */
    public Map<String, Coordinates> getServerPlayerPositions() {
        return serverPlayerPositions;
    }

    /**
     * Sets server player positions.
     *
     * @param serverPlayerPositions the server player positions
     */
    public void setServerPlayerPositions(Map<String, Coordinates> serverPlayerPositions) {
        this.serverPlayerPositions = serverPlayerPositions;
    }

    /**
     * Sets disconnected.
     *
     * @param disconnected the disconnected
     */
    public void setDisconnected(boolean disconnected) {
        this.disconnected = disconnected;
    }
}
