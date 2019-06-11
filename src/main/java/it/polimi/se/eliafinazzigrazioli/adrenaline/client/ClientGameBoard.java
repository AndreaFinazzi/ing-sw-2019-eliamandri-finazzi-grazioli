package it.polimi.se.eliafinazzigrazioli.adrenaline.client;

import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientGameBoard {
    private BoardSquareClient[][] squaresMatrix;
    private int x_max = Rules.GAME_BOARD_X_MAX;
    private int y_max = Rules.GAME_BOARD_Y_MAX;
    private Map<String, BoardSquareClient> playerPositions = new HashMap<>();

    public ClientGameBoard(MapType mapType) {
        if (mapType.equals(MapType.ONE)) {
            squaresMatrix = new BoardSquareClient[x_max][y_max];
            squaresMatrix[0][2] = new GenericBoardSquareClient(Room.RED, new Coordinates(0, 2),
                    InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.DOOR, InterSquareLink.WALL);
            squaresMatrix[0][1] = new SpawnBoardSquareClient(Room.RED, new Coordinates(0, 1),
                    InterSquareLink.SAMEROOM, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.WALL);
            squaresMatrix[1][2] = new GenericBoardSquareClient(Room.BLUE, new Coordinates(1, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.DOOR);
            squaresMatrix[2][2] = new SpawnBoardSquareClient(Room.BLUE, new Coordinates(2, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[1][1] = new GenericBoardSquareClient(Room.PURPLE, new Coordinates(1, 1),
                    InterSquareLink.DOOR, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[2][1] = new GenericBoardSquareClient(Room.PURPLE, new Coordinates(2, 1),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
            squaresMatrix[3][1] = new GenericBoardSquareClient(Room.YELLOW, new Coordinates(3, 1),
                    InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.DOOR);
            squaresMatrix[3][0] = new SpawnBoardSquareClient(Room.YELLOW, new Coordinates(3, 0),
                    InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.DOOR);
            squaresMatrix[0][0] = new GenericBoardSquareClient(Room.GRAY, new Coordinates(0, 0),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[1][0] = new GenericBoardSquareClient(Room.GRAY, new Coordinates(1, 0),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM);
            squaresMatrix[2][0] = new GenericBoardSquareClient(Room.GRAY, new Coordinates(2, 0),
                    InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
            squaresMatrix[3][2] = null;

        }

        if (mapType.equals(MapType.TWO)) {
            squaresMatrix = new BoardSquareClient[x_max][y_max];
            squaresMatrix[0][0] = null;
            squaresMatrix[0][1] = new SpawnBoardSquareClient(Room.RED, new Coordinates(0, 1),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[1][1] = new GenericBoardSquareClient(Room.RED, new Coordinates(1, 1),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[1][0] = new GenericBoardSquareClient(Room.GRAY, new Coordinates(1, 0),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[2][0] = new GenericBoardSquareClient(Room.GRAY, new Coordinates(2, 0),
                    InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
            squaresMatrix[3][0] = new SpawnBoardSquareClient(Room.YELLOW, new Coordinates(3, 0),
                    InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.DOOR);
            squaresMatrix[3][1] = new GenericBoardSquareClient(Room.YELLOW, new Coordinates(3, 1),
                    InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.DOOR);
            squaresMatrix[2][1] = new GenericBoardSquareClient(Room.PURPLE, new Coordinates(2, 1),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL);
            squaresMatrix[0][2] = new GenericBoardSquareClient(Room.BLUE, new Coordinates(0, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[1][2] = new GenericBoardSquareClient(Room.BLUE, new Coordinates(1, 2),
                    InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM);
            squaresMatrix[2][2] = new GenericBoardSquareClient(Room.BLUE, new Coordinates(2, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[3][2] = null;
        }

        if (mapType.equals(MapType.THREE)) {
            squaresMatrix = new BoardSquareClient[x_max][y_max];
            squaresMatrix[0][0] = null;
            squaresMatrix[0][1] = new SpawnBoardSquareClient(Room.RED, new Coordinates(0, 1),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[1][1] = new GenericBoardSquareClient(Room.RED, new Coordinates(1, 1),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[1][0] = new GenericBoardSquareClient(Room.GRAY, new Coordinates(1, 0),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL);
            squaresMatrix[2][0] = new GenericBoardSquareClient(Room.YELLOW, new Coordinates(2, 0),
                    InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.DOOR);
            squaresMatrix[3][0] = new SpawnBoardSquareClient(Room.YELLOW, new Coordinates(3, 0),
                    InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[3][1] = new GenericBoardSquareClient(Room.YELLOW, new Coordinates(3, 1),
                    InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[2][1] = new GenericBoardSquareClient(Room.YELLOW, new Coordinates(2, 1),
                    InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[0][2] = new GenericBoardSquareClient(Room.BLUE, new Coordinates(0, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[1][2] = new GenericBoardSquareClient(Room.BLUE, new Coordinates(1, 2),
                    InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM);
            squaresMatrix[2][2] = new GenericBoardSquareClient(Room.BLUE, new Coordinates(2, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
            squaresMatrix[3][2] = new SpawnBoardSquareClient(Room.GREEN, new Coordinates(3, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR);
        }

        if (mapType.equals(MapType.FOUR)) {
            if (mapType.equals(MapType.THREE)) {
                squaresMatrix = new BoardSquareClient[x_max][y_max];
                squaresMatrix[0][0] = new GenericBoardSquareClient(Room.GRAY, new Coordinates(0, 0),
                        InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
                squaresMatrix[0][1] = new SpawnBoardSquareClient(Room.RED, new Coordinates(0, 1),
                        InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
                squaresMatrix[1][1] = new GenericBoardSquareClient(Room.PURPLE, new Coordinates(1, 1),
                        InterSquareLink.DOOR, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.WALL);
                squaresMatrix[1][0] = new GenericBoardSquareClient(Room.GRAY, new Coordinates(1, 0),
                        InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
                squaresMatrix[2][0] = new GenericBoardSquareClient(Room.YELLOW, new Coordinates(2, 0),
                        InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.DOOR);
                squaresMatrix[3][0] = new SpawnBoardSquareClient(Room.YELLOW, new Coordinates(3, 0),
                        InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
                squaresMatrix[3][1] = new GenericBoardSquareClient(Room.YELLOW, new Coordinates(3, 1),
                        InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
                squaresMatrix[2][1] = new GenericBoardSquareClient(Room.YELLOW, new Coordinates(2, 1),
                        InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
                squaresMatrix[0][2] = new GenericBoardSquareClient(Room.RED, new Coordinates(0, 2),
                        InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.DOOR, InterSquareLink.WALL);
                squaresMatrix[1][2] = new GenericBoardSquareClient(Room.BLUE, new Coordinates(1, 2),
                        InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
                squaresMatrix[2][2] = new GenericBoardSquareClient(Room.BLUE, new Coordinates(2, 2),
                        InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
                squaresMatrix[3][2] = new SpawnBoardSquareClient(Room.GREEN, new Coordinates(3, 2),
                        InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR);
            }

        }
    }

    public void setPlayerPosition (String player, Coordinates coordinates) {
        playerPositions.put(player, squaresMatrix[coordinates.getXCoordinate()][coordinates.getYCoordinate()]);
    }

    public List<BoardSquareClient> getRoomSquares(Room room) {
        List<BoardSquareClient> roomSquares = new ArrayList<>();
        for (int i = 0; i < x_max; i++) {
            for (int j = 0; j < y_max; j++) {
                BoardSquareClient square = squaresMatrix[i][j];
                if (square != null && square.getRoom() == room) {
                    roomSquares.add(square);
                }
            }
        }
        return roomSquares;
    }

    public BoardSquareClient getPlayerPositionByName(String playerName) {
        if(!playerPositions.containsKey(playerName))
            return null;
        return playerPositions.get(playerName);
    }

    public Map<String, BoardSquareClient> getPlayersPosition() {
        return playerPositions;
    }

    public Coordinates getCoordinates(BoardSquareClient boardSquare) {
        for (int i = 0; i < x_max; i++) {
            for (int j = 0; j < y_max; j++)
                if (boardSquare == squaresMatrix[i][j])
                    return new Coordinates(i, j);
        }
        return null;
    }

    public BoardSquareClient getBoardSquareByCoordinates(Coordinates coordinates) {
        return squaresMatrix[coordinates.getXCoordinate()][coordinates.getYCoordinate()];
    }

    public List<BoardSquareClient> getSpawnBoardSquareClient() {
        List<BoardSquareClient> listSpawn = new ArrayList<>();
        for(int i=0; i<x_max; i++) {
            for(int j=0; j<y_max; j++) {
                if(squaresMatrix[i][j] != null && squaresMatrix[i][j].isSpawnBoard())
                    listSpawn.add(squaresMatrix[i][j]);
            }
        }
        return listSpawn;
    }

    public int getX_max() {
        return x_max;
    }

    public int getY_max() {
        return y_max;
    }
}
