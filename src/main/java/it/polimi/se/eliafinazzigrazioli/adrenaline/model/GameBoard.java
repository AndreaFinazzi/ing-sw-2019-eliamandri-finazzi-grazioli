package it.polimi.se.eliafinazzigrazioli.adrenaline.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.utils.Coordinates;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameBoard {

    private BoardSquare[][] squaresMatrix;
    private int width;
    private int height;
    private Match match;

    public GameBoard(MapType mapType, Match match) {
        if (mapType.equals (MapType.ONE)) {
            width = 4;
            height = 3;
            this.match = match;
            squaresMatrix = new BoardSquare[height][width];
            squaresMatrix[0][0] = new GenericBoardSquare (Room.RED, new Coordinates (0,0),
                    InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.DOOR, InterSquareLink.WALL, match);
            squaresMatrix[1][0] = new SpawnBoardSquare(Room.RED, new Coordinates (1,0),
                    InterSquareLink.SAMEROOM, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.WALL, match);
            squaresMatrix[0][1] = new GenericBoardSquare (Room.BLUE, new Coordinates (0,1),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.DOOR, match);
            squaresMatrix[0][2] = new SpawnBoardSquare (Room.BLUE, new Coordinates (0,2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, match);
            squaresMatrix[1][1] = new GenericBoardSquare (Room.PURPLE, new Coordinates (1,1),
                    InterSquareLink.DOOR, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL, match);
            squaresMatrix[1][2] = new GenericBoardSquare (Room.PURPLE, new Coordinates (1,2),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, match);
            squaresMatrix[1][3] = new SpawnBoardSquare (Room.YELLOW, new Coordinates (1,3),
                    InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.DOOR, match);
            squaresMatrix[2][3] = new SpawnBoardSquare (Room.YELLOW, new Coordinates (2,3),
                    InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.DOOR, match);
            squaresMatrix[2][0] = new GenericBoardSquare (Room.GRAY, new Coordinates (2,0),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL, match);
            squaresMatrix[2][1] = new GenericBoardSquare (Room.GRAY, new Coordinates (2,1),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM, match);
            squaresMatrix[2][2] = new GenericBoardSquare (Room.GRAY, new Coordinates (2,2),
                    InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, match);
            squaresMatrix[0][3] = null;
        }
    }



    public List<BoardSquare> getRoomSquares(Room room){
        List<BoardSquare> roomSquares = new ArrayList<>();
        for(int i=0; i<height; i++){
            for (int j=0; j<width; j++){
                BoardSquare square = squaresMatrix[i][j];
                if (square != null && square.getRoom()==room){
                    roomSquares.add(square);
                }
            }
        }
        return roomSquares;
    }

    public List<BoardSquare> getVisibleSquares(BoardSquare referenceSquare, boolean notVisible){
        List<BoardSquare> visibleSquares = new ArrayList<>();
        visibleSquares.addAll(getRoomSquares(referenceSquare.getRoom()));
        Coordinates coord = referenceSquare.getCoordinates();
        int x;
        int y;
        if (referenceSquare.getNorth() == InterSquareLink.DOOR){
            x = coord.getXCoordinates();
            y = coord.getYCoordinates()+1;
            visibleSquares.addAll(getRoomSquares(squaresMatrix[y][x].getRoom()));
        }
        if (referenceSquare.getSouth() == InterSquareLink.DOOR){
            x = coord.getXCoordinates();
            y = coord.getYCoordinates()-1;
            visibleSquares.addAll(getRoomSquares(squaresMatrix[y][x].getRoom()));
        }
        if (referenceSquare.getEast() == InterSquareLink.DOOR){
            x = coord.getXCoordinates()+1;
            y = coord.getYCoordinates();
            visibleSquares.addAll(getRoomSquares(squaresMatrix[y][x].getRoom()));
        }
        if (referenceSquare.getWest() == InterSquareLink.DOOR){
            x = coord.getXCoordinates()-1;
            y = coord.getYCoordinates();
            visibleSquares.addAll(getRoomSquares(squaresMatrix[y][x].getRoom()));
        }

        if(notVisible){
            List<BoardSquare> notVisibleSquares = new ArrayList<>();
            for (int i=0; i<height; i++){
                for (int j=0; j<width; j++) {
                    if (!visibleSquares.contains(squaresMatrix[i][j])){
                        notVisibleSquares.add(squaresMatrix[i][j]);
                    }
                }
            }
            return notVisibleSquares;
        }
        return visibleSquares;
    }

    public List<BoardSquare> getVisibleSquares(Player referencePlayer, boolean notVisible){
        return getVisibleSquares(referencePlayer.getPosition(), notVisible);
    }

    public List<Player> getVisiblePlayers(BoardSquare referenceSquare, boolean notVisible){
        List<Player> visiblePlayers = new ArrayList<>();
        List<BoardSquare> visibleSquares = getVisibleSquares(referenceSquare, notVisible);
        for (BoardSquare visible:visibleSquares){
            visiblePlayers.addAll(visible.getPlayers());
        }
        return visiblePlayers;
    }

    public List<Player> getVisiblePlayers(Player referencePlayer, boolean notVisible){
        BoardSquare referenceSquare = referencePlayer.getPosition();
        return getVisiblePlayers(referenceSquare, notVisible);
    }

    private Set<BoardSquare> getOneStepReachableSquares(BoardSquare referenceSquare) throws Exception{ //TODO define exception type
        if(referenceSquare == null){
            throw new Exception();
        }
        Set<BoardSquare> reachableSquares = new HashSet<>();
        Coordinates coord = referenceSquare.getCoordinates();
        int x;
        int y;
        if (referenceSquare.getNorth() != InterSquareLink.WALL){
            x = coord.getXCoordinates();
            y = coord.getYCoordinates()+1;
            reachableSquares.add(squaresMatrix[y][x]);
        }
        if (referenceSquare.getSouth() != InterSquareLink.WALL){
            x = coord.getXCoordinates();
            y = coord.getYCoordinates()-1;
            reachableSquares.add(squaresMatrix[y][x]);
        }
        if (referenceSquare.getEast() != InterSquareLink.WALL){
            x = coord.getXCoordinates()+1;
            y = coord.getYCoordinates();
            reachableSquares.add(squaresMatrix[y][x]);
        }
        if (referenceSquare.getWest() != InterSquareLink.WALL){
            x = coord.getXCoordinates()-1;
            y = coord.getYCoordinates();
            reachableSquares.add(squaresMatrix[y][x]);
        }

        return reachableSquares;

    }

    //returns all the squares between maxDistance and minDistance, if minDistance=0 referenceSquare is included, if minDistance=1 reference square is excluded
    public List<BoardSquare> getSquaresByDistance(BoardSquare referenceSquare, int maxDistance, int minDistance) throws Exception{ //TODO define exception type
        if(maxDistance < minDistance){
            throw new Exception();
        }
        int stepsTaken=0;
        Set<BoardSquare> newBoundSet = new HashSet<>();
        Set<BoardSquare> belowMinDistanceSquares = new HashSet<>();
        Set<BoardSquare> belowMaxDistanceSquares = new HashSet<>();
        Set<BoardSquare> boundSquares = new HashSet<>();
        boundSquares.add(referenceSquare);
        while (stepsTaken <= maxDistance){
            for (BoardSquare bound:boundSquares) {
                newBoundSet.addAll(getOneStepReachableSquares(bound));
                newBoundSet.removeAll(belowMaxDistanceSquares);
                newBoundSet.removeAll(boundSquares);
            }
            belowMaxDistanceSquares.addAll(boundSquares);
            if(stepsTaken < minDistance){
                belowMinDistanceSquares.addAll(boundSquares);
            }
            boundSquares = new HashSet<>(newBoundSet);
            newBoundSet.clear();
            stepsTaken++;
        }

        belowMaxDistanceSquares.removeAll(belowMinDistanceSquares);
        return new ArrayList<>(belowMaxDistanceSquares);

    }

    public List<Player> getPlayersByDistance(BoardSquare referenceSquare, int maxDistance, int minDistance) throws Exception { //TODO define exception type
        List<Player> playersByDistance = new ArrayList<>();
        List<BoardSquare> squaresByDistance = getSquaresByDistance(referenceSquare, maxDistance, minDistance);
        for(BoardSquare square:squaresByDistance){
            playersByDistance.addAll(square.getPlayers());
        }
        return playersByDistance;
    }

    public List<BoardSquare> getSquaresByDistance(Player referencePlayer, int maxDistance, int minDistance) throws Exception { //TODO define exception type
        return getSquaresByDistance(referencePlayer.getPosition(), maxDistance, minDistance);
    }

    public List<Player> getPlayersByDistance(Player referencePlayer, int maxDistance, int minDistance) throws Exception { //TODO define exception type
        return getPlayersByDistance(referencePlayer.getPosition(), maxDistance, minDistance);
    }

    public List<BoardSquare> getSquaresOnCardinalDirections(BoardSquare referenceSquare){
        Coordinates referenceCoordinates = referenceSquare.getCoordinates();
        int x = referenceCoordinates.getXCoordinates();
        int y = referenceCoordinates.getYCoordinates();
        List<BoardSquare> squaresByDirection = new ArrayList<>();
        squaresByDirection.add(referenceSquare);
        //check north
        for(int i=y+1; i<height; i++){
            squaresByDirection.add(squaresMatrix[i][x]);
        }
        //check east
        for(int i=x+1; i<width; i++){
            squaresByDirection.add(squaresMatrix[y][i]);
        }
        //check south
        for(int i=y-1; i>=0; i--){
            squaresByDirection.add(squaresMatrix[i][x]);
        }
        //check west
        for(int i=y-1; i>=0; i--){
            squaresByDirection.add(squaresMatrix[y][i]);
        }
        return squaresByDirection;
    }

    public List<BoardSquare> getSquaresOnCardinalDirections(Player referencePlayer){
        return getSquaresOnCardinalDirections(referencePlayer.getPosition());
    }

    public List<Player> getPlayersOnCardinalDirections(BoardSquare referenceSquare){
        List<BoardSquare> squaresByDirection = getSquaresOnCardinalDirections(referenceSquare);
        List<Player> playersByDirection = new ArrayList<>();
        for (BoardSquare square:squaresByDirection){
            playersByDirection.addAll(square.getPlayers());
        }
        return playersByDirection;
    }

    public List<Player> getPlayersOnCardinalDirections(Player referencePlayer){
        return getPlayersOnCardinalDirections(referencePlayer.getPosition());
    }




}
