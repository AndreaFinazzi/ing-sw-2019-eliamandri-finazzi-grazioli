package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.AbstractModelEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.request.NotAllowedPlayEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.AvatarNotAvailableException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.PowerUpsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observable;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observer;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            for (Player player: players)
                cont++;
            return cont;
        }

    };

    private int matchID;

    private ArrayList<Observer> observers = new ArrayList<>();

    private GameBoard gameBoard;
    private MatchPhase phase;
    private Player currentPlayer;
    private Player firstPlayer;
    private PowerUpsDeck powerUpsDeck;
    private WeaponsDeck weaponsDeck;
    private ArrayList<Avatar> availableAvatars = new ArrayList<>(Arrays.asList(Avatar.values()));
    private int turn = 0;

    public Match() {
        phase = MatchPhase.INITIALIZATION;
        powerUpsDeck = new PowerUpsDeck();
        weaponsDeck = new WeaponsDeck();
    }


    /*
     * Player-related methods
     */

    public ArrayList<Player> getPlayersOnSquare(BoardSquare square) {
        ArrayList<Player> onSquare = new ArrayList<>();
        for (Player player : players) {
            if (player.getPosition() == square) {
                onSquare.add(player);
            }
        }
        return onSquare;
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

    public void nextCurrentPlayer() {
        //TODO Should be debugged
        int index = players.indexOf(currentPlayer);

        currentPlayer = players.get((index + 1) % players.size());
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

    public ArrayList<String> getPlayersNicknames() {
        ArrayList<String> tempList = new ArrayList<>();
        for (Player player : players) {
            tempList.add(player.getPlayerNickname());
        }

        return tempList;
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

    public synchronized Player addPlayer(int clientID, String nickname, Avatar avatar) throws MaxPlayerException, PlayerAlreadyPresentException, AvatarNotAvailableException {
        Player tempPlayer = players.get(nickname);

        if (tempPlayer != null) {
            if (tempPlayer.isConnected())
                throw new PlayerAlreadyPresentException();
            else
                tempPlayer.connect();
        } else if (players.size() >= Rules.GAME_MAX_PLAYERS) {
            throw new MaxPlayerException();
        } else {
            tempPlayer = players.add(clientID, nickname);
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


    public void removePlayer(String nickname) {
        Player tempPlayer = players.get(nickname);
        if (tempPlayer != null) {
            players.remove(nickname);
        }
    }

    public void weaponToUseSelected(Player player, String weaponSelected) {
        if (player != currentPlayer) {
            //TODO throw exception or generate event
            return;
        }
        WeaponCard weaponCard = currentPlayer.getWeaponByName(weaponSelected);
        if (weaponCard == null) {
            //TODO throw exception or generate event
            return;
        }
        if (!weaponCard.isLoaded()) {
            //TODO throw exception or generate event
            return;
        }

    }

    /**
     * Given a a boardSquare list and a player the method performs the movement of the player along the square list notifying
     * it or notifies the client that the provided path is not valid.
     * @param player
     * @param path
     */
    public void playerMovement(Player player, List<Coordinates> path) {
        if (path != null && path.size() > 0)
            notifyObservers(gameBoard.playerMovement(player, path));
        else
            notifyObservers(new NotAllowedPlayEvent(player));
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

    public void increaseTurn() {
        nextCurrentPlayer();
        turn++;
    }


    //TODO who should create the event?
    public void beginTurn() {
        notifyObservers(currentPlayer.createBeginTurnEvent());
    }

    public void endTurn() {
        notifyObservers(currentPlayer.createEndTurnEvent());
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
}