package it.polimi.se.eliafinazzigrazioli.adrenaline.client.model;

import it.polimi.se.eliafinazzigrazioli.adrenaline.client.CLI.Color;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.model.*;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Coordinates;
import it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils.Rules;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Game board client.
 */
public class GameBoardClient {
    /**
     * The Logger.
     */
    static final Logger LOGGER = Logger.getLogger(GameBoardClient.class.getName());

    private MapType mapType;

    private BoardSquareClient[][] squaresMatrix;

    private int x_max = Rules.GAME_BOARD_X_MAX;
    private int y_max = Rules.GAME_BOARD_Y_MAX;
    private Map<String, BoardSquareClient> playerPositions = new HashMap<>();
    private final int DIM_X = 12;
    private final int DIM_Y = 6;

    /**
     * The constant ANSI_RESET.
     */
    public static final String ANSI_RESET = "\u001B[0m";
    /**
     * The constant ANSI_RED.
     */
    public static final String ANSI_RED = "\u001B[31m";
    /**
     * The constant ANSI_GREEN.
     */
    public static final String ANSI_GREEN = "\u001B[32m";
    /**
     * The constant ANSI_YELLOW.
     */
    public static final String ANSI_YELLOW = "\u001B[33m";
    /**
     * The constant ANSI_BLUE.
     */
    public static final String ANSI_BLUE = "\u001B[34m";
    /**
     * The constant ANSI_PURPLE.
     */
    public static final String ANSI_PURPLE = "\u001B[35m";
    /**
     * The constant ANSI_WHITE.
     */
    public static final String ANSI_WHITE = "\u001B[37m";

