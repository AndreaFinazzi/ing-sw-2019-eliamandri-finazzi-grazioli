package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Messages.
 */
// Centralized message manager
public final class Messages {
    private static final Logger LOGGER = Logger.getGlobal();

    private static final String MESSAGES_FILE_PATH = Config.CONFIG_SERVER_MESSAGES_FILE_PATH;

    private static final String MESSAGES_FILE_NOT_FOUND_ERROR = MESSAGES_FILE_PATH + " file not found in " + MESSAGES_FILE_PATH;

    private static final Properties MESSAGES = new Properties();

    static {
        try {
            InputStreamReader inputStreamReader;

            File jarFile = new File(Messages.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File configFile = new File(jarFile.getParent() + MESSAGES_FILE_PATH);
            if (!configFile.exists())
                inputStreamReader = new InputStreamReader(Messages.class.getResourceAsStream(MESSAGES_FILE_PATH));
            else
                inputStreamReader = new InputStreamReader(new FileInputStream(configFile));

            MESSAGES.load(inputStreamReader);
        } catch (IOException | URISyntaxException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * The constant MESSAGE_LOGGING_INFO_SERVER_STARTED.
     */
    public static final String MESSAGE_LOGGING_INFO_SERVER_STARTED = MESSAGES.getProperty("app.server.log.info.server_started", "DEFAULT: Server started!");
    /**
     * The constant MESSAGE_LOGGING_INFO_SOCKET_STARTING.
     */
    public static final String MESSAGE_LOGGING_INFO_SOCKET_STARTING = MESSAGES.getProperty("app.server.log.info.socket.starting", "DEFAULT: Server Socket started!");
    /**
     * The constant MESSAGE_LOGGING_INFO_SOCKET_ACCEPT.
     */
    public static final String MESSAGE_LOGGING_INFO_SOCKET_ACCEPT = MESSAGES.getProperty("app.server.log.info.socket.accept", "DEFAULT: Waiting a connection...");
    /**
     * The constant MESSAGE_LOGGING_INFO_SOCKET_NEW_CLIENT.
     */
    public static final String MESSAGE_LOGGING_INFO_SOCKET_NEW_CLIENT = MESSAGES.getProperty("app.server.log.info.socket.new_client", "DEFAULT: New client connected");

    /**
     * The constant MESSAGE_LOGGING_INFO_RMI_STARTING.
     */
    public static final String MESSAGE_LOGGING_INFO_RMI_STARTING = MESSAGES.getProperty("app.server.log.info.rmi.starting", "DEFAULT: Server RMI started!");
    /**
     * The constant MESSAGE_LOGGING_INFO_CLIENT_HANDLER_INITIALIZED.
     */
    public static final String MESSAGE_LOGGING_INFO_CLIENT_HANDLER_INITIALIZED = MESSAGES.getProperty("app.server.log.info.client_handler.started", "DEFAULT: ClientHandler initialized");
    /**
     * The constant MESSAGE_LOGGING_INFO_SERVER_RMI_INTERRUPTED.
     */
    public static final String MESSAGE_LOGGING_INFO_SERVER_RMI_INTERRUPTED = MESSAGES.getProperty("app.server.log.info.rmi.error", "DEFAULT: RMI closed!");
    /**
     * The constant MESSAGE_LOGGING_INFO_CLIENT_DISCONNECTED.
     */
    public static final String MESSAGE_LOGGING_INFO_CLIENT_DISCONNECTED = MESSAGES.getProperty("app.server.log.info.client_disconnected", "DEFAULT: Client disconnected!");
    /**
     * The constant MESSAGE_LOGGING_INFO_EVENT_FAILURE.
     */
    public static final String MESSAGE_LOGGING_INFO_EVENT_FAILURE = MESSAGES.getProperty("app.server.log.info.rmi.event_failure", "DEFAULT: Events generation failed!");
    /**
     * The constant MESSAGE_LOGGING_INFO_MATCH_NEW.
     */
    public static final String MESSAGE_LOGGING_INFO_MATCH_NEW = MESSAGES.getProperty("app.server.log.info.match.new", "DEFAULT: Next match initialization started!");

    /**
     * The constant MESSAGE_EXCEPTIONS_SERVER_HANDLER_NOT_IMPLEMENTED.
     */
    public static final String MESSAGE_EXCEPTIONS_SERVER_HANDLER_NOT_IMPLEMENTED = MESSAGES.getProperty("app.exceptions.server.handler_not_implemented", "DEFAULT: Handler not implemented");
    /**
     * The constant MESSAGE_EXCEPTIONS_SERVER_LISTENER_NOT_FOUND.
     */
    public static final String MESSAGE_EXCEPTIONS_SERVER_LISTENER_NOT_FOUND = MESSAGES.getProperty("app.exceptions.server.listener_not_found", "DEFAULT: Listener not found");
    /**
     * The constant MESSAGE_EXCEPTIONS_SERVER_CONFIG_FILE_NOT_FOUND.
     */
    public static final String MESSAGE_EXCEPTIONS_SERVER_CONFIG_FILE_NOT_FOUND = MESSAGES.getProperty("app.exceptions.server.config_file_not_found", "DEFAULT: Config file not found");
    /**
     * The constant MESSAGE_EXCEPTIONS_SERVER_RULES_FILE_NOT_FOUND.
     */
    public static final String MESSAGE_EXCEPTIONS_SERVER_RULES_FILE_NOT_FOUND = MESSAGES.getProperty("app.exceptions.server.rules_file_not_found", "DEFAULT: Rules file not found");

    /**
     * The constant MESSAGE_EXCEPTIONS_GAME_OUT_OF_BOUND.
     */
//Game related messages
    //Exceptions
    public static final String MESSAGE_EXCEPTIONS_GAME_OUT_OF_BOUND = MESSAGES.getProperty("app.exceptions.game.out_of_bound", "DEFAULT: Ammo not available");

    /**
     * The constant MESSAGE_EXCEPTIONS_GAME_AMMO_NOT_AVAILABLE.
     */
//Ammo
    public static final String MESSAGE_EXCEPTIONS_GAME_AMMO_NOT_AVAILABLE = MESSAGES.getProperty("app.exceptions.game.ammo.not_available", "DEFAULT: Ammo not available");

    /**
     * The constant MESSAGE_EXCEPTIONS_GAME_PLAYER_MARKS_OUT_OF_BOUND.
     */
//Player
    public static final String MESSAGE_EXCEPTIONS_GAME_PLAYER_MARKS_OUT_OF_BOUND = MESSAGES.getProperty("app.exceptions.game.player.marks_out_of_bound", "DEFAULT: Marks out of bound");
    /**
     * The constant MESSAGE_EXCEPTIONS_GAME_PLAYER_DAMAGES_OUT_OF_BOUND.
     */
    public static final String MESSAGE_EXCEPTIONS_GAME_PLAYER_DAMAGES_OUT_OF_BOUND = MESSAGES.getProperty("app.exceptions.game.player.damages_out_of_bound", "DEFAULT: Damages out of bound");
    /**
     * The constant MESSAGE_EXCEPTIONS_GAME_PLAYER_SKULLS_OUT_OF_BOUND.
     */
    public static final String MESSAGE_EXCEPTIONS_GAME_PLAYER_SKULLS_OUT_OF_BOUND = MESSAGES.getProperty("app.exceptions.game.player.skulls_out_of_bound", "DEFAULT: Skulls out of bound");

    /**
     * The constant MESSAGE_EXCEPTIONS_GAME_MATCH_PLAYER_ALREADY_PRESENT.
     */
//Match
    public static final String MESSAGE_EXCEPTIONS_GAME_MATCH_PLAYER_ALREADY_PRESENT = MESSAGES.getProperty("app.exceptions.game.match.player_already_present", "DEFAULT: Player is already connected");
    /**
     * The constant MESSAGE_EXCEPTIONS_GAME_MATCH_PLAYERS_OUT_OF_BOUND.
     */
    public static final String MESSAGE_EXCEPTIONS_GAME_MATCH_PLAYERS_OUT_OF_BOUND = MESSAGES.getProperty("app.exceptions.game.match.players_out_of_bounds", "DEFAULT: Players out of bound");
    /**
     * The constant MESSAGE_EXCEPTIONS_GAME_MATCH_BOARD_OUT_OF_BOUND.
     */
    public static final String MESSAGE_EXCEPTIONS_GAME_MATCH_BOARD_OUT_OF_BOUND = MESSAGES.getProperty("app.exceptions.game.match.board_out_of_bounds", "DEFAULT: OutOfBoundException");
    /**
     * The constant MESSAGE_EXCEPTIONS_GAME_MATCH_AVATAR_NOT_AVAILABLE.
     */
    public static final String MESSAGE_EXCEPTIONS_GAME_MATCH_AVATAR_NOT_AVAILABLE = MESSAGES.getProperty("app.exceptions.game.match.avatar_not_available", "DEFAULT: Avatar not available");

    /**
     * The constant MESSAGE_EXCEPTIONS_GAME_WEAPON_CARD_WEAPON_FILE_NOT_FOUND.
     */
//WeaponCard
    public static final String MESSAGE_EXCEPTIONS_GAME_WEAPON_CARD_WEAPON_FILE_NOT_FOUND = MESSAGES.getProperty("app.exceptions.game.match.weapon_file_not_found", "DEFAULT: Weapon doesn't exist");

    /**
     * The constant MESSAGE_EVENTS_MODEL_DEFAULT.
     */
//Events messages
    //Model
    public static final String MESSAGE_EVENTS_MODEL_DEFAULT = MESSAGES.getProperty("app.events.model.default");
    /**
     * The constant MESSAGE_EVENTS_VIEW_DEFAULT.
     */
//View
    public static final String MESSAGE_EVENTS_VIEW_DEFAULT = MESSAGES.getProperty("app.events.view.default");

    /**
     * The constant MESSAGE_EXCEPTIONS_NETWORK_SOCKET_SEND_FAILED.
     */
// Network
    public static final String MESSAGE_EXCEPTIONS_NETWORK_SOCKET_SEND_FAILED = MESSAGES.getProperty("app.exceptions.network.socket.send_failed", "DEFAULT: Socket send failed!");

}
