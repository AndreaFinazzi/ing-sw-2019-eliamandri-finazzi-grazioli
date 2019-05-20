package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MovementNotAllowedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.WeaponCard;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Observable;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.List;

public class Match extends Observable {

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
    };

    private GameBoard map;
    private MatchPhase phase;
    private Player currentPlayer;
    private Player firstPlayer;
    private int turn = 0;

    public Match() {
        phase = MatchPhase.INITIALIZATION;
        map = new GameBoard(MapType.ONE);
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

    public void nextCurrentPlayer() {
        int index = players.indexOf(currentPlayer);
        currentPlayer = players.get((index + 1) % players.size());
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player.AbstractPlayerList getPlayers() {
        return players;
    }

    public List<String> getPlayersNickname(){
        ArrayList<String> playersNickname = new ArrayList<>();
        for(Player player : players){
            playersNickname.add(player.getPlayerNickname());
        }
        return playersNickname;
    }

    public Player getPlayer(String nickname) {
        return getPlayer(nickname);
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

    public void addPlayer(String nickname) throws MaxPlayerException, PlayerAlreadyPresentException {
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
            if (players.size() == 1) {
                firstPlayer = tempPlayer;
                currentPlayer = tempPlayer;
            }
        }

        if (players.size() == Rules.GAME_MAX_PLAYERS) {

        }
    }

    public void removePlayer(String nickname) {
        Player tempPlayer = players.get(nickname);
        if (tempPlayer != null) {
            players.remove(nickname);
        }
    }

    public void weaponToUseSelected(Player player, String weaponSelected){
        if (player != currentPlayer){
            //TODO throw exception or generate event
            return;
        }
        WeaponCard weaponCard = currentPlayer.getWeaponByName(weaponSelected);
        if (weaponCard == null){
            //TODO throw exception or generate event
            return;
        }
        if (!weaponCard.isLoaded()){
            //TODO throw exception or generate event
            return;
        }

    }

    public void playerMovement(Player player, List<Coordinates> path) {
        try {
            notifyObservers(map.playerMovement(player, path));
        } catch (MovementNotAllowedException e){
            //TODO generate invalid move exception
        }
    }

    /*
     * GameBoard-related methods
     */

    public GameBoard getMap() {
        return map;
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

    public int getTurn() {
        return turn;
    }

    public void increaseTurn() {
        turn++;
    }

    public void beginTurn() {
        notifyObservers(currentPlayer.createBeginTurnEvent());
    }

    public void endTurn() {
        notifyObservers(currentPlayer.createEndTurnEvent());
    }




}