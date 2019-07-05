package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.LocalModel;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PlayerClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.PlayerShotEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.SkullRemovalEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.SpawnSelectionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.AvatarNotAvailableException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerReconnectionException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.AmmoCardsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.PowerUpsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observable;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Match.
 */
public class Match implements Observable {

    // Players list extra-methods implementations
    private Player.AbstractPlayerList players = new Player.AbstractPlayerList() {
        // allows to check on nicknames
        @Override
        public boolean contains(String nickname) {
            for (Player player : this) {
                if (player.getPlayerNickname().equals(nickname)) return true;
            }
            return false;
        }

        @Override
        public Player get(String nickname) {
            for (Player player : this) {
                if (player.getPlayerNickname().equals(nickname)) return player;
            }
            return null;
        }

        @Override
        public Player add(String nickname) {
            Player newPlayer = new Player(nickname);
            newPlayer.setConnected(true);
            add(newPlayer);
            return newPlayer;
        }

        @Override
        public Player add(int clientID, String nickname) {
            Player newPlayer = new Player(clientID, nickname);
            newPlayer.setConnected(true);
            add(newPlayer);
            return newPlayer;
        }

        @Override
        public Player remove(String nickname) {
            for (Player player : this) {
                if (player.getPlayerNickname().equals(nickname)) {
                    remove(player);
                    return player;
                }
            }
            return null;
        }

        public int size() {
            int cont = 0;
            for (Player player : players)
                cont++;
            return cont;
        }

    };

    /**
     * The Logger.
     */
    static final Logger LOGGER = Logger.getLogger(Match.class.getName());

    private int matchID;

    private ArrayList<Observer> observers = new ArrayList<>();

    private GameBoard gameBoard;

    /**
     * The Kill track.
     */
    KillTrack killTrack;
    private MatchPhase phase;
    private Player currentPlayer;
    private Player firstPlayer;
    private Player lastTurnPlayer;
    private PowerUpsDeck powerUpsDeck;
    private WeaponsDeck weaponsDeck;
    private AmmoCardsDeck ammoCardsDeck;
    private ArrayList<Avatar> availableAvatars = new ArrayList<>(Arrays.asList(Avatar.values()));

    private int turn = 0;

