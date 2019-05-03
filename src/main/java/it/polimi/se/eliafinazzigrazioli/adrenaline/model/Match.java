package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model.MaxPlayerException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.exceptions.model.PlayerAlreadyPresentException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Rules;

import java.util.ArrayList;
import java.util.List;

public class Match {

    private List<Player> players;
    private GameBoard map;
    private MatchPhase phase;
    private Player currentPlayer;
    private Player firstPlayer;
    private int turn;

    public Match() {
        phase = MatchPhase.INITIALIZATION;
        players = new ArrayList<> ();
    }

    public List<Player> getPlayersOnSquare(BoardSquare square){
        List<Player> onSquare = new ArrayList<>();
        for (Player player:players){
            if (player.getPosition() == square){
                onSquare.add(player);
            }
        }
        return onSquare;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameBoard getMap() {
        return map;
    }

    public void nextCurrentPlayer() {
        int index = players.indexOf (currentPlayer);
        currentPlayer = players.get ((index+1)%players.size ());
    }

    public void addPlayers(Player player) throws MaxPlayerException, PlayerAlreadyPresentException {
        if (players.size () >= Rules.GAME_MAX_PLAYERS)
            throw new MaxPlayerException();
        if (players.contains (player))
            throw new PlayerAlreadyPresentException ();
        players.add (player);
        if (players.size () == 1) {
            firstPlayer = player;
            currentPlayer = player;
        }
    }

    public MatchPhase getPhase() {
        return phase;
    }

    public void setPhase(MatchPhase phase) {
        this.phase = phase;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public int getTurn() {
        return turn;
    }

    public void increaseTurn() {
        turn++;
    }
}
