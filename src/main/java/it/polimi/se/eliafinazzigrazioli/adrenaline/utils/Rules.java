package it.polimi.se.eliafinazzigrazioli.adrenaline.utils;

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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger LOGGER = Logger.getGlobal();
    // PLAYER CARDS
    private static final JsonNode CARDS = PLAYER.get("cards");
    public static final int PLAYER_CARDS_MAX_WEAPONS = CARDS.get("max_weapons").asInt(3);
    public static final int PLAYER_CARDS_MAX_POWER_UPS = CARDS.get("max_power_ups").asInt(3);
    // PLAYER BOARD
    private static final JsonNode BOARD = PLAYER.get("board");
    public static final int PLAYER_BOARD_MAX_MARKS_PER_TYPE = BOARD.get("max_marks_per_type").asInt(3);
    public static final int PLAYER_BOARD_MAX_SKULLS = BOARD.get("max_skulls").asInt(6);
    public static final int PLAYER_BOARD_MAX_DAMAGE = BOARD.get("max_damage").asInt(12);
    public static final int PLAYER_BOARD_MAX_MARKS = BOARD.get("max_marks").asInt(12);
    public static final int PLAYER_BOARD_DEAD_SHOOT = BOARD.get("dead_shoot").asInt(11);
    public static final int PLAYER_BOARD_FIRST_SHOOT = BOARD.get("first_shoot").asInt(1);
    public static final int PLAYER_BOARD_MAX_AMMO = BOARD.get("max_ammo").asInt(3);
    public static final ArrayList<Integer> PLAYER_BOARD_DEATH_SCORES = asArray(BOARD.get("death_scores"));
    private static JsonNode RULES = null;
    //PUBLIC RULES CONSTANTS
    private static final JsonNode PLAYER = RULES.get("player");

    // TODO Rules should be loadable for single match, dynamically
    static {
        try {
            RULES = objectMapper.readTree(Rules.class.getClassLoader().getResourceAsStream(RULES_FILE_NAME)).get("default");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, Messages.get("app.exceptions.server.rules_file_not_found") + RULES_FILE_PATH, e);
        }
    }


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