package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.PlayerMovementEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.MovementNotAllowedException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.*;

public class GameBoard {

    private BoardSquare[][] squaresMatrix;
    private int x_max = Rules.GAME_BOARD_X_MAX;
    private int y_max = Rules.GAME_BOARD_Y_MAX;
    private Map<Player,BoardSquare> playerPositions;

    public GameBoard(MapType mapType) {
        if (mapType.equals (MapType.ONE)) {
            squaresMatrix = new BoardSquare[x_max][y_max];
            squaresMatrix[0][2] = new GenericBoardSquare (Room.RED, new Coordinates (0,2),
                    InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.DOOR, InterSquareLink.WALL);
            squaresMatrix[0][1] = new SpawnBoardSquare(Room.RED, new Coordinates (0,1),
                    InterSquareLink.SAMEROOM, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.WALL);
            squaresMatrix[1][2] = new GenericBoardSquare (Room.BLUE, new Coordinates (1,2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.DOOR);
            squaresMatrix[2][2] = new SpawnBoardSquare (Room.BLUE, new Coordinates (2,2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[1][1] = new GenericBoardSquare (Room.PURPLE, new Coordinates (1,1),
                    InterSquareLink.DOOR, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[2][1] = new GenericBoardSquare (Room.PURPLE, new Coordinates (2,1),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
            squaresMatrix[3][1] = new GenericBoardSquare (Room.YELLOW, new Coordinates (3,1),
                    InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.DOOR);
            squaresMatrix[3][0] = new SpawnBoardSquare (Room.YELLOW, new Coordinates (3,0),
                    InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.DOOR);
            squaresMatrix[0][0] = new GenericBoardSquare (Room.GRAY, new Coordinates (0,0),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[1][0] = new GenericBoardSquare (Room.GRAY, new Coordinates (1,0),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM);
            squaresMatrix[2][0] = new GenericBoardSquare (Room.GRAY, new Coordinates (2,0),
                    InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
            squaresMatrix[3][2] = null;

        }

        if (mapType.equals (MapType.TWO)) {
            squaresMatrix = new BoardSquare[x_max][y_max];
            squaresMatrix[0][0] = null;
            squaresMatrix[0][1] = new SpawnBoardSquare (Room.RED, new Coordinates (0,1),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[1][1] = new GenericBoardSquare (Room.RED, new Coordinates (1,1),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[1][0] = new GenericBoardSquare (Room.GRAY, new Coordinates (1,0),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[2][0] = new GenericBoardSquare (Room.GRAY, new Coordinates (2,0),
                    InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
            squaresMatrix[3][0] = new SpawnBoardSquare (Room.YELLOW, new Coordinates (3,0),
                    InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.DOOR);
            squaresMatrix[3][1] = new GenericBoardSquare (Room.YELLOW, new Coordinates (3,1),
                    InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.DOOR);
            squaresMatrix[2][1] = new GenericBoardSquare (Room.PURPLE, new Coordinates (2,1),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL);
            squaresMatrix[0][2] = new GenericBoardSquare (Room.BLUE, new Coordinates (0,2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[1][2] = new GenericBoardSquare (Room.BLUE, new Coordinates (1,2),
                    InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM);
            squaresMatrix[2][2] = new GenericBoardSquare (Room.BLUE, new Coordinates (2,2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[3][2] = null;
        }

        if (mapType.equals (MapType.THREE)) {
            squaresMatrix = new BoardSquare[x_max][y_max];
            squaresMatrix[0][0] = null;
            squaresMatrix[0][1] = new SpawnBoardSquare (Room.RED, new Coordinates (0,1),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[1][1] = new GenericBoardSquare (Room.RED, new Coordinates (1,1),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[1][0] = new GenericBoardSquare (Room.GRAY, new Coordinates (1,0),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL);
            squaresMatrix[2][0] = new GenericBoardSquare (Room.YELLOW, new Coordinates (2,0),
                    InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.DOOR);
            squaresMatrix[3][0] = new SpawnBoardSquare (Room.YELLOW, new Coordinates (3,0),
                    InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[3][1] = new GenericBoardSquare (Room.YELLOW, new Coordinates (3,1),
                    InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[2][1] = new GenericBoardSquare (Room.YELLOW, new Coordinates (2,1),
                    InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[0][2] = new GenericBoardSquare (Room.BLUE, new Coordinates (0,2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[1][2] = new GenericBoardSquare (Room.BLUE, new Coordinates (1,2),
                    InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM);
            squaresMatrix[2][2] = new GenericBoardSquare (Room.BLUE, new Coordinates (2,2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
            squaresMatrix[3][2] = new SpawnBoardSquare (Room.GREEN, new Coordinates (3,2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR);
        }

        if (mapType.equals (MapType.FOUR)) {
            if (mapType.equals (MapType.THREE)) {
                squaresMatrix = new BoardSquare[x_max][y_max];
                squaresMatrix[0][0] = new GenericBoardSquare (Room.GRAY, new Coordinates (0,0),
                        InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
                squaresMatrix[0][1] = new SpawnBoardSquare (Room.RED, new Coordinates (0,1),
                        InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
                squaresMatrix[1][1] = new GenericBoardSquare (Room.PURPLE, new Coordinates (1,1),
                        InterSquareLink.DOOR, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.WALL);
                squaresMatrix[1][0] = new GenericBoardSquare (Room.GRAY, new Coordinates (1,0),
                        InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
                squaresMatrix[2][0] = new GenericBoardSquare (Room.YELLOW, new Coordinates (2,0),
                        InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.DOOR);
                squaresMatrix[3][0] = new SpawnBoardSquare (Room.YELLOW, new Coordinates (3,0),
                        InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
                squaresMatrix[3][1] = new GenericBoardSquare (Room.YELLOW, new Coordinates (3,1),
                        InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
                squaresMatrix[2][1] = new GenericBoardSquare (Room.YELLOW, new Coordinates (2,1),
                        InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
                squaresMatrix[0][2] = new GenericBoardSquare (Room.RED, new Coordinates (0,2),
                        InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.DOOR, InterSquareLink.WALL);
                squaresMatrix[1][2] = new GenericBoardSquare (Room.BLUE, new Coordinates (1,2),
                        InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
                squaresMatrix[2][2] = new GenericBoardSquare (Room.BLUE, new Coordinates (2,2),
                        InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
                squaresMatrix[3][2] = new SpawnBoardSquare (Room.GREEN, new Coordinates (3,2),
                        InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR);
            }

        }
        playerPositions = new HashMap<>();
    }



    public void movePlayer(Player player, BoardSquare destination){
        playerPositions.put(player, destination);
    }

    public PlayerMovementEvent playerMovement(Player player, List<Coordinates> coordinatesPath) throws MovementNotAllowedException {
        if (player.getPlayerBoard().getNumberOfMovementsAllowed() < coordinatesPath.size()){
            throw new MovementNotAllowedException("MovementNotAllowedException: number of steps exceeds allowed movements");
            //TODO define appropriate message for the exception
        }
        List<BoardSquare> boardSquaresPath = new ArrayList<>();
        BoardSquare playerPostion = getPlayerPosition(player);
        coordinatesPath.forEach(coordinates -> {
            boardSquaresPath.add(getBoardSquareByCoordinates(coordinates));
        });
        for (BoardSquare boardSquare:boardSquaresPath){
            if (!getOneStepReachableSquares(playerPostion).contains(boardSquare))
                throw new MovementNotAllowedException("MovementNotAllowedExeption: provided path is not valid");
                //TODO define appropriate message for the exception
            playerPostion = boardSquare;
        }
        movePlayer(player, playerPostion);

        return new PlayerMovementEvent(player.getPlayerNickname(), coordinatesPath);
    }

    public List<BoardSquare> getRoomSquares(Room room){
        List<BoardSquare> roomSquares = new ArrayList<BoardSquare>();
        for (int i = 0; i < x_max; i++) {
            for (int j = 0; j < y_max; j++) {
                BoardSquare square = squaresMatrix[i][j];
                if (square != null && square.getRoom()==room){
                    roomSquares.add(square);
                }
            }
        }
        return roomSquares;
    }



    public List<Player> getRoomPlayers(Room room){
        List<BoardSquare> roomSquares = getRoomSquares(room);
        List<Player> roomPlayers = new ArrayList<>();
        for (BoardSquare boardSquare:roomSquares)
            roomPlayers.addAll(getPlayersOnSquare(boardSquare));
        return roomPlayers;
    }

    public List<BoardSquare> getVisibleSquares(BoardSquare referenceSquare, boolean notVisible){
        List<BoardSquare> visibleSquares = new ArrayList<>();
        visibleSquares.addAll(getRoomSquares(referenceSquare.getRoom()));
        Coordinates coord = referenceSquare.getCoordinates();
        int x;
        int y;
        if (referenceSquare.getNorth() == InterSquareLink.DOOR){
            x = coord.getXCoordinate();
            y = coord.getYCoordinate()+1;
            visibleSquares.addAll(getRoomSquares(squaresMatrix[x][y].getRoom()));
        }
        if (referenceSquare.getSouth() == InterSquareLink.DOOR){
            x = coord.getXCoordinate();
            y = coord.getYCoordinate()-1;
            visibleSquares.addAll(getRoomSquares(squaresMatrix[x][y].getRoom()));
        }
        if (referenceSquare.getEast() == InterSquareLink.DOOR){
            x = coord.getXCoordinate()+1;
            y = coord.getYCoordinate();
            visibleSquares.addAll(getRoomSquares(squaresMatrix[x][y].getRoom()));
        }
        if (referenceSquare.getWest() == InterSquareLink.DOOR){
            x = coord.getXCoordinate()-1;
            y = coord.getYCoordinate();
            visibleSquares.addAll(getRoomSquares(squaresMatrix[x][y].getRoom()));
        }

        if(notVisible){
            List<BoardSquare> notVisibleSquares = new ArrayList<>();
            for (int i = 0; i < x_max; i++) {
                for (int j = 0; j < y_max; j++) {
                    if (!visibleSquares.contains(squaresMatrix[i][j])){
                        if (squaresMatrix[i][j] != null)
                            notVisibleSquares.add(squaresMatrix[i][j]);
                    }
                }
            }
            return notVisibleSquares;
        }
        return visibleSquares;
    }

    public List<Room> getVisibleRooms(BoardSquare referenceSquare, boolean notVisible){
        List<Room> visibleRooms = new ArrayList<>();
        int x = referenceSquare.getCoordinates().getXCoordinate();
        int y = referenceSquare.getCoordinates().getYCoordinate();
        if (referenceSquare.getNorth() == InterSquareLink.DOOR)
            visibleRooms.add(squaresMatrix[x][y+1].getRoom());
        if (referenceSquare.getSouth() == InterSquareLink.DOOR)
            visibleRooms.add(squaresMatrix[x][y-1].getRoom());
        if (referenceSquare.getEast() == InterSquareLink.DOOR)
            visibleRooms.add(squaresMatrix[x+1][y].getRoom());
        if (referenceSquare.getWest() == InterSquareLink.DOOR)
            visibleRooms.add(squaresMatrix[x-1][y].getRoom());
        return visibleRooms;
    }

    public List<Player> getVisiblePlayers(BoardSquare referenceSquare, boolean notVisible){
        List<Player> visiblePlayers = new ArrayList<>();
        List<BoardSquare> visibleSquares = getVisibleSquares(referenceSquare, notVisible);
        for (BoardSquare visible:visibleSquares){
            visiblePlayers.addAll(getPlayersOnSquare(visible));
        }
        return visiblePlayers;
    }




    private Set<BoardSquare> getOneStepReachableSquares(BoardSquare referenceSquare){ //TODO define exception type
        Set<BoardSquare> reachableSquares = new HashSet<>();
        Coordinates coord = referenceSquare.getCoordinates();
        int x;
        int y;
        if (referenceSquare.getNorth() != InterSquareLink.WALL){
            x = coord.getXCoordinate();
            y = coord.getYCoordinate()+1;
            reachableSquares.add(squaresMatrix[x][y]);
        }
        if (referenceSquare.getSouth() != InterSquareLink.WALL){
            x = coord.getXCoordinate();
            y = coord.getYCoordinate()-1;
            reachableSquares.add(squaresMatrix[x][y]);
        }
        if (referenceSquare.getEast() != InterSquareLink.WALL){
            x = coord.getXCoordinate()+1;
            y = coord.getYCoordinate();
            reachableSquares.add(squaresMatrix[x][y]);
        }
        if (referenceSquare.getWest() != InterSquareLink.WALL){
            x = coord.getXCoordinate()-1;
            y = coord.getYCoordinate();
            reachableSquares.add(squaresMatrix[x][y]);
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
            playersByDistance.addAll(getPlayersOnSquare(square));
        }
        return playersByDistance;
    }

    public List<BoardSquare> getSquaresByDistance(Player referencePlayer, int maxDistance, int minDistance) throws Exception { //TODO define exception type
        return getSquaresByDistance(getPlayerPosition(referencePlayer), maxDistance, minDistance);
    }

    public List<Player> getPlayersByDistance(Player referencePlayer, int maxDistance, int minDistance) throws Exception { //TODO define exception type
        return getPlayersByDistance(getPlayerPosition(referencePlayer), maxDistance, minDistance);
    }

    public List<BoardSquare> getSquaresOnCardinalDirections(BoardSquare referenceSquare){
        Coordinates referenceCoordinates = referenceSquare.getCoordinates();
        int x = referenceCoordinates.getXCoordinate();
        int y = referenceCoordinates.getYCoordinate();
        List<BoardSquare> squaresByDirection = new ArrayList<>();
        squaresByDirection.add(referenceSquare);
        //check north vertical
        for (int i=0;i<y_max;i++) {
            if (squaresMatrix[x][i] != null && i!=y)
                squaresByDirection.add(squaresMatrix[x][i]);
        }
        //check horizontal
        for (int i=0;i<x_max;i++) {
            if (squaresMatrix[i][y] != null && i!=x)
                squaresByDirection.add(squaresMatrix[i][y]);
        }
        return squaresByDirection;
    }

    public List<Player> getPlayersOnCardinalDirections(BoardSquare referenceSquare){
        List<BoardSquare> squaresByDirection = getSquaresOnCardinalDirections(referenceSquare);
        List<Player> playersByDirection = new ArrayList<>();
        for (BoardSquare square:squaresByDirection){
            playersByDirection.addAll(getPlayersOnSquare(square));
        }
        return playersByDirection;
    }

    public BoardSquare getBoardSquareByCoordinates(Coordinates coordinates) {
        return squaresMatrix[coordinates.getXCoordinate()][coordinates.getYCoordinate()];
    }

    public List<Player> getPlayersOnSquare(BoardSquare square){
        List<Player> onSquarePlayers = new ArrayList<>();
        for (Map.Entry<Player, BoardSquare> playerBoardSquareEntry: playerPositions.entrySet()){
            if (playerBoardSquareEntry.getValue() == square)
                onSquarePlayers.add(playerBoardSquareEntry.getKey());
        }
        return onSquarePlayers;
    }

    public BoardSquare getPlayerPosition(Player player){
        return playerPositions.get(player);
    }



}
