package it.polimi.se.eliafinazzigrazioli.adrenaline.core.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.model.AmmoCardClient;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.PlayerMovementEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.events.model.update.PlayerSpawnedEvent;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.exceptions.model.OutOfBoundBoardException;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.PowerUpsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.cards.effects.AmmoCardsDeck;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.*;

public class GameBoard {

    private BoardSquare[][] squaresMatrix;
    private int x_max = Rules.GAME_BOARD_X_MAX;
    private int y_max = Rules.GAME_BOARD_Y_MAX;
    private Map<Player, BoardSquare> playerPositions;

    public GameBoard(MapType mapType) {
        if (mapType.equals(MapType.ONE)) {
            squaresMatrix = new BoardSquare[x_max][y_max];
            squaresMatrix[0][2] = new GenericBoardSquare(Room.RED, new Coordinates(0, 2),
                    InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.DOOR, InterSquareLink.WALL);
            squaresMatrix[0][1] = new SpawnBoardSquare(Room.RED, new Coordinates(0, 1),
                    InterSquareLink.SAMEROOM, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.WALL);
            squaresMatrix[1][2] = new GenericBoardSquare(Room.BLUE, new Coordinates(1, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.DOOR);
            squaresMatrix[2][2] = new SpawnBoardSquare(Room.BLUE, new Coordinates(2, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[1][1] = new GenericBoardSquare(Room.PURPLE, new Coordinates(1, 1),
                    InterSquareLink.DOOR, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[2][1] = new GenericBoardSquare(Room.PURPLE, new Coordinates(2, 1),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
            squaresMatrix[3][1] = new GenericBoardSquare(Room.YELLOW, new Coordinates(3, 1),
                    InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.DOOR);
            squaresMatrix[3][0] = new SpawnBoardSquare(Room.YELLOW, new Coordinates(3, 0),
                    InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.DOOR);
            squaresMatrix[0][0] = new GenericBoardSquare(Room.GRAY, new Coordinates(0, 0),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[1][0] = new GenericBoardSquare(Room.GRAY, new Coordinates(1, 0),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM);
            squaresMatrix[2][0] = new GenericBoardSquare(Room.GRAY, new Coordinates(2, 0),
                    InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
            squaresMatrix[3][2] = null;

        }

        if (mapType.equals(MapType.TWO)) {
            squaresMatrix = new BoardSquare[x_max][y_max];
            squaresMatrix[0][0] = null;
            squaresMatrix[0][1] = new SpawnBoardSquare(Room.RED, new Coordinates(0, 1),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[1][1] = new GenericBoardSquare(Room.RED, new Coordinates(1, 1),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[1][0] = new GenericBoardSquare(Room.GRAY, new Coordinates(1, 0),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[2][0] = new GenericBoardSquare(Room.GRAY, new Coordinates(2, 0),
                    InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
            squaresMatrix[3][0] = new SpawnBoardSquare(Room.YELLOW, new Coordinates(3, 0),
                    InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.DOOR);
            squaresMatrix[3][1] = new GenericBoardSquare(Room.YELLOW, new Coordinates(3, 1),
                    InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.DOOR);
            squaresMatrix[2][1] = new GenericBoardSquare(Room.PURPLE, new Coordinates(2, 1),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL);
            squaresMatrix[0][2] = new GenericBoardSquare(Room.BLUE, new Coordinates(0, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[1][2] = new GenericBoardSquare(Room.BLUE, new Coordinates(1, 2),
                    InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM);
            squaresMatrix[2][2] = new GenericBoardSquare(Room.BLUE, new Coordinates(2, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[3][2] = null;
        }

        if (mapType.equals(MapType.THREE)) {
            squaresMatrix = new BoardSquare[x_max][y_max];
            squaresMatrix[0][0] = null;
            squaresMatrix[0][1] = new SpawnBoardSquare(Room.RED, new Coordinates(0, 1),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[1][1] = new GenericBoardSquare(Room.RED, new Coordinates(1, 1),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[1][0] = new GenericBoardSquare(Room.GRAY, new Coordinates(1, 0),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL);
            squaresMatrix[2][0] = new GenericBoardSquare(Room.YELLOW, new Coordinates(2, 0),
                    InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.DOOR);
            squaresMatrix[3][0] = new SpawnBoardSquare(Room.YELLOW, new Coordinates(3, 0),
                    InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[3][1] = new GenericBoardSquare(Room.YELLOW, new Coordinates(3, 1),
                    InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
            squaresMatrix[2][1] = new GenericBoardSquare(Room.YELLOW, new Coordinates(2, 1),
                    InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[0][2] = new GenericBoardSquare(Room.BLUE, new Coordinates(0, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[1][2] = new GenericBoardSquare(Room.BLUE, new Coordinates(1, 2),
                    InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM);
            squaresMatrix[2][2] = new GenericBoardSquare(Room.BLUE, new Coordinates(2, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
            squaresMatrix[3][2] = new SpawnBoardSquare(Room.GREEN, new Coordinates(3, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR);
        }

        if (mapType.equals(MapType.FOUR)) {
                squaresMatrix = new BoardSquare[x_max][y_max];
                squaresMatrix[0][0] = new GenericBoardSquare(Room.GRAY, new Coordinates(0, 0),
                        InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
                squaresMatrix[0][1] = new SpawnBoardSquare(Room.RED, new Coordinates(0, 1),
                        InterSquareLink.SAMEROOM, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.WALL);
                squaresMatrix[1][1] = new GenericBoardSquare(Room.PURPLE, new Coordinates(1, 1),
                        InterSquareLink.DOOR, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.WALL);
                squaresMatrix[1][0] = new GenericBoardSquare(Room.GRAY, new Coordinates(1, 0),
                        InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
                squaresMatrix[2][0] = new GenericBoardSquare(Room.YELLOW, new Coordinates(2, 0),
                        InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.DOOR);
                squaresMatrix[3][0] = new SpawnBoardSquare(Room.YELLOW, new Coordinates(3, 0),
                        InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
                squaresMatrix[3][1] = new GenericBoardSquare(Room.YELLOW, new Coordinates(3, 1),
                        InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.SAMEROOM);
                squaresMatrix[2][1] = new GenericBoardSquare(Room.YELLOW, new Coordinates(2, 1),
                        InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
                squaresMatrix[0][2] = new GenericBoardSquare(Room.RED, new Coordinates(0, 2),
                        InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.DOOR, InterSquareLink.WALL);
                squaresMatrix[1][2] = new GenericBoardSquare(Room.BLUE, new Coordinates(1, 2),
                        InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.DOOR);
                squaresMatrix[2][2] = new SpawnBoardSquare(Room.BLUE, new Coordinates(2, 2),
                        InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
                squaresMatrix[3][2] = new GenericBoardSquare(Room.GREEN, new Coordinates(3, 2),
                        InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR);

        }
        playerPositions = new HashMap<>();
    }

    public Map<Coordinates, AmmoCardClient> ammoCardsSetup(AmmoCardsDeck deck) {
        Map<Coordinates, AmmoCardClient> coordinatesAmmoCardMap = new HashMap<>();
        for (int x = 0; x < x_max; x++) {
            for (int y = 0; y < y_max; y++) {
                if (squaresMatrix[x][y] != null && !squaresMatrix[x][y].isSpawnPoint() && ((GenericBoardSquare) squaresMatrix[x][y]).getCollectable() == null) {
                    AmmoCard ammoCard = deck.drawCard();
                    ((GenericBoardSquare) squaresMatrix[x][y]).dropCollectables(ammoCard);
                    coordinatesAmmoCardMap.put(squaresMatrix[x][y].getCoordinates(), new AmmoCardClient(ammoCard));
                }
            }
        }
        return coordinatesAmmoCardMap;
    }

    public void setPlayerPositions(Player player, BoardSquare position) throws OutOfBoundBoardException {
        if (position == null)
            throw new OutOfBoundBoardException("OutOfBoundException");
        else
            playerPositions.put(player, position);
    }

    public void movePlayer(Player player, BoardSquare destination) {
        playerPositions.put(player, destination);
    }



    public PlayerSpawnedEvent spawnPlayer(Player player, PowerUpCard powerUpCard) {
        Ammo ammoColor = powerUpCard.getAmmo();
        List<BoardSquare> room;
        BoardSquare spawnBoardSquare = null;
        switch (ammoColor) {
            case YELLOW:
                room = getRoomSquares(Room.YELLOW);
                break;
            case BLUE:
                room = getRoomSquares(Room.BLUE);
                break;
            case RED:
                room = getRoomSquares(Room.RED);
                break;
            default:
                room = null;
        }
        for (BoardSquare boardSquare: room)
            if (boardSquare.isSpawnPoint())
                spawnBoardSquare = boardSquare;

        movePlayer(player, spawnBoardSquare);
        return new PlayerSpawnedEvent(player, spawnBoardSquare.getCoordinates(), powerUpCard);
    }

    /**
     * Method called by match to perform player movement
     * @param player
     * @param coordinatesPath
     * @return
     */

    public PlayerMovementEvent playerMovement(Player player, List<Coordinates> coordinatesPath) {
        List<BoardSquare> boardSquaresPath = coordinatesToBoardSquares(coordinatesPath);

        movePlayer(player, boardSquaresPath.get(boardSquaresPath.size()-1));
        return new PlayerMovementEvent(player, coordinatesPath);
    }

    /**
     * Returns true if a valid path is given. A valid path is to be considered a list of squares where
     * the first element is adjacent(squares with a wall in the middle are not considered adjacent) to the current player
     * position and every other element is adjacent to the previous one.
     * Another validation requirement is that the length of the given path is no bigger then the maximum number of
     * movements allowed in case no other action is performed.
     * @param player : The player who should perform the movement along given path.
     * @param path : List of boardSquares. Not null value is required
     * @return
     */
    public boolean pathIsValid(Player player, List<Coordinates> path){
        List<BoardSquare> boardSquaresPath = coordinatesToBoardSquares(path);
        BoardSquare playerPosition = getPlayerPosition(player);
        for (BoardSquare boardSquare:boardSquaresPath){
            if (!getOneStepReachableSquares(playerPosition).contains(boardSquare))
                return false;
            playerPosition = boardSquare;
        }
        return true;
    }

    public AmmoCard collectAmmoCard(Player player, PowerUpsDeck deck) {
        BoardSquare position = getPlayerPosition(player);
        if (position.isSpawnPoint())
            return null;
        else
            return ((GenericBoardSquare) position).gatherCollectables();

    }

    private List<BoardSquare> coordinatesToBoardSquares(List<Coordinates> coordinatesList) {
        List<BoardSquare> boardSquares = new ArrayList<>();
        coordinatesList.forEach(coordinates -> {
            boardSquares.add(getBoardSquareByCoordinates(coordinates));
        });
        return boardSquares;
    }

    public List<BoardSquare> getRoomSquares(Room room){
        List<BoardSquare> roomSquares = new ArrayList<BoardSquare>();
        for (int i = 0; i < x_max; i++) {
            for (int j = 0; j < y_max; j++) {
                BoardSquare square = squaresMatrix[i][j];
                if (square != null && square.getRoom() == room) {
                    roomSquares.add(square);
                }
            }
        }
        return roomSquares;
    }

    public List<Player> getRoomPlayers(Room room) {
        return convertSquaresIntoPlayers(getRoomSquares(room));
    }

    public List<BoardSquare> getVisibleSquares(BoardSquare referenceSquare, boolean notVisible) {
        List<BoardSquare> visibleSquares = new ArrayList<>();
        visibleSquares.addAll(getRoomSquares(referenceSquare.getRoom()));
        Coordinates coord = referenceSquare.getCoordinates();
        int x;
        int y;
        if (referenceSquare.getNorth() == InterSquareLink.DOOR) {
            x = coord.getXCoordinate();
            y = coord.getYCoordinate() + 1;
            visibleSquares.addAll(getRoomSquares(squaresMatrix[x][y].getRoom()));
        }
        if (referenceSquare.getSouth() == InterSquareLink.DOOR) {
            x = coord.getXCoordinate();
            y = coord.getYCoordinate() - 1;
            visibleSquares.addAll(getRoomSquares(squaresMatrix[x][y].getRoom()));
        }
        if (referenceSquare.getEast() == InterSquareLink.DOOR) {
            x = coord.getXCoordinate() + 1;
            y = coord.getYCoordinate();
            visibleSquares.addAll(getRoomSquares(squaresMatrix[x][y].getRoom()));
        }
        if (referenceSquare.getWest() == InterSquareLink.DOOR) {
            x = coord.getXCoordinate() - 1;
            y = coord.getYCoordinate();
            visibleSquares.addAll(getRoomSquares(squaresMatrix[x][y].getRoom()));
        }

        if (notVisible) {
            List<BoardSquare> notVisibleSquares = new ArrayList<>();
            for (int i = 0; i < x_max; i++) {
                for (int j = 0; j < y_max; j++) {
                    if (!visibleSquares.contains(squaresMatrix[i][j])) {
                        if (squaresMatrix[i][j] != null)
                            notVisibleSquares.add(squaresMatrix[i][j]);
                    }
                }
            }
            return notVisibleSquares;
        }
        return visibleSquares;
    }

    public List<Room> getVisibleRooms(BoardSquare referenceSquare, boolean notVisible) {
        List<Room> visibleRooms = new ArrayList<>();
        int x = referenceSquare.getCoordinates().getXCoordinate();
        int y = referenceSquare.getCoordinates().getYCoordinate();
        if (referenceSquare.getNorth() == InterSquareLink.DOOR)
            visibleRooms.add(squaresMatrix[x][y + 1].getRoom());
        if (referenceSquare.getSouth() == InterSquareLink.DOOR)
            visibleRooms.add(squaresMatrix[x][y - 1].getRoom());
        if (referenceSquare.getEast() == InterSquareLink.DOOR)
            visibleRooms.add(squaresMatrix[x + 1][y].getRoom());
        if (referenceSquare.getWest() == InterSquareLink.DOOR)
            visibleRooms.add(squaresMatrix[x - 1][y].getRoom());
        return visibleRooms;
    }

    public List<Player> getVisiblePlayers(BoardSquare referenceSquare, boolean notVisible) {
        return convertSquaresIntoPlayers(getVisibleSquares(referenceSquare, notVisible));
    }

    //returns all the squares between maxDistance and minDistance, if minDistance=0 referenceSquare is included, if minDistance=1 reference square is excluded
    public List<BoardSquare> getSquaresByDistance(BoardSquare referenceSquare, int maxDistance, int minDistance) throws Exception { //TODO define exception type
        if (maxDistance < minDistance) {
            throw new Exception();
        }
        int stepsTaken = 0;
        Set<BoardSquare> newBoundSet = new HashSet<>();
        Set<BoardSquare> belowMinDistanceSquares = new HashSet<>();
        Set<BoardSquare> belowMaxDistanceSquares = new HashSet<>();
        Set<BoardSquare> boundSquares = new HashSet<>();
        boundSquares.add(referenceSquare);
        while (stepsTaken <= maxDistance) {
            for (BoardSquare bound : boundSquares) {
                newBoundSet.addAll(getOneStepReachableSquares(bound));
                newBoundSet.removeAll(belowMaxDistanceSquares);
                newBoundSet.removeAll(boundSquares);
            }
            belowMaxDistanceSquares.addAll(boundSquares);
            if (stepsTaken < minDistance) {
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
        return convertSquaresIntoPlayers(getSquaresByDistance(referenceSquare, maxDistance, minDistance));
    }

    public List<BoardSquare> getSquaresByDistance(Player referencePlayer, int maxDistance, int minDistance) throws Exception { //TODO define exception type
        return getSquaresByDistance(getPlayerPosition(referencePlayer), maxDistance, minDistance);
    }

    public List<Player> getPlayersByDistance(Player referencePlayer, int maxDistance, int minDistance) throws Exception { //TODO define exception type
        return getPlayersByDistance(getPlayerPosition(referencePlayer), maxDistance, minDistance);
    }

    public List<BoardSquare> getBoardSquaresByCardinalDirection(BoardSquare referenceSquare, CardinalDirection direction) {
        List<BoardSquare> squaresByDirection = new ArrayList<>();
        int x = referenceSquare.getCoordinates().getXCoordinate();
        int y = referenceSquare.getCoordinates().getYCoordinate();
        switch (direction) {
            case NORTH:
                for (int i = y + 1; i < y_max; i++) {
                    if (squaresMatrix[x][i] != null)
                        squaresByDirection.add(squaresMatrix[x][i]);
                }
                break;
            case SOUTH:
                for (int i = y - 1; i >= 0; i--) {
                    if (squaresMatrix[x][i] != null)
                        squaresByDirection.add(squaresMatrix[x][i]);
                }
                break;
            case EAST:
                for (int i = x + 1; i < x_max; i++) {
                    if (squaresMatrix[i][y] != null)
                        squaresByDirection.add(squaresMatrix[i][y]);
                }
                break;
            case WEST:
                for (int i = x-1; i >= 0; i--){
                    if (squaresMatrix[i][y] != null)
                        squaresByDirection.add(squaresMatrix[i][y]);
                }
                break;
        }
        return squaresByDirection;
    }

    public List<Player> getPlayersByCardinalDirection(BoardSquare referenceSquare, CardinalDirection direction) {
        return convertSquaresIntoPlayers(getBoardSquaresByCardinalDirection(referenceSquare, direction));
    }

    public BoardSquare getBoardSquareByCoordinates(Coordinates coordinates) {
        return squaresMatrix[coordinates.getXCoordinate()][coordinates.getYCoordinate()];
    }

    public Coordinates getCoordinates(BoardSquare boardSquare) {
        for (int i = 0; i < x_max; i++) {
            for (int j = 0; j < y_max; j++)
                if (boardSquare == squaresMatrix[i][j])
                    return new Coordinates(i, j);
        }
        return null;
    }

    public List<Player> getPlayersOnSquare(BoardSquare square) {
        List<Player> onSquarePlayers = new ArrayList<>();
        for (Map.Entry<Player, BoardSquare> playerBoardSquareEntry : playerPositions.entrySet()) {
            if (playerBoardSquareEntry.getValue() == square)
                onSquarePlayers.add(playerBoardSquareEntry.getKey());
        }
        return onSquarePlayers;
    }

    public List<Player> getPlayersOnGameBoard() {
        return new ArrayList<>(playerPositions.keySet());
    }

    public BoardSquare getPlayerPosition(Player player) {
        return playerPositions.get(player);
    }

    private Set<BoardSquare> getOneStepReachableSquares(BoardSquare referenceSquare) { //TODO define exception type
        Set<BoardSquare> reachableSquares = new HashSet<>();
        Coordinates coord = referenceSquare.getCoordinates();
        int x;
        int y;
        if (referenceSquare.getNorth() != InterSquareLink.WALL) {
            x = coord.getXCoordinate();
            y = coord.getYCoordinate() + 1;
            reachableSquares.add(squaresMatrix[x][y]);
        }
        if (referenceSquare.getSouth() != InterSquareLink.WALL) {
            x = coord.getXCoordinate();
            y = coord.getYCoordinate() - 1;
            reachableSquares.add(squaresMatrix[x][y]);
        }
        if (referenceSquare.getEast() != InterSquareLink.WALL) {
            x = coord.getXCoordinate() + 1;
            y = coord.getYCoordinate();
            reachableSquares.add(squaresMatrix[x][y]);
        }
        if (referenceSquare.getWest() != InterSquareLink.WALL) {
            x = coord.getXCoordinate() - 1;
            y = coord.getYCoordinate();
            reachableSquares.add(squaresMatrix[x][y]);
        }

        return reachableSquares;

    }

    private List<Player> convertSquaresIntoPlayers(List<BoardSquare> boardSquares) {
        List<Player> players = new ArrayList<>();
        for (BoardSquare square : boardSquares) {
            players.addAll(getPlayersOnSquare(square));
        }
        return players;
    }


}
