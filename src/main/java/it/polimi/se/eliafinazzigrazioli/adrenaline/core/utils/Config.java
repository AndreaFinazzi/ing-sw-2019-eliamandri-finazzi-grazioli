package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

import it.polimi.se.eliafinazzigrazioli.adrenaline.Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * The type Config.
 */
// App configuration manager
public final class Config {

    private static final Logger LOGGER = Logger.getLogger(Config.class.getName());
    private static final Properties CONFIG = new Properties();

    static {
        try {
            InputStreamReader inputStreamReader;

            File jarFile = new File(Config.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File configFile = new File(jarFile.getParent() + Application.CONFIG_FILE_PATH);
            if (!configFile.exists())
                inputStreamReader = new InputStreamReader(Config.class.getResourceAsStream(Application.CONFIG_FILE_PATH));
            else
                inputStreamReader = new InputStreamReader(new FileInputStream(configFile));

            CONFIG.load(inputStreamReader);
        } catch (IOException | URISyntaxException e) {
            LOGGER.severe(Messages.MESSAGE_EXCEPTIONS_SERVER_CONFIG_FILE_NOT_FOUND + Application.CONFIG_FILE_PATH);
        }
    }

    /**
     * The constant CONFIG_SERVER_MESSAGES_FILE_PATH.
     */
    public static final String CONFIG_SERVER_MESSAGES_FILE_PATH = (String) CONFIG.getOrDefault("server.messages.file_path", "/config/messages.properties");
    /**
     * The constant CONFIG_SERVER_RULES_FILE_PATH.
     */
    public static final String CONFIG_SERVER_RULES_FILE_PATH = (String) CONFIG.getOrDefault("server.rules.file_path", "/config/rules.json");

    /**
     * The constant CONFIG_SERVER_SOCKET_PORT.
     */
    public static final int CONFIG_SERVER_SOCKET_PORT = Integer.parseInt((String) CONFIG.getOrDefault("server.net.socket.port", 9999));
    /**
     * The constant CONFIG_SERVER_RMI_PORT.
     */
    public static final int CONFIG_SERVER_RMI_PORT = Integer.parseInt((String) CONFIG.getOrDefault("server.net.rmi.port", 1099));
    /**
     * The constant CONFIG_SERVER_RMI_NAME.
     */
    public static final String CONFIG_SERVER_RMI_NAME = (String) CONFIG.getOrDefault("server.net.rmi.name", "ServerRMIManager");

    /**
     * The constant CONFIG_SERVER_PING_TIMEOUT.
     */
    public static final int CONFIG_SERVER_PING_TIMEOUT = Integer.parseInt((String) CONFIG.getOrDefault("server.ping.timeout", 1000));
    /**
     * The constant CONFIG_SERVER_NEW_GAME_TIMEOUT.
     */
    public static final int CONFIG_SERVER_NEW_GAME_TIMEOUT = Integer.parseInt((String) CONFIG.getOrDefault("server.new_game_timeout", 30000));
    /**
     * The constant CONFIG_MATCH_TURN_TIMEOUT.
     */
    public static final int CONFIG_MATCH_TURN_TIMEOUT = Integer.parseInt((String) CONFIG.getOrDefault("core.turn_timeout", 30000));


    /**
     * The constant CONFIG_CLIENT_GUI_ASSETS_DEFAULT_FORMAT.
     */
    public static final String CONFIG_CLIENT_GUI_ASSETS_DEFAULT_FORMAT = (String) CONFIG.getOrDefault("client.gui.assets.default.format", ".png");
    /**
     * The constant CONFIG_CLIENT_SERVER_IP.
     */
    public static final String CONFIG_CLIENT_SERVER_IP = (String) CONFIG.getOrDefault("client.net.server_ip", "localhost");
    /**
     * The constant CONFIG_CLIENT_CONNECTION_ATTEMPT_DELAY.
     */
    public static final int CONFIG_CLIENT_CONNECTION_ATTEMPT_DELAY = Integer.parseInt((String) CONFIG.getOrDefault("client.net.connection.delay", 1000));
    /**
     * The constant CONFIG_CLIENT_CONNECTION_MAX_ATTEMPTS.
     */
    public static final int CONFIG_CLIENT_CONNECTION_MAX_ATTEMPTS = Integer.parseInt((String) CONFIG.getOrDefault("client.net.connection.max_attempts", 1));


    /**
     * The constant CONFIG_CLIENT_GUI_PATH_FXML_ROOT.
     */
// Clients config parameters
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_ROOT = (String) CONFIG.getOrDefault("client.gui.path.fxml.root", "/client/GUI/fxml/");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_FXML_LOGIN.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_LOGIN = (String) CONFIG.getOrDefault("client.gui.path.fxml.login", "login.fxml");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_FXML_COMMANDS.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_COMMANDS = (String) CONFIG.getOrDefault("client.gui.path.fxml.commands", "commands.fxml");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_FXML_OPPONENT_PLAYER_INFO.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_OPPONENT_PLAYER_INFO = (String) CONFIG.getOrDefault("client.gui.path.fxml.opponent_player_info", "opponent_player_info.fxml");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_FXML_PLAYER_BOARD.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_PLAYER_BOARD = (String) CONFIG.getOrDefault("client.gui.path.fxml.player_board", "player_board.fxml");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_FXML_BOARD_SQUARE.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_BOARD_SQUARE = (String) CONFIG.getOrDefault("client.gui.path.fxml.board_square", "board_square.fxml");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_FXML_MAIN.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_MAIN = (String) CONFIG.getOrDefault("client.gui.path.fxml.main", "main.fxml");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_FXML_POWER_UP_CARD.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_POWER_UP_CARD = (String) CONFIG.getOrDefault("client.gui.path.fxml.power_up_card", "power_up_card.fxml");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_FXML_WEAPON_CARD.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_WEAPON_CARD = (String) CONFIG.getOrDefault("client.gui.path.fxml.weapon_card", "weapon_card.fxml");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_FXML_AVATAR.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_AVATAR = (String) CONFIG.getOrDefault("client.gui.path.fxml.avatar", "avatar.fxml");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_FXML_MARK.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_MARK = (String) CONFIG.getOrDefault("client.gui.path.fxml.mark", "mark.fxml");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_FXML_SKULL.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_SKULL = (String) CONFIG.getOrDefault("client.gui.path.fxml.skull", "skull.fxml");

    /**
     * The constant CONFIG_CLIENT_GUI_PATH_ASSETS_ROOT.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_ROOT = (String) CONFIG.getOrDefault("client.gui.path.assets.root", "/client/GUI/assets/");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_ASSETS_CARDS.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_CARDS = (String) CONFIG.getOrDefault("client.gui.path.assets.cards", "cards/");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_ASSETS_CARDS_ROTATED.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_CARDS_ROTATED = (String) CONFIG.getOrDefault("client.gui.path.assets.cards_rotated", "cards/rotated/");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_ASSETS_MAPS.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_MAPS = (String) CONFIG.getOrDefault("client.gui.path.assets.maps", "maps/");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_ASSETS_AMMO.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_AMMO = (String) CONFIG.getOrDefault("client.gui.path.assets.ammo", "ammo/");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_ASSETS_PLAYER_BOARDS.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_PLAYER_BOARDS = (String) CONFIG.getOrDefault("client.gui.path.assets.playerboards", "playerboards/");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_ASSETS_AVATARS.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_AVATARS = (String) CONFIG.getOrDefault("client.gui.path.assets.avatars", "avatars/");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_ASSETS_ICONS.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_ICONS = (String) CONFIG.getOrDefault("client.gui.path.assets.icons", "icons/");
    /**
     * The constant CONFIG_CLIENT_GUI_PATH_ASSETS_MARKS.
     */
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_MARKS = (String) CONFIG.getOrDefault("client.gui.path.assets.marks", "marks/");

    /**
     * The constant CONFIG_CLIENT_GUI_ASSETS_PREFIX_MAP.
     */
    public static final String CONFIG_CLIENT_GUI_ASSETS_PREFIX_MAP = (String) CONFIG.getOrDefault("client.gui.assets.prefix.map", "Map_");
    /**
     * The constant CONFIG_CLIENT_GUI_ASSETS_PREFIX_POWER_UP.
     */
    public static final String CONFIG_CLIENT_GUI_ASSETS_PREFIX_POWER_UP = (String) CONFIG.getOrDefault("client.gui.assets.prefix.power_up", "AD_powerups_IT_");
    /**
     * The constant CONFIG_CLIENT_GUI_ASSETS_PREFIX_WEAPON.
     */
    public static final String CONFIG_CLIENT_GUI_ASSETS_PREFIX_WEAPON = (String) CONFIG.getOrDefault("client.gui.assets.prefix.weapon", "AD_weapons_IT_");
    /**
     * The constant CONFIG_CLIENT_GUI_ASSETS_PREFIX_AMMO.
     */
    public static final String CONFIG_CLIENT_GUI_ASSETS_PREFIX_AMMO = (String) CONFIG.getOrDefault("client.gui.assets.prefix.ammo", "AD_ammo_");
    /**
     * The constant CONFIG_CLIENT_GUI_ASSETS_PREFIX_PLAYERBOARD.
     */
    public static final String CONFIG_CLIENT_GUI_ASSETS_PREFIX_PLAYERBOARD = (String) CONFIG.getOrDefault("client.gui.assets.prefix.playerboard", "PlayerBoard_");
    /**
     * The constant CONFIG_CLIENT_GUI_ASSETS_PREFIX_AVATAR.
     */
    public static final String CONFIG_CLIENT_GUI_ASSETS_PREFIX_AVATAR = (String) CONFIG.getOrDefault("client.gui.assets.prefix.avatar", "avatar_");
    /**
     * The constant CONFIG_CLIENT_GUI_ASSETS_PREFIX_MARK.
     */
    public static final String CONFIG_CLIENT_GUI_ASSETS_PREFIX_MARK = (String) CONFIG.getOrDefault("client.gui.assets.prefix.mark", "mark_");
    /**
     * The constant CONFIG_CLIENT_GUI_ASSETS_PREFIX_ICONS_ARROW.
     */
    public static final String CONFIG_CLIENT_GUI_ASSETS_PREFIX_ICONS_ARROW = (String) CONFIG.getOrDefault("client.gui.assets.prefix.icons.arrow", "arrow_");

    /**
     * The constant CONFIG_CLIENT_GUI_ASSETS_SUFFIX_PLAYERBOARD.
     */
    public static final String CONFIG_CLIENT_GUI_ASSETS_SUFFIX_PLAYERBOARD = (String) CONFIG.getOrDefault("client.gui.assets.suffix.playerboard", "_FF");

    /**
     * The constant CONFIG_CLIENT_GUI_ASSETS_ID_HIDDEN_CARD.
     */
    public static final String CONFIG_CLIENT_GUI_ASSETS_ID_HIDDEN_CARD = (String) CONFIG.getOrDefault("client.gui.assets.id.hidden_card", "02");
    /**
     * The constant CONFIG_CLIENT_GUI_ASSETS_ID_HIDDEN_AMMO.
     */
    public static final String CONFIG_CLIENT_GUI_ASSETS_ID_HIDDEN_AMMO = (String) CONFIG.getOrDefault("client.gui.assets.id.hidden_ammo", "04");
    /**
     * The constant CONFIG_CLIENT_GUI_ASSETS_ID_SKULL.
     */
    public static final String CONFIG_CLIENT_GUI_ASSETS_ID_SKULL = (String) CONFIG.getOrDefault("client.gui.assets.id.skull", "skull");
    /**
     * The constant CONFIG_CLIENT_GUI_ASSETS_ID_SKULL_ROTATED.
     */
    public static final String CONFIG_CLIENT_GUI_ASSETS_ID_SKULL_ROTATED = (String) CONFIG.getOrDefault("client.gui.assets.id.skull_rotated", "skull_rotated");

    private Config() {
        throw new AssertionError();
    }

    /**
     * Get string.
     *
     * @param key the key
     * @return the string
     */
    public static String get(String key) {
        return CONFIG.getProperty(key);
    }

    /**
     * Get string.
     *
     * @param key the key
     * @param defaultValue the default value
     * @return the string
     */
    public static String get(String key, String defaultValue) {
        return CONFIG.getProperty(key, defaultValue);
    }
}