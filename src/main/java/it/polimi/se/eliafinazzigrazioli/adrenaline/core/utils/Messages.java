package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

// Centralized message manager
public final class Messages {
    private static final String MESSAGES_FILE_PATH = Config.CONFIG_SERVER_MESSAGES_FILE_PATH;
    private static final String MESSAGES_FILE_NAME = Config.CONFIG_SERVER_MESSAGES_FILE_NAME;

    private static final String MESSAGES_FILE_NOT_FOUND_ERROR = MESSAGES_FILE_NAME + " file not found in " + MESSAGES_FILE_PATH;

    private static final Properties MESSAGES = new Properties();

    private static final Logger LOGGER = Logger.getGlobal();

    static {
        try {
            MESSAGES.load(Config.class.getClassLoader().getResourceAsStream(MESSAGES_FILE_NAME));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static final String MESSAGE_LOGGING_INFO_SERVER_STARTED = MESSAGES.getProperty("app.server.log.info.server_started", "DEFAULT: Server started!");
    public static final String MESSAGE_EXCEPTIONS_SERVER_HANDLER_NOT_IMPLEMENTED = MESSAGES.getProperty("app.exceptions.server.handler_not_implemented", "DEFAULT: Handler not implemented");
    public static final String MESSAGE_EXCEPTIONS_SERVER_LISTENER_NOT_FOUND = MESSAGES.getProperty("app.exceptions.server.listener_not_found", "DEFAULT: Listener not found");
    public static final String MESSAGE_EXCEPTIONS_SERVER_CONFIG_FILE_NOT_FOUND = MESSAGES.getProperty("app.exceptions.server.config_file_not_found", "DEFAULT: Config file not found");
    public static final String MESSAGE_EXCEPTIONS_SERVER_RULES_FILE_NOT_FOUND = MESSAGES.getProperty("app.exceptions.server.rules_file_not_found", "DEFAULT: Rules file not found");

    //Game related messages
    //Exceptions
    public static final String MESSAGE_EXCEPTIONS_GAME_OUT_OF_BOUND = MESSAGES.getProperty("app.exceptions.game.out_of_bound", "DEFAULT: Ammo not available");

    //Ammo
    public static final String MESSAGE_EXCEPTIONS_GAME_AMMO_NOT_AVAILABLE = MESSAGES.getProperty("app.exceptions.game.ammo.not_available", "DEFAULT: Ammo not available");

    //Player
    public static final String MESSAGE_EXCEPTIONS_GAME_PLAYER_MARKS_OUT_OF_BOUND = MESSAGES.getProperty("app.exceptions.game.player.marks_out_of_bound", "DEFAULT: Marks out of bound");
    public static final String MESSAGE_EXCEPTIONS_GAME_PLAYER_DAMAGES_OUT_OF_BOUND = MESSAGES.getProperty("app.exceptions.game.player.damages_out_of_bound", "DEFAULT: Damages out of bound");
    public static final String MESSAGE_EXCEPTIONS_GAME_PLAYER_SKULLS_OUT_OF_BOUND = MESSAGES.getProperty("app.exceptions.game.player.skulls_out_of_bound", "DEFAULT: Skulls out of bound");

    //Match
    public static final String MESSAGE_EXCEPTIONS_GAME_MATCH_PLAYER_ALREADY_PRESENT = MESSAGES.getProperty("app.exceptions.game.match.player_already_present", "DEFAULT: Player is already connected");
    public static final String MESSAGE_EXCEPTIONS_GAME_MATCH_PLAYERS_OUT_OF_BOUND = MESSAGES.getProperty("app.exceptions.game.match.players_out_of_bounds", "DEFAULT: Players out of bound");
    public static final String MESSAGE_EXCEPTIONS_GAME_MATCH_AVATAR_NOT_AVAILABLE = MESSAGES.getProperty("app.exceptions.game.match.avatar_not_available", "DEFAULT: Avatar not available");

    //WeaponCard
    public static final String MESSAGE_EXCEPTIONS_GAME_WEAPON_CARD_WEAPON_FILE_NOT_FOUND = MESSAGES.getProperty("app.exceptions.game.match.weapon_file_not_found", "DEFAULT: Weapon doesn't exist");

    //Events messages
    //Model
    public static final String MESSAGE_EVENTS_MODEL_DEFAULT = MESSAGES.getProperty("app.events.model.default");
    //View
    public static final String MESSAGE_EVENTS_VIEW_DEFAULT = MESSAGES.getProperty("app.events.view.default");

    // Network
    public static final String MESSAGE_EXCEPTIONS_NETWORK_SOCKET_SEND_FAILED = MESSAGES.getProperty("app.exceptions.network.socket.send_failed", "DEFAULT: Socket send failed!");

}
