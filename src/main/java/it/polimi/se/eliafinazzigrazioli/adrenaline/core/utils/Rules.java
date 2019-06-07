package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

//Game rules constants
public final class Rules {
    private static final String RULES_FILE_NAME = "rules.json";
    private static final String RULES_FILE_PATH = "resources/rules.json";

    private static final Logger LOGGER = Logger.getLogger(Rules.class.getName());
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static JsonNode RULES = null;

    // TODO Rules should be loadable for single match, dynamically
    static {
        try {
            RULES = objectMapper.readTree(Rules.class.getClassLoader().getResourceAsStream(RULES_FILE_NAME)).get("default");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, Messages.MESSAGE_EXCEPTIONS_SERVER_RULES_FILE_NOT_FOUND + RULES_FILE_PATH, e);
        }
    }

    //PUBLIC RULES CONSTANTS
    private static final JsonNode PLAYER = RULES.get("player");

    // PLAYER CARDS
    private static final JsonNode CARDS = PLAYER.get("cards");
    public static final int PLAYER_CARDS_MAX_WEAPONS = CARDS.get("max_weapons").asInt(3);
    public static final int PLAYER_CARDS_MAX_POWER_UPS = CARDS.get("max_power_ups").asInt(3);
    // PLAYER PLAYER_BOARD
    private static final JsonNode PLAYER_BOARD = PLAYER.get("board");
    public static final int PLAYER_BOARD_MAX_MARKS_PER_TYPE = PLAYER_BOARD.get("max_marks_per_type").asInt(3);
    public static final int PLAYER_BOARD_MAX_SKULLS = PLAYER_BOARD.get("max_skulls").asInt(6);
    public static final int PLAYER_BOARD_MAX_DAMAGE = PLAYER_BOARD.get("max_damage").asInt(12);
    public static final int PLAYER_BOARD_MAX_MARKS = PLAYER_BOARD.get("max_marks").asInt(12);
    public static final int PLAYER_BOARD_DEAD_SHOOT = PLAYER_BOARD.get("dead_shoot").asInt(11);
    public static final int PLAYER_BOARD_MAX_AMMO = PLAYER_BOARD.get("max_ammo").asInt(3);
    public static final ArrayList<Integer> PLAYER_BOARD_DEATH_SCORES = asArray(PLAYER_BOARD.get("death_scores"));

    // GAME
    private static final JsonNode GAME = RULES.get("game");
    public static final int GAME_MIN_PLAYERS = GAME.get("min_players").asInt(3);
    public static final int GAME_MAX_PLAYERS = GAME.get("max_players").asInt(5);
    public static final int GAME_MAX_MAPS = GAME.get("max_maps").asInt(4);

    // TURN
    private static final JsonNode TURN = RULES.get("turn");
    public static final int MAX_MOVEMENTS = TURN.get("max_moves_simple").asInt(3);
    public static final int MAX_MOVEMENTS_BEFORE_COLLECTION = TURN.get("max_moves_collecting").asInt(1);
    public static final int MAX_MOVEMENTS_BEFORE_SHOOTING = TURN.get("max_moves_shooting").asInt(0);
    public static final int MAX_ACTIONS_AVAILABLE = TURN.get("max_actions_available").asInt(2);

    // SIMPLE MOVEMENT ADRENALINIC ACTION
    private static final JsonNode MOVEMENT_ADRENALINIC_ACTION = RULES.get("movement_adrenalinic_action");
    public static final int MOVEMENT_ADRENALINIC_ACTION_MOVES_SURPLUS = MOVEMENT_ADRENALINIC_ACTION.get("moves_surplus").asInt(0);
    public static final int MOVEMENT_ADRENALINIC_ACTION_MIN_DAMAGE = MOVEMENT_ADRENALINIC_ACTION.get("min_damages").asInt(1000);

    // SHOOTING MOVEMENT ADRENALINIC ACTION
    private static final JsonNode SHOOTING_ADRENALINIC_ACTION = RULES.get("shooting_adrenalinic_action");
    public static final int SHOOTING_ADRENALINIC_ACTION_MOVES_SURPLUS = SHOOTING_ADRENALINIC_ACTION.get("moves_surplus").asInt(1);
    public static final int SHOOTING_ADRENALINIC_ACTION_MIN_DAMAGE = SHOOTING_ADRENALINIC_ACTION.get("min_damages").asInt(6);

    // COLLECTING MOVEMENT ADRENALINIC ACTION
    private static final JsonNode COLLECTING_ADRENALINIC_ACTION = RULES.get("collection_adrenalinic_action");
    public static final int COLLECTING_ADRENALINIC_ACTION_MOVES_SURPLUS = COLLECTING_ADRENALINIC_ACTION.get("moves_surplus").asInt(1);
    public static final int COLLECTING_ADRENALINIC_ACTION_MIN_DAMAGE = COLLECTING_ADRENALINIC_ACTION.get("min_damages").asInt(3);

    // POWER UPS
    private static final JsonNode POWER_UPS = RULES.get("power_ups");
    public static final int POWER_UPS_PER_COLOR = POWER_UPS.get("cards_per_color").asInt(2);

    // GAME_BOARD
    private static final JsonNode GAME_BOARD = GAME.get("board");
    public static final int GAME_BOARD_X_MAX = GAME_BOARD.get("x_max").asInt(4);
    public static final int GAME_BOARD_Y_MAX = GAME_BOARD.get("y_max").asInt(5);

    private Rules() {
        throw new AssertionError();
    }

    public static ArrayList asArray(JsonNode arrayNode) {
        ArrayList arrayList = new ArrayList();
        if (arrayNode.isArray()) {
            for (final JsonNode objNode : arrayNode) {
                arrayList.add(objNode.asInt());
            }
        } else {
            return null;
        }
        return arrayList;
    }
}