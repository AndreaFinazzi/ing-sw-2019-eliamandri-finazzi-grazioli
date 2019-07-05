package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

//Game rules constants
public final class Rules {
    private static final String RULES_FILE_PATH = "/rules.json";

    private static final Logger LOGGER = Logger.getLogger(Rules.class.getName());
    private static final Gson gson = new Gson();

    private static JsonObject RULES = null;

    // TODO Rules should be loadable for single match, dynamically
    static {
        RULES = gson.fromJson(new InputStreamReader(Rules.class.getResourceAsStream(RULES_FILE_PATH)), JsonElement.class).getAsJsonObject().getAsJsonObject("default");
    }

    //PUBLIC RULES CONSTANTS
    private static final JsonObject PLAYER = RULES.getAsJsonObject("player");

    // PLAYER CARDS
    private static final JsonObject CARDS = PLAYER.getAsJsonObject("cards");
    public static final int PLAYER_CARDS_MAX_WEAPONS = CARDS.get("max_weapons").getAsInt();
    public static final int PLAYER_CARDS_MAX_POWER_UPS = CARDS.get("max_power_ups").getAsInt();
    // PLAYER PLAYER_BOARD
    private static final JsonObject PLAYER_BOARD = PLAYER.getAsJsonObject("board");
    public static final int PLAYER_BOARD_MAX_MARKS_DELIVERED = PLAYER_BOARD.get("max_marks_delivered").getAsInt();
    public static final int PLAYER_BOARD_MAX_MARKS_PER_TYPE = PLAYER_BOARD.get("max_marks_per_type").getAsInt();
    public static final int PLAYER_BOARD_MAX_SKULLS = PLAYER_BOARD.get("max_skulls").getAsInt();
    public static final int PLAYER_BOARD_MAX_DAMAGE = PLAYER_BOARD.get("max_damage").getAsInt();
    public static final int PLAYER_BOARD_MAX_MARKS = PLAYER_BOARD.get("max_marks").getAsInt();
    public static final int PLAYER_BOARD_DEAD_SHOOT = PLAYER_BOARD.get("dead_shoot").getAsInt();
    public static final int PLAYER_BOARD_MAX_AMMO = PLAYER_BOARD.get("max_ammo").getAsInt();
    public static final ArrayList<Integer> PLAYER_BOARD_DEATH_SCORES = asIntegerArrayList(PLAYER_BOARD.getAsJsonArray("death_scores"));

    // GAME
    private static final JsonObject GAME = RULES.getAsJsonObject("game");
    public static final int GAME_MIN_PLAYERS = GAME.get("min_players").getAsInt();
    public static final int GAME_MAX_PLAYERS = GAME.get("max_players").getAsInt();
    public static final int GAME_MAX_MAPS = GAME.get("max_maps").getAsInt();
    public static final int GAME_AMMO_CARDS_DUPLICATES = GAME.get("ammo_cards_duplicates").getAsInt();
    public static final int GAME_FIRST_BLOOD_POINTS = GAME.get("first_blood_points").getAsInt();
    public static final int GAME_DOUBLE_KILL_POINTS = GAME.get("double_kill_points").getAsInt();
    public static final int GAME_MAX_KILL_TRACK_SKULLS = GAME.get("max_kill_track_skulls").getAsInt();


    // TURN
    private static final JsonObject TURN = RULES.getAsJsonObject("turn");
    public static final int MAX_MOVEMENTS = TURN.get("max_moves_simple").getAsInt();
    public static final int MAX_MOVEMENTS_BEFORE_COLLECTION = TURN.get("max_moves_collecting").getAsInt();
    public static final int MAX_MOVEMENTS_BEFORE_SHOOTING = TURN.get("max_moves_shooting").getAsInt();
    public static final int MAX_ACTIONS_AVAILABLE = TURN.get("max_actions_available").getAsInt();

    // SIMPLE MOVEMENT ADRENALINIC ACTION
    private static final JsonObject MOVEMENT_ADRENALINIC_ACTION = RULES.getAsJsonObject("movement_adrenalinic_action");
    public static final int MOVEMENT_ADRENALINIC_ACTION_MOVES_SURPLUS = MOVEMENT_ADRENALINIC_ACTION.get("moves_surplus").getAsInt();
    public static final int MOVEMENT_ADRENALINIC_ACTION_MIN_DAMAGE = MOVEMENT_ADRENALINIC_ACTION.get("min_damages").getAsInt();

    // SHOOTING MOVEMENT ADRENALINIC ACTION
    private static final JsonObject SHOOTING_ADRENALINIC_ACTION = RULES.getAsJsonObject("shooting_adrenalinic_action");
    public static final int SHOOTING_ADRENALINIC_ACTION_MOVES_SURPLUS = SHOOTING_ADRENALINIC_ACTION.get("moves_surplus").getAsInt();
    public static final int SHOOTING_ADRENALINIC_ACTION_MIN_DAMAGE = SHOOTING_ADRENALINIC_ACTION.get("min_damages").getAsInt();

    // COLLECTING MOVEMENT ADRENALINIC ACTION
    private static final JsonObject COLLECTING_ADRENALINIC_ACTION = RULES.getAsJsonObject("collection_adrenalinic_action");
    public static final int COLLECTING_ADRENALINIC_ACTION_MOVES_SURPLUS = COLLECTING_ADRENALINIC_ACTION.get("moves_surplus").getAsInt();
    public static final int COLLECTING_ADRENALINIC_ACTION_MIN_DAMAGE = COLLECTING_ADRENALINIC_ACTION.get("min_damages").getAsInt();

    // POWER UPS
    private static final JsonObject POWER_UPS = RULES.getAsJsonObject("power_ups");
    public static final int POWER_UPS_PER_COLOR = POWER_UPS.get("cards_per_color").getAsInt();

    // GAME_BOARD
    private static final JsonObject GAME_BOARD = GAME.getAsJsonObject("board");
    public static final int GAME_BOARD_X_MAX = GAME_BOARD.get("x_max").getAsInt();
    public static final int GAME_BOARD_Y_MAX = GAME_BOARD.get("y_max").getAsInt();
    public static final int GAME_BOARD_MAX_WEAPONS_ON_SPAWN = GAME_BOARD.get("max_weapons_on_spawn").getAsInt();

    private Rules() {
        throw new AssertionError();
    }

    public static ArrayList<Integer> asIntegerArrayList(JsonArray arrayNode) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (arrayNode.isJsonArray()) {
            for (final JsonElement objNode : arrayNode) {
                arrayList.add(objNode.getAsInt());
            }
        } else {
            return null;
        }
        return arrayList;
    }
}