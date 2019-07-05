package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

//Game rules constants
public final class Rules {
    private static final String RULES_FILE_PATH = Config.CONFIG_SERVER_RULES_FILE_PATH;

    private static final Logger LOGGER = Logger.getLogger(Rules.class.getName());
    private static final Gson gson = new Gson();

    private static InputStreamReader inputStreamReader;
    private static JsonObject RULES = null;

    // TODO Rules should be loadable for single match, dynamically
    static {
        try {
            File jarFile = new File(Rules.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File rulesFile = new File(jarFile.getParent() + RULES_FILE_PATH);
            if (!rulesFile.exists())
                inputStreamReader = new InputStreamReader(Rules.class.getResourceAsStream(RULES_FILE_PATH));
            else
                inputStreamReader = new InputStreamReader(new FileInputStream(rulesFile));

        } catch (URISyntaxException | FileNotFoundException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }

        RULES = gson.fromJson(inputStreamReader, JsonElement.class).getAsJsonObject().getAsJsonObject("default");
    }

    //PUBLIC RULES CONSTANTS
    private static final JsonObject PLAYER = RULES.getAsJsonObject("player");

    // PLAYER CARDS
    private static final JsonObject CARDS = PLAYER.getAsJsonObject("cards");
    public static final int PLAYER_CARDS_MAX_WEAPONS = asInt(CARDS.get("max_weapons"), 3);
    public static final int PLAYER_CARDS_MAX_POWER_UPS = asInt(CARDS.get("max_power_ups"), 3);
    // PLAYER PLAYER_BOARD
    private static final JsonObject PLAYER_BOARD = PLAYER.getAsJsonObject("board");
    public static final int PLAYER_BOARD_MAX_MARKS_DELIVERED = asInt(PLAYER_BOARD.get("max_marks_delivered"), 3);
    public static final int PLAYER_BOARD_MAX_MARKS_PER_TYPE = asInt(PLAYER_BOARD.get("max_marks_per_type"), 3);
    public static final int PLAYER_BOARD_MAX_SKULLS = asInt(PLAYER_BOARD.get("max_skulls"), 8);
    public static final int PLAYER_BOARD_MAX_DAMAGE = asInt(PLAYER_BOARD.get("max_damage"), 12);
    public static final int PLAYER_BOARD_MAX_MARKS = asInt(PLAYER_BOARD.get("max_marks"), 12);
    public static final int PLAYER_BOARD_DEAD_SHOOT = asInt(PLAYER_BOARD.get("dead_shoot"), 11);
    public static final int PLAYER_BOARD_MAX_AMMO = asInt(PLAYER_BOARD.get("max_ammo"), 3);
    public static final ArrayList<Integer> PLAYER_BOARD_DEATH_SCORES = asIntegerArrayList(PLAYER_BOARD.getAsJsonArray("death_scores"), new ArrayList<>(Arrays.asList(8, 6, 4, 2, 1, 1)));


    // GAME
    private static final JsonObject GAME = RULES.getAsJsonObject("game");
    public static final int GAME_MIN_PLAYERS = asInt(GAME.get("min_players"), 3);
    public static final int GAME_MAX_PLAYERS = asInt(GAME.get("max_players"), 5);
    public static final int GAME_MAX_MAPS = asInt(GAME.get("max_maps"), 4);
    public static final int GAME_AMMO_CARDS_DUPLICATES = asInt(GAME.get("ammo_cards_duplicates"), 3);
    public static final int GAME_FIRST_BLOOD_POINTS = asInt(GAME.get("first_blood_points"), 1);
    public static final int GAME_DOUBLE_KILL_POINTS = asInt(GAME.get("double_kill_points"), 1);
    public static final int GAME_MAX_KILL_TRACK_SKULLS = asInt(GAME.get("max_kill_track_skulls"), 8);


    // TURN
    private static final JsonObject TURN = RULES.getAsJsonObject("turn");
    public static final int MAX_MOVEMENTS = asInt(TURN.get("max_moves_simple"), 3);
    public static final int MAX_MOVEMENTS_BEFORE_COLLECTION = asInt(TURN.get("max_moves_collecting"), 1);
    public static final int MAX_MOVEMENTS_BEFORE_SHOOTING = asInt(TURN.get("max_moves_shooting"), 0);
    public static final int MAX_ACTIONS_AVAILABLE = asInt(TURN.get("max_actions_available"), 2);

    // SIMPLE MOVEMENT ADRENALINIC ACTION
    private static final JsonObject MOVEMENT_ADRENALINIC_ACTION = RULES.getAsJsonObject("movement_adrenalinic_action");
    public static final int MOVEMENT_ADRENALINIC_ACTION_MOVES_SURPLUS = asInt(MOVEMENT_ADRENALINIC_ACTION.get("moves_surplus"), 0);
    public static final int MOVEMENT_ADRENALINIC_ACTION_MIN_DAMAGE = asInt(MOVEMENT_ADRENALINIC_ACTION.get("min_damages"), 9999);

    // SHOOTING MOVEMENT ADRENALINIC ACTION
    private static final JsonObject SHOOTING_ADRENALINIC_ACTION = RULES.getAsJsonObject("shooting_adrenalinic_action");
    public static final int SHOOTING_ADRENALINIC_ACTION_MOVES_SURPLUS = asInt(SHOOTING_ADRENALINIC_ACTION.get("moves_surplus"), 1);
    public static final int SHOOTING_ADRENALINIC_ACTION_MIN_DAMAGE = asInt(SHOOTING_ADRENALINIC_ACTION.get("min_damages"), 6);

    // COLLECTING MOVEMENT ADRENALINIC ACTION
    private static final JsonObject COLLECTING_ADRENALINIC_ACTION = RULES.getAsJsonObject("collection_adrenalinic_action");
    public static final int COLLECTING_ADRENALINIC_ACTION_MOVES_SURPLUS = asInt(COLLECTING_ADRENALINIC_ACTION.get("moves_surplus"), 1);
    public static final int COLLECTING_ADRENALINIC_ACTION_MIN_DAMAGE = asInt(COLLECTING_ADRENALINIC_ACTION.get("min_damages"), 3);

    // FINAL FRENZY PARAMETERS
    private static final JsonObject FINAL_FRENZY = RULES.getAsJsonObject("final_frenzy");
    public static final ArrayList<Integer> FINAL_FRENZY_DEATH_SCORES = asIntegerArrayList(FINAL_FRENZY .getAsJsonArray("death_scores"), new ArrayList<>(Arrays.asList(2,1,1,1)));

    // DOUBLE ACTION CASE
    private static final JsonObject DOUBLE_ACTION = FINAL_FRENZY.getAsJsonObject("single_action");
    public static final int FINAL_FRENZY_DOUBLE_ACTION_MAX_MOVES = asInt(DOUBLE_ACTION.get("max_movements"),4);
    public static final int FINAL_FRENZY_DOUBLE_ACTION_MAX_MOVES_BEFORE_COLLECTION = asInt(DOUBLE_ACTION.get("max_moves_collecting"), 2);
    public static final int FINAL_FRENZY_DOUBLE_ACTION_MAX_MOVES_BEFORE_SHOOTING = asInt(DOUBLE_ACTION.get("max_moves_shooting"), 1);

    // SINGLE ACTION CASE
    private static final JsonObject SINGLE_ACTION = FINAL_FRENZY.getAsJsonObject("single_action");
    public static final int FINAL_FRENZY_SINGLE_ACTION_MAX_MOVES_BEFORE_COLLECTION = asInt(SINGLE_ACTION.get("max_moves_collecting"), 3);
    public static final int FINAL_FRENZY_SINGLE_ACTION_MAX_MOVES_BEFORE_SHOOTING = asInt(SINGLE_ACTION.get("max_moves_shooting"), 2);

    // POWER UPS
    private static final JsonObject POWER_UPS = RULES.getAsJsonObject("power_ups");
    public static final int POWER_UPS_PER_COLOR = asInt(POWER_UPS.get("cards_per_color"), 2);

    // GAME_BOARD
    private static final JsonObject GAME_BOARD = GAME.getAsJsonObject("board");
    public static final int GAME_BOARD_X_MAX = asInt(GAME_BOARD.get("x_max"), 4);
    public static final int GAME_BOARD_Y_MAX = asInt(GAME_BOARD.get("y_max"), 3);
    public static final int GAME_BOARD_MAX_WEAPONS_ON_SPAWN = asInt(GAME_BOARD.get("max_weapons_on_spawn"), 3);

    private Rules() {
        throw new AssertionError();
    }

    private static ArrayList<Integer> asIntegerArrayList(JsonArray arrayNode, ArrayList<Integer> defaultValue) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        if (arrayNode.isJsonArray()) {
            try {
                for (final JsonElement objNode : arrayNode) {
                    arrayList.add(objNode.getAsInt());
                }
            } catch (ClassCastException | IllegalStateException e) {
                LOGGER.info(e.getMessage());
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
        return arrayList;
    }

    private static Integer asInt(JsonElement jsonElement, int defaultValue) {
        if (jsonElement != null) {
            try {
                return jsonElement.getAsInt();
            } catch (ClassCastException | IllegalStateException e) {
                return defaultValue;
            }
        }

        return defaultValue;
    }
}