    /**
     * Instantiates a new Match.
     */
    public Match() {
        phase = MatchPhase.INITIALIZATION;
        killTrack = new KillTrack(Rules.GAME_MAX_KILL_TRACK_SKULLS);
        try {
            powerUpsDeck = new PowerUpsDeck();
        } catch (URISyntaxException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        weaponsDeck = new WeaponsDeck();
        ammoCardsDeck = new AmmoCardsDeck();
    }

    /**
     * Skulls assignment list.
     *
     * @return the list
     */
    public List<AbstractModelEvent> skullsAssignment() {
        List<AbstractModelEvent> events = new ArrayList<>();
        for (Player deadPlayer : getDeadPlayers()) {
            PlayerBoard deadPlayerBoard = deadPlayer.getPlayerBoard();
            boolean trackFull = killTrack.isFull();
            if (!trackFull)
                deadPlayer.getPlayerBoard().addSkull();
            killTrack.removeSkull(currentPlayer.getDamageMarkDelivered(), deadPlayerBoard.isOverkill());

            if (deadPlayer.getPlayerBoard().isOverkill() &&
                    currentPlayer.getPlayerBoard().numMarkType(deadPlayer.getDamageMarkDelivered()) < Rules.PLAYER_BOARD_MAX_MARKS_PER_TYPE && deadPlayerBoard.canUseMark()) {
                currentPlayer.getPlayerBoard().addMark(deadPlayer.getDamageMarkDelivered());
                events.add(new PlayerShotEvent(currentPlayer, currentPlayer.getPlayerNickname(), new ArrayList<>(), new ArrayList<>(Arrays.asList(deadPlayer.getDamageMarkDelivered())), new ArrayList<>()));
            }
            events.add(new SkullRemovalEvent(currentPlayer, currentPlayer.getDamageMarkDelivered(), deadPlayer.getPlayerNickname(), deadPlayer.getPlayerBoard().isOverkill(), trackFull));
        }
        if (killTrack.isFull()) {
            phase = MatchPhase.FINAL_FRENZY;
            lastTurnPlayer = currentPlayer;
        }
        return events;
    }

    /**
     * Gets last turn player.
     *
     * @return the last turn player
     */
    public Player getLastTurnPlayer() {
        return lastTurnPlayer;
    }

    /**
     * Final frenzy player board switch list.
     *
     * @return the list
     */
    public List<Player> finalFrenzyPlayerBoardSwitch() {
        List<Player> playersToSwitch = new ArrayList<>();
        for (Player player : players) {
            if (!player.hasDamages()) {
                player.getPlayerBoard().switchToFinalFrenzy();
                playersToSwitch.add(player);
            }
            if (players.indexOf(player) <= players.indexOf(currentPlayer))
                player.enableDoubleAction();
        }
        return playersToSwitch;
    }


    public Map<String, Integer> finalPointsCalculation() {
        List<DamageMark> markOrder = new ArrayList<>();
        Map<DamageMark, Integer> marksCount = new HashMap<>();
        for (KillTrack.Slot slot : killTrack.getTrack()) {
            if (!slot.isSkull()) {
                DamageMark damageMark = slot.getDamageMark();
                if (!markOrder.contains(damageMark))
                    markOrder.add(damageMark);
                if (!marksCount.keySet().contains(damageMark))
                    marksCount.put(damageMark, 0);
                if (slot.isDoubleDamage())
                    marksCount.put(damageMark, marksCount.get(damageMark) + 2);
                else
                    marksCount.put(damageMark, marksCount.get(damageMark) + 1);
            }
        }
        for (DamageMark damageMark : killTrack.getMarksAfterLastSkull()) {
            if (!markOrder.contains(damageMark))
                markOrder.add(damageMark);
            if (!marksCount.keySet().contains(damageMark))
                marksCount.put(damageMark, 0);

            marksCount.put(damageMark, marksCount.get(damageMark) + 1);
        }

        Map<String, Integer> points = new HashMap<>();
        int count = 0;
        while (!markOrder.isEmpty()) {
            Player bestPlayer = null;
            int max = 0;
            for (DamageMark damageMark : markOrder) {
                if (marksCount.get(damageMark) > max) {
                    bestPlayer = getPlayerByMark(damageMark);
                    max = marksCount.get(damageMark);
                }
            }
            markOrder.remove(bestPlayer.getDamageMarkDelivered());
            if (bestPlayer != null)
                points.put(bestPlayer.getPlayerNickname(), Rules.PLAYER_BOARD_DEATH_SCORES.get(count));
            count++;
        }

        return points;
    }

    public String getWinner() {
        int max = 0;
        Player winner = null;
        for (Player player : players) {
            if (player.getPoints() > max) {
                winner = player;
                max = player.getPoints();
            }
        }
        return winner.getPlayerNickname();
    }

    /**
     * Update points.
     *
     * @param playerPoints the player points
     */
    public void updatePoints(Map<String, Integer> playerPoints) {
        for (Map.Entry<String, Integer> points : playerPoints.entrySet())
            players.get(points.getKey()).addPoints(points.getValue());
    }

    /**
     * Gets match id.
     *
     * @return the match id
     */
    public int getMatchID() {
        return matchID;
    }

    /**
     * Sets match id.
     *
     * @param matchID the match id
     */
    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    /**
     * Gets current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets power ups deck.
     *
     * @return the power ups deck
     */
    public PowerUpsDeck getPowerUpsDeck() {
        return powerUpsDeck;
    }

    /**
     * Gets weapons deck.
     *
     * @return the weapons deck
     */
    public WeaponsDeck getWeaponsDeck() {
        return weaponsDeck;
    }

    /**
     * Gets ammo cards deck.
     *
     * @return the ammo cards deck
     */
    public AmmoCardsDeck getAmmoCardsDeck() {
        return ammoCardsDeck;
    }

    /**
     * Next current player.
     */
    public void nextCurrentPlayer() {
        int index = players.indexOf(currentPlayer);
        currentPlayer = players.get((index + 1) % players.size());
    }

    /**
     * Gets next player.
     *
     * @return the next player
     */
    public Player getNextPlayer() {
        int index = players.indexOf(currentPlayer);
        return players.get((index + 1) % players.size());
    }

    /**
     * Gets first player.
     *
     * @return the first player
     */
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    /**
     * Gets players.
     *
     * @return the players
     */
    public Player.AbstractPlayerList getPlayers() {
        return players;
    }

    /**
     * Gets players nickname.
     *
     * @return the players nickname
     */
    public List<String> getPlayersNickname() {
        ArrayList<String> playersNickname = new ArrayList<>();
        for (Player player : players) {
            playersNickname.add(player.getPlayerNickname());
        }
        return playersNickname;
    }

    /**
     * Gets player.
     *
     * @param nickname the nickname
     * @return the player
     */
    public Player getPlayer(String nickname) {
        return players.get(nickname);
    }

    /**
     * Add player.
     *
     * @param player the player
     * @throws MaxPlayerException            the max player exception
     * @throws PlayerAlreadyPresentException the player already present exception
     */
    public void addPlayer(Player player) throws MaxPlayerException, PlayerAlreadyPresentException {
        if (players.size() >= Rules.GAME_MAX_PLAYERS)
            throw new MaxPlayerException();
        if (players.contains(player))
            throw new PlayerAlreadyPresentException();
        players.add(player);
        if (players.size() == 1) {
            firstPlayer = player;
            currentPlayer = player;
        }
    }

    /**
     * Add player player.
     *
     * @param nickname the nickname
     * @param avatar the avatar
     * @return the player
     * @throws MaxPlayerException            the max player exception
     * @throws PlayerAlreadyPresentException the player already present exception
     * @throws AvatarNotAvailableException   the avatar not available exception
     */
//TODO should be removed
    public synchronized Player addPlayer(String nickname, Avatar avatar) throws MaxPlayerException, PlayerAlreadyPresentException, AvatarNotAvailableException {
        Player tempPlayer = players.get(nickname);

        if (tempPlayer != null) {
            if (tempPlayer.isConnected())
                throw new PlayerAlreadyPresentException();
            else
                tempPlayer.connect();
        } else if (players.size() >= Rules.GAME_MAX_PLAYERS) {
            throw new MaxPlayerException();
        } else {
            tempPlayer = players.add(nickname);
            if (availableAvatars.contains(avatar))
                tempPlayer.setAvatar(avatar);
            else
                throw new AvatarNotAvailableException();
            availableAvatars.remove(avatar);
            if (players.size() == 1) {
                firstPlayer = tempPlayer;
                currentPlayer = tempPlayer;
            }
        }

        return tempPlayer;
    }

    /**
     * Add player player.
     *
     * @param clientID the client id
     * @param nickname the nickname
     * @param avatar the avatar
     * @return the player
     * @throws MaxPlayerException            the max player exception
     * @throws PlayerAlreadyPresentException the player already present exception
     * @throws PlayerReconnectionException   the player reconnection exception
     */
    public synchronized Player addPlayer(int clientID, String nickname, Avatar avatar) throws MaxPlayerException, PlayerAlreadyPresentException, PlayerReconnectionException {
        Player tempPlayer = players.get(nickname);

        if (tempPlayer != null) {
            if (tempPlayer.isConnected())
                throw new PlayerAlreadyPresentException();
            else {
                tempPlayer.connect();
                throw new PlayerReconnectionException();
            }
        } else if (players.size() >= Rules.GAME_MAX_PLAYERS) {
            throw new MaxPlayerException();
        } else {
            tempPlayer = players.add(clientID, nickname);
            if (!availableAvatars.contains(avatar))
                avatar = availableAvatars.get(0);

            tempPlayer.setAvatar(avatar);
            availableAvatars.remove(avatar);
            if (players.size() == 1) {
                firstPlayer = tempPlayer;
                currentPlayer = tempPlayer;
            }
        }

        return tempPlayer;
    }

    /**
     * Remove player.
     *
     * @param nickname the nickname
     */
    public void removePlayer(String nickname) {
        Player tempPlayer = players.get(nickname);
        if (tempPlayer != null) {
            players.remove(nickname);
        }
    }

    /**
     * Gets game board.
     *
     * @return the game board
     */
    /*
     * GameBoard-related methods
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * Sets game board.
     *
     * @param mapType the map type
     */
    public void setGameBoard(MapType mapType) {
        gameBoard = new GameBoard(mapType);
    }

    /**
     * Gets dead players.
     *
     * @return the dead players
     */
    public List<Player> getDeadPlayers() {
        List<Player> deadPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.isDead())
                deadPlayers.add(player);
        }
        return deadPlayers;
    }

    /**
     * Gets player by mark.
     *
     * @param damageMark the damage mark
     * @return the player by mark
     */
    public Player getPlayerByMark(DamageMark damageMark) {
        for (Player player : players) {
            if (player.getDamageMarkDelivered() == damageMark)
                return player;
        }
        return null;
    }


    /*
     * Match flow related methods
     */

    /**
     * Gets phase.
     *
     * @return the phase
     */
    public MatchPhase getPhase() {
        return phase;
    }

    /**
     * Sets phase.
     *
     * @param phase the phase
     */
    public void setPhase(MatchPhase phase) {
        this.phase = phase;
    }

    /**
     * Is ended boolean.
     *
     * @return the boolean
     */
    public boolean isEnded() {
        return phase == MatchPhase.ENDED;
    }

    /**
     * Gets turn.
     *
     * @return the turn
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Next turn.
     */
    public void nextTurn() {
        nextCurrentPlayer();
        turn++;
    }

    /**
     * Increase turn.
     */
    public void increaseTurn() {
        if (currentPlayer == firstPlayer)
            turn++;
    }


    /**
     * This method initializes the match and notifies the BeginMatchEvent which contains all the information to
     * instantiate the client copy of the match.
     *
     * @param mapType the map type
     */
    public void beginMatch(MapType mapType) {
        //todo preparation of the setup of the model, (weapons, power ups, deadPath....)
        currentPlayer = firstPlayer;
        gameBoard = new GameBoard(mapType);
    }

    /**
     * Gets avatar map.
     *
     * @return the avatar map
     */
    public Map<String, Avatar> getAvatarMap() {
        Map<String, Avatar> playerToAvatarMap = new HashMap<>();
        for (Player player : players) {
            playerToAvatarMap.put(player.getPlayerNickname(), player.getAvatar());
        }

        return playerToAvatarMap;
    }

    /**
     * Begin turn.
     */
