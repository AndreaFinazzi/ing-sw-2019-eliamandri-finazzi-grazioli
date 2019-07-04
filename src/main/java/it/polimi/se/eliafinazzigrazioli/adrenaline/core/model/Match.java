package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.LocalModel;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PlayerClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.PowerUpCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.WeaponCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.PlayerShotEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.SkullRemovalEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.SpawnSelectionRequestEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.PowerUpsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects.AmmoCardsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observable;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.*;

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

    private int matchID;

    private ArrayList<Observer> observers = new ArrayList<>();

    private GameBoard gameBoard;

    KillTrack killTrack;
    private MatchPhase phase;
    private Player currentPlayer;
    private Player firstPlayer;
    private PowerUpsDeck powerUpsDeck;
    private WeaponsDeck weaponsDeck;
    private AmmoCardsDeck ammoCardsDeck;
    private ArrayList<Avatar> availableAvatars = new ArrayList<>(Arrays.asList(Avatar.values()));

    private int turn = 0;

    public Match() {
        phase = MatchPhase.INITIALIZATION;
        killTrack = new KillTrack(Rules.GAME_MAX_KILL_TRACK_SKULLS);
        powerUpsDeck = new PowerUpsDeck();
        weaponsDeck = new WeaponsDeck();
        try {
            ammoCardsDeck = new AmmoCardsDeck();
        } catch (WeaponFileNotFoundException e) {
            ammoCardsDeck = null;
        }
    }

    public List<AbstractModelEvent> skullsAssignment() {
        List<AbstractModelEvent> events = new ArrayList<>();
        for (Player deadPlayer: getDeadPlayers()) {
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
        if (killTrack.isFull())
            phase = MatchPhase.FINAL_FRENZY;
        return events;
    }





    public void updatePoints(Map<String, Integer> playerPoints) {
        for (Map.Entry<String, Integer> points: playerPoints.entrySet())
            players.get(points.getKey()).addPoints(points.getValue());
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public PowerUpsDeck getPowerUpsDeck() {
        return powerUpsDeck;
    }

    public WeaponsDeck getWeaponsDeck() {
        return weaponsDeck;
    }

    public AmmoCardsDeck getAmmoCardsDeck() {
        return ammoCardsDeck;
    }

    public void nextCurrentPlayer() {
        int index = players.indexOf(currentPlayer);
        currentPlayer = players.get((index + 1) % players.size());
    }

    public Player getNextPlayer() {
        int index = players.indexOf(currentPlayer);
        return players.get((index + 1) % players.size());
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player.AbstractPlayerList getPlayers() {
        return players;
    }

    public List<String> getPlayersNickname() {
        ArrayList<String> playersNickname = new ArrayList<>();
        for (Player player : players) {
            playersNickname.add(player.getPlayerNickname());
        }
        return playersNickname;
    }

    public Player getPlayer(String nickname) {
        return players.get(nickname);
    }

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

    public void removePlayer(String nickname) {
        Player tempPlayer = players.get(nickname);
        if (tempPlayer != null) {
            players.remove(nickname);
        }
    }

    /*
     * GameBoard-related methods
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(MapType mapType) {
        gameBoard = new GameBoard(mapType);
    }

    public List<Player> getDeadPlayers() {
        List<Player> deadPlayers = new ArrayList<>();
        for (Player player: players) {
            if (player.isDead())
                deadPlayers.add(player);
        }
        return deadPlayers;
    }

    public Player getPlayerByMark(DamageMark damageMark) {
        for (Player player: players){
            if (player.getDamageMarkDelivered() == damageMark)
                return player;
        }
        return null;
    }


    /*
     * Match flow related methods
     */

    public MatchPhase getPhase() {
        return phase;
    }

    public void setPhase(MatchPhase phase) {
        this.phase = phase;
    }

    public boolean isEnded() {
        return phase == MatchPhase.ENDED;
    }

    public int getTurn() {
        return turn;
    }

    public void nextTurn() {
        nextCurrentPlayer();
        turn++;
    }

    public void increaseTurn() {
        if (currentPlayer == firstPlayer)
            turn++;
    }


    /**
     * This method initializes the match and notifies the BeginMatchEvent which contains all the information to
     * instantiate the client copy of the match.
     */
    public void beginMatch(MapType mapType) {
        //todo preparation of the setup of the model, (weapons, power ups, deadPath....)
        currentPlayer = firstPlayer;
        gameBoard = new GameBoard(mapType);
    }

    public Map<String, Avatar> getAvatarMap() {
        Map<String, Avatar> playerToAvatarMap = new HashMap<>();
        for (Player player : players) {
            playerToAvatarMap.put(player.getPlayerNickname(), player.getAvatar());
        }

        return playerToAvatarMap;
    }

    //TODO who should create the event?
    public void beginTurn() {
        notifyObservers(new SpawnSelectionRequestEvent(currentPlayer, Arrays.asList(powerUpsDeck.drawCard(), powerUpsDeck.drawCard()), true));
    }

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

    public LocalModel generateClientModel(Player player) {
        LocalModel clientModel = new LocalModel();

        clientModel.setPlayerName(player.getPlayerNickname());

        clientModel.setPlayerToAvatarMap(getAvatarMap());

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
        clientModel.setOverkill(player.getPlayerBoard().isOverkill());

        clientModel.setMapType(gameBoard.getMapType());

        Map<String, PlayerClient> opponentsInfo = new HashMap<>();
        Map<String, Coordinates> playerPositions = new HashMap<>();
        players.forEach(serverPlayer -> {
            if (!serverPlayer.getPlayerNickname().equals(player.getPlayerNickname())) {
                PlayerClient opponent = new PlayerClient(serverPlayer);
                opponentsInfo.put(serverPlayer.getPlayerNickname(), opponent);
            }

            BoardSquare playerPosition = gameBoard.getPlayerPosition(serverPlayer);
            if (playerPosition != null)
                playerPositions.put(serverPlayer.getPlayerNickname(), playerPosition.getCoordinates());
        });
        clientModel.setServerPlayerPositions(playerPositions);
        clientModel.setOpponentsInfo(opponentsInfo);

        return clientModel;
    }
}