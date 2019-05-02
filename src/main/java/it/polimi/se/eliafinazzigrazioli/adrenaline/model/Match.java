package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import java.util.ArrayList;
import java.util.List;

public class Match {

    private List<Player> players;
    private GameBoard map;
    private MatchPhase phase;
    private Player currentPlayer;
    private int turn;


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
}