//TODO who should create the event?
    public void beginTurn() {
        notifyObservers(new SpawnSelectionRequestEvent(currentPlayer, Arrays.asList(powerUpsDeck.drawCard(), powerUpsDeck.drawCard()), true));
    }

    /**
     * Gets available avatars.
     *
     * @return the available avatars
     */
    public ArrayList<Avatar> getAvailableAvatars() {
        return availableAvatars;
    }

    @Override
    public List<Observer> getObservers() {
        return observers;
    }

    @Override
    public void notifyObservers(AbstractModelEvent event) {
        synchronized (observers) {
            for (Observer observer : getObservers()) {
                event.setMatchID(matchID);
                observer.update(event);
            }
        }
    }

    @Override
    public void notifyObservers(List<AbstractModelEvent> eventsList) {
        synchronized (observers) {
            for (AbstractModelEvent event : eventsList)
                for (Observer observer : getObservers()) {
                    event.setMatchID(matchID);
                    observer.update(event);
                }
        }
    }

    /**
     * Generate client model local model.
     *
     * @param player the player
     * @return the local model
     */
    public LocalModel generateClientModel(Player player) {
        LocalModel clientModel = new LocalModel();

        clientModel.setPlayerName(player.getPlayerNickname());

        clientModel.setPlayerToAvatarMap(getAvatarMap());

        //kill track
        clientModel.setKillTrack(killTrack);

        // cards on map setup
        clientModel.setWeaponCardsSetup(gameBoard.getWeaponCardsOnMap());
        clientModel.setAmmoCardsSetup(gameBoard.getAmmoCardsOnMap());

        // weapons
        player.getWeapons().forEach(weaponCard ->
                clientModel.addWeapon(new WeaponCardClient(weaponCard)));
        if (player.weaponHandIsFull()) clientModel.setWeaponHandFull();

        //powerUps
        player.getPowerUps().forEach(powerUpCard ->
                clientModel.addPowerUp(new PowerUpCardClient(powerUpCard)));

        //ammos
        clientModel.setAmmos(player.getPlayerBoard().getAmmos());

        clientModel.setDamages(player.getPlayerBoard().getDamages());

        clientModel.setMarks(player.getPlayerBoard().getMarks());

        clientModel.setSkulls(player.getPlayerBoard().getSkulls());

        clientModel.setDeathScores(player.getPlayerBoard().getDeathScores());

        clientModel.setDeath(player.getPlayerBoard().isDeath());

        clientModel.setMapType(gameBoard.getMapType());

        Map<String, PlayerClient> opponentsInfo = new HashMap<>();
        Map<String, Coordinates> playerPositions = new HashMap<>();
        Map<String, Integer> points = new HashMap<>();
        players.forEach(serverPlayer -> {
            if (!serverPlayer.getPlayerNickname().equals(player.getPlayerNickname())) {
                PlayerClient opponent = new PlayerClient(serverPlayer);
                opponentsInfo.put(serverPlayer.getPlayerNickname(), opponent);
            }

            BoardSquare playerPosition = gameBoard.getPlayerPosition(serverPlayer);
            if (playerPosition != null)
                playerPositions.put(serverPlayer.getPlayerNickname(), playerPosition.getCoordinates());

            points.put(serverPlayer.getPlayerNickname(), serverPlayer.getPoints());
        });
        clientModel.setServerPlayerPositions(playerPositions);
        clientModel.setOpponentsInfo(opponentsInfo);

        return clientModel;
    }
}