    /**
     * Instantiates a new Game board client.
     *
     * @param mapType the map type
     */
    public GameBoardClient(MapType mapType) {
        this.mapType = mapType;

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
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM);
            squaresMatrix[1][0] = new GenericBoardSquareClient(Room.GRAY, new Coordinates(1, 0),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[2][0] = new GenericBoardSquareClient(Room.GRAY, new Coordinates(2, 0),
                    InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
            squaresMatrix[3][0] = new SpawnBoardSquareClient(Room.YELLOW, new Coordinates(3, 0),
                    InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.DOOR);
            squaresMatrix[3][1] = new GenericBoardSquareClient(Room.YELLOW, new Coordinates(3, 1),
                    InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL, InterSquareLink.DOOR);
            squaresMatrix[2][1] = new GenericBoardSquareClient(Room.RED, new Coordinates(2, 1),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
            squaresMatrix[0][2] = new GenericBoardSquareClient(Room.BLUE, new Coordinates(0, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[1][2] = new GenericBoardSquareClient(Room.BLUE, new Coordinates(1, 2),
                    InterSquareLink.WALL, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.SAMEROOM);
            squaresMatrix[2][2] = new SpawnBoardSquareClient(Room.BLUE, new Coordinates(2, 2),
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
            squaresMatrix[2][2] = new SpawnBoardSquareClient(Room.BLUE, new Coordinates(2, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
            squaresMatrix[3][2] = new GenericBoardSquareClient(Room.GREEN, new Coordinates(3, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR);
        }

        if (mapType.equals(MapType.FOUR)) {
            squaresMatrix = new BoardSquareClient[x_max][y_max];
            squaresMatrix[0][0] = new GenericBoardSquareClient(Room.GRAY, new Coordinates(0, 0),
                    InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.SAMEROOM, InterSquareLink.WALL);
            squaresMatrix[0][1] = new SpawnBoardSquareClient(Room.RED, new Coordinates(0, 1),
                    InterSquareLink.SAMEROOM, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.WALL);
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
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.SAMEROOM, InterSquareLink.DOOR);
            squaresMatrix[2][2] = new SpawnBoardSquareClient(Room.BLUE, new Coordinates(2, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.DOOR, InterSquareLink.SAMEROOM);
            squaresMatrix[3][2] = new GenericBoardSquareClient(Room.GREEN, new Coordinates(3, 2),
                    InterSquareLink.WALL, InterSquareLink.DOOR, InterSquareLink.WALL, InterSquareLink.DOOR);
        }
    }

    /**
     * Gets map type.
     *
     * @return the map type
     */
    public MapType getMapType() {
        return mapType;
    }

    /**
     * Reset ammo cards.
     *
     * @param ammoCardsReset the ammo cards reset
     */
    public void resetAmmoCards(Map<Coordinates, AmmoCardClient> ammoCardsReset) {
        for (Map.Entry<Coordinates, AmmoCardClient> squareToAmmoCardEntry : ammoCardsReset.entrySet()) {
            getBoardSquareByCoordinates(squareToAmmoCardEntry.getKey()).addAmmoCard(squareToAmmoCardEntry.getValue());
        }
    }

    /**
     * Reset weapons.
     *
     * @param weaponsPlaced the weapons placed
     */
    public void resetWeapons(Map<Coordinates, List<WeaponCardClient>> weaponsPlaced) {
        for (Map.Entry<Coordinates, List<WeaponCardClient>> coordinatesToWeapons : weaponsPlaced.entrySet()) {
            BoardSquareClient boardSquare = getBoardSquareByCoordinates(coordinatesToWeapons.getKey());
            for (WeaponCardClient weaponCard : coordinatesToWeapons.getValue())
                ((SpawnBoardSquareClient) boardSquare).addWeapon(weaponCard);
        }
    }

    /**
     * Sets player position.
     *
     * @param player the player
     * @param coordinates the coordinates
     */
    public void setPlayerPosition(String player, Coordinates coordinates) {
        if (coordinates != null)
            playerPositions.put(player, squaresMatrix[coordinates.getXCoordinate()][coordinates.getYCoordinate()]);
    }

    /**
     * Gets room squares.
     *
     * @param room the room
     * @return the room squares
     */
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

    /**
     * Gets player position by name.
     *
     * @param playerName the player name
     * @return the player position by name
     */
    public BoardSquareClient getPlayerPositionByName(String playerName) {
        if (!playerPositions.containsKey(playerName))
            return null;
        return playerPositions.get(playerName);
    }

    /**
     * Remove player.
     *
     * @param player the player
     */
    public void removePlayer(String player) {
        playerPositions.remove(player);
    }

    /**
     * Gets players position.
     *
     * @return the players position
     */
    public Map<String, BoardSquareClient> getPlayersPosition() {
        return playerPositions;
    }

    /**
     * Gets coordinates.
     *
     * @param boardSquare the board square
     * @return the coordinates
     */
    public Coordinates getCoordinates(BoardSquareClient boardSquare) {
        for (int i = 0; i < x_max; i++) {
            for (int j = 0; j < y_max; j++)
                if (boardSquare == squaresMatrix[i][j])
                    return new Coordinates(i, j);
        }
        return null;
    }

    /**
     * Gets board square by coordinates.
     *
     * @param coordinates the coordinates
     * @return the board square by coordinates
     */
    public BoardSquareClient getBoardSquareByCoordinates(Coordinates coordinates) {
        return squaresMatrix[coordinates.getXCoordinate()][coordinates.getYCoordinate()];
    }

    /**
     * Gets spawn board square client.
     *
     * @return the spawn board square client
     */
    public List<SpawnBoardSquareClient> getSpawnBoardSquareClient() {
        List<SpawnBoardSquareClient> listSpawn = new ArrayList<>();
        for (int i = 0; i < x_max; i++) {
            for (int j = 0; j < y_max; j++) {
                if (squaresMatrix[i][j] != null && squaresMatrix[i][j].isSpawnBoard())
                    try {
                        listSpawn.add((SpawnBoardSquareClient) squaresMatrix[i][j]);
                    } catch (ClassCastException e) {
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                    }
            }
        }
        return listSpawn;
    }

    /**
     * Gets spawn board square client by room.
     *
     * @param room the room
     * @return the spawn board square client by room
     */
    public SpawnBoardSquareClient getSpawnBoardSquareClientByRoom(Room room) {
        for (SpawnBoardSquareClient spawnBoardSquare : getSpawnBoardSquareClient()) {
            if (spawnBoardSquare.room == room) return spawnBoardSquare;
        }

        return null;
    }


    /**
     * Get square text string [ ] [ ].
     *
     * @param boardSquare the board square
     * @param avatars the avatars
     * @return the string [ ] [ ]
     */
    public String[][] getSquareText(BoardSquareClient boardSquare, List<Avatar> avatars) {
        int width = DIM_X;
        int height = DIM_Y;
        Random random = new Random();
        String[][] squareText = new String[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                squareText[i][j] = " ";
            }
        }
        String color;

        if (boardSquare == null) {
            return squareText;
        } else {
            if (boardSquare.getRoom().equals(Room.BLUE))
                color = ANSI_BLUE;
            else if (boardSquare.getRoom().equals(Room.RED))
                color = ANSI_RED;
            else if (boardSquare.getRoom().equals(Room.YELLOW))
                color = ANSI_YELLOW;
            else if (boardSquare.getRoom().equals((Room.PURPLE)))
                color = ANSI_PURPLE;
            else if (boardSquare.getRoom().equals(Room.GRAY))
                color = ANSI_WHITE;
            else if (boardSquare.getRoom().equals(Room.GREEN))
                color = ANSI_GREEN;
            else
                color = ANSI_RESET;

            if (boardSquare.isSpawnBoard()) {
                squareText[1][DIM_Y - 2] = color + "S";
            }

            if (boardSquare.getNorth().equals(InterSquareLink.WALL)) {
                if (boardSquare.getWest().equals(InterSquareLink.WALL) || boardSquare.getWest().equals(InterSquareLink.DOOR))
                    squareText[0][height - 1] = color + "╔";
                else if (boardSquare.getWest().equals(InterSquareLink.SAMEROOM))
                    squareText[0][height - 1] = color + "═";
                for (int i = 1; i < width - 1; i++) {
                    squareText[i][height - 1] = color + "═";
                }
                if (boardSquare.getEast().equals(InterSquareLink.WALL) || boardSquare.getEast().equals(InterSquareLink.DOOR))
                    squareText[width - 1][height - 1] = color + "╗";
                else if (boardSquare.getEast().equals(InterSquareLink.SAMEROOM))
                    squareText[width - 1][height - 1] = color + "═";

            } else if (boardSquare.getNorth().equals(InterSquareLink.DOOR)) {

                if (boardSquare.getWest().equals(InterSquareLink.WALL) || boardSquare.getWest().equals(InterSquareLink.DOOR))
                    squareText[0][height - 1] = color + "╔";
                else if (boardSquare.getWest().equals(InterSquareLink.SAMEROOM))
                    squareText[0][height - 1] = color + "═";
                for (int i = 1; i < width - 1; i++) {
                    squareText[i][height - 1] = color + "═";
                }
                squareText[3][height - 1] = color + "╝" + ANSI_RESET;
                squareText[4][height - 1] = " ";
                squareText[5][height - 1] = " ";
                squareText[6][height - 1] = " ";
                squareText[7][height - 1] = " ";
                squareText[8][height - 1] = color + "╚";
                if (boardSquare.getEast().equals(InterSquareLink.WALL) || boardSquare.getEast().equals(InterSquareLink.DOOR))
                    squareText[width - 1][height - 1] = color + "╗";
                else if (boardSquare.getEast().equals(InterSquareLink.SAMEROOM))
                    squareText[width - 1][height - 1] = color + "═";
            } else if (boardSquare.getNorth().equals(InterSquareLink.SAMEROOM)) {
                squareText[0][height - 1] = color + "║";
                squareText[width - 1][height - 1] = color + "║";
            }

            if (boardSquare.getSouth().equals(InterSquareLink.WALL)) {
                if (boardSquare.getWest().equals(InterSquareLink.WALL) || boardSquare.getWest().equals(InterSquareLink.DOOR))
                    squareText[0][0] = color + "╚";
                else if (boardSquare.getWest().equals(InterSquareLink.SAMEROOM))
                    squareText[0][0] = color + "═";
                for (int i = 1; i < width - 1; i++) {
                    squareText[i][0] = color + "═";
                }
                if (boardSquare.getEast().equals(InterSquareLink.WALL) || boardSquare.getEast().equals(InterSquareLink.DOOR))
                    squareText[width - 1][0] = color + "╝";
                else if (boardSquare.getEast().equals(InterSquareLink.SAMEROOM))
                    squareText[width - 1][0] = color + "═";
            } else if (boardSquare.getSouth().equals(InterSquareLink.DOOR)) {
                if (boardSquare.getWest().equals(InterSquareLink.WALL) || boardSquare.getWest().equals(InterSquareLink.DOOR))
                    squareText[0][0] = color + "╚";
                else if (boardSquare.getWest().equals(InterSquareLink.SAMEROOM))
                    squareText[0][0] = color + "═";
                for (int i = 1; i < width - 1; i++) {
                    squareText[i][0] = color + "═";
                }
                squareText[3][0] = color + "╗";
                squareText[4][0] = " ";
                squareText[5][0] = " ";
                squareText[6][0] = " ";
                squareText[7][0] = " ";
                squareText[8][0] = color + "╔";
                if (boardSquare.getEast().equals(InterSquareLink.WALL) || boardSquare.getEast().equals(InterSquareLink.DOOR))
                    squareText[width - 1][0] = color + "╝";
                else if (boardSquare.getEast().equals(InterSquareLink.SAMEROOM))
                    squareText[width - 1][0] = color + "═";
            } else if (boardSquare.getSouth().equals(InterSquareLink.SAMEROOM)) {
                squareText[0][0] = color + "║";
                squareText[width - 1][0] = color + "║";
            }

            if (boardSquare.getEast().equals(InterSquareLink.WALL)) {
                for (int i = 1; i < height - 1; i++) {
                    squareText[width - 1][i] = color + "║";
                }
            } else if (boardSquare.getEast().equals(InterSquareLink.DOOR)) {
                for (int i = 1; i < height - 1; i++) {
                    squareText[width - 1][i] = color + "║";
                }
                squareText[width - 1][1] = color + "╔";
                squareText[width - 1][2] = " ";
                squareText[width - 1][3] = " ";
                squareText[width - 1][4] = color + "╚";
            }

            if (boardSquare.getWest().equals(InterSquareLink.WALL)) {
                for (int i = 1; i < height - 1; i++) {
                    squareText[0][i] = color + "║";
                }
            } else if (boardSquare.getWest().equals(InterSquareLink.DOOR)) {
                for (int i = 1; i < height - 1; i++) {
                    squareText[0][i] = color + "║";
                }
                squareText[0][1] = color + "╗";
                squareText[0][2] = " ";
                squareText[0][3] = " ";
                squareText[0][4] = color + "╝";
            }

        }
        if (boardSquare.hasAmmoCard()) {
            int x = (DIM_X / 2) - 1;
            int y = (DIM_Y / 2);
            for (Ammo ammo : boardSquare.getAmmoCard().getAmmos()) {
                squareText[x][y] = ammo.toStringToMap();
                x++;
            }

            if (boardSquare.getAmmoCard().containsPowerUp()) {
                squareText[x][y] = Color.RESET + "P";
            }
        }
        addPlayers(squareText, avatars);
        System.out.print(ANSI_RESET);
        return squareText;
    }

    /**
     * Add players.
     *
     * @param squareText the square text
     * @param avatars the avatars
     */
    public void addPlayers(String[][] squareText, List<Avatar> avatars) {
        Random random = new Random();
        int x = 0;
        int y = 0;
        String color;

        for (Avatar avatar : avatars) {
            // perchè è null???
            if (avatar != null) {
                if (avatar.getDamageMark().equals(DamageMark.GREEN)) {
                    color = ANSI_GREEN;
                } else if (avatar.getDamageMark().equals(DamageMark.YELLOW)) {
                    color = ANSI_YELLOW;
                } else if (avatar.getDamageMark().equals(DamageMark.BLUE)) {
                    color = ANSI_BLUE;
                } else if (avatar.getDamageMark().equals(DamageMark.GREY)) {
                    color = ANSI_WHITE;
                } else { //Purple
                    color = ANSI_PURPLE;
                }
                do {
                    x = random.nextInt(DIM_X - 2) + 1;
                    y = random.nextInt(DIM_Y - 2) + 1;
                } while (!squareText[x][y].equals(" "));
                squareText[x][y] = color + "\u263B";
            }

        }
    }

    /**
     * Get map matrix string [ ] [ ].
     *
     * @param avatarMap the avatar map
     * @return the string [ ] [ ]
     */
    public String[][] getMapMatrix(Map<String, Avatar> avatarMap) {
        List<String[][]> squares = new ArrayList<>();

        String[][] square;
        for (int j = y_max - 1; j >= 0; j--) {
            for (int i = 0; i < x_max; i++) {
                BoardSquareClient boardSquareClient = squaresMatrix[i][j];
                List<String> keys = new ArrayList<>(playerPositions.keySet());
                List<Avatar> avatars = new ArrayList<>();
                for (String key : keys) {
                    if (playerPositions.get(key).equals(squaresMatrix[i][j])) {
                        avatars.add(avatarMap.get(key));
                    }
                }
                String[][] temp = getSquareText(squaresMatrix[i][j], avatars);
                squares.add(temp);
            }
        }

        String[][] mapText = new String[DIM_X * x_max][DIM_Y * y_max];

        int currentX = 0;
        int currentY = DIM_Y * y_max - 1;
        int count = -1;
        for (String[][] iterator : squares) {

            count++;
            if (count != 0 && count % 4 == 0) {
                currentY = currentY - DIM_Y;
                currentX = 0;
            } else if (count != 0)
                currentX += DIM_X;

            for (int j = 0; j < DIM_Y; j++) {
                for (int i = 0; i < DIM_X; i++) {
                    mapText[currentX + i][currentY - j] = iterator[i][DIM_Y - 1 - j];
                }
            }
        }
        return mapText;
    }

    /**
     * Gets map text.
     *
     * @param avatarMap the avatar map
     * @return the map text
     */
    public String getMapText(Map<String, Avatar> avatarMap) {
        String[][] mapText = getMapMatrix(avatarMap);
        String map = "";
        for (int j = mapText[0].length - 1; j >= 0; j--) {
            for (int i = 0; i < mapText.length; i++) {
                map = map + mapText[i][j];
            }
            map = map + ANSI_RESET + "\n";
        }
        return map;
    }


    /**
     * Gets coordinates ammo card map.
     *
     * @return the coordinates ammo card map
     */
    public Map<Coordinates, AmmoCardClient> getCoordinatesAmmoCardMap() {
        Map<Coordinates, AmmoCardClient> coordinatesAmmoCardMap = new HashMap<>();

        for (int i = 0; i < x_max; i++) {
            for (int j = 0; j < y_max; j++) {
                if (squaresMatrix[i][j] != null && !squaresMatrix[i][j].isSpawnBoard())
                    try {
                        coordinatesAmmoCardMap.put((squaresMatrix[i][j]).getCoordinates(), squaresMatrix[i][j].getAmmoCard());
                    } catch (ClassCastException e) {
                        LOGGER.log(Level.SEVERE, e.getMessage(), e);
                    }
            }
        }

        return coordinatesAmmoCardMap;
    }


    /**
     * Gets x max.
     *
     * @return the x max
     */
    public int getX_max() {
        return x_max;
    }

    /**
     * Gets y max.
     *
     * @return the y max
     */
    public int getY_max() {
        return y_max;
    }
}