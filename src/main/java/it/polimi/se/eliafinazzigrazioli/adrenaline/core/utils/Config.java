package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

import it.polimi.se.eliafinazzigrazioli.adrenaline.Application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Logger;

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

    public static final String CONFIG_SERVER_MESSAGES_FILE_PATH = (String) CONFIG.getOrDefault("server.messages.file_path", "/config/messages.properties");
    public static final String CONFIG_SERVER_RULES_FILE_PATH = (String) CONFIG.getOrDefault("server.rules.file_path", "/config/rules.json");

    public static final int CONFIG_SERVER_SOCKET_PORT = Integer.parseInt((String) CONFIG.getOrDefault("server.net.socket.port", 9999));
    public static final int CONFIG_SERVER_RMI_PORT = Integer.parseInt((String) CONFIG.getOrDefault("server.net.rmi.port", 1099));
    public static final String CONFIG_SERVER_RMI_NAME = (String) CONFIG.getOrDefault("server.net.rmi.name", "ServerRMIManager");

    public static final int CONFIG_SERVER_NEW_GAME_TIMEOUT = Integer.parseInt((String) CONFIG.getOrDefault("server.new_game_timeout", 30000));
    public static final int CONFIG_MATCH_TURN_TIMEOUT = Integer.parseInt((String) CONFIG.getOrDefault("match.turn_timeout", 30000));


    public static final String CONFIG_CLIENT_GUI_ASSETS_DEFAULT_FORMAT = (String) CONFIG.getOrDefault("client.gui.assets.default.format", ".png");
    public static final String CONFIG_CLIENT_SERVER_IP = (String) CONFIG.getOrDefault("client.net.server_ip", "localhost");
    public static final int CONFIG_CLIENT_CONNECTION_ATTEMPT_DELAY = Integer.parseInt((String) CONFIG.getOrDefault("client.net.connection.delay", 1000));
    public static final int CONFIG_CLIENT_CONNECTION_MAX_ATTEMPTS = Integer.parseInt((String) CONFIG.getOrDefault("client.net.connection.max_attempts", 1));


    // Clients config parameters
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_ROOT = (String) CONFIG.getOrDefault("client.gui.path.fxml.root", "/client/GUI/fxml/");
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_LOGIN = (String) CONFIG.getOrDefault("client.gui.path.fxml.login", "login.fxml");
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_COMMANDS = (String) CONFIG.getOrDefault("client.gui.path.fxml.commands", "commands.fxml");
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_OPPONENT_PLAYER_INFO = (String) CONFIG.getOrDefault("client.gui.path.fxml.opponent_player_info", "opponent_player_info.fxml");
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_PLAYER_BOARD = (String) CONFIG.getOrDefault("client.gui.path.fxml.player_board", "player_board.fxml");
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_BOARD_SQUARE = (String) CONFIG.getOrDefault("client.gui.path.fxml.board_square", "board_square.fxml");
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_MAIN = (String) CONFIG.getOrDefault("client.gui.path.fxml.main", "main.fxml");
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_POWER_UP_CARD = (String) CONFIG.getOrDefault("client.gui.path.fxml.power_up_card", "power_up_card.fxml");
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_WEAPON_CARD = (String) CONFIG.getOrDefault("client.gui.path.fxml.weapon_card", "weapon_card.fxml");
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_AVATAR = (String) CONFIG.getOrDefault("client.gui.path.fxml.avatar", "avatar.fxml");
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_MARK = (String) CONFIG.getOrDefault("client.gui.path.fxml.mark", "mark.fxml");
    public static final String CONFIG_CLIENT_GUI_PATH_FXML_SKULL = (String) CONFIG.getOrDefault("client.gui.path.fxml.skull", "skull.fxml");

    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_ROOT = (String) CONFIG.getOrDefault("client.gui.path.assets.root", "/client/GUI/assets/");
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_CARDS = (String) CONFIG.getOrDefault("client.gui.path.assets.cards", "cards/");
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_CARDS_ROTATED = (String) CONFIG.getOrDefault("client.gui.path.assets.cards_rotated", "cards/rotated/");
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_MAPS = (String) CONFIG.getOrDefault("client.gui.path.assets.maps", "maps/");
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_AMMO = (String) CONFIG.getOrDefault("client.gui.path.assets.ammo", "ammo/");
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_PLAYER_BOARDS = (String) CONFIG.getOrDefault("client.gui.path.assets.playerboards", "playerboards/");
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_AVATARS = (String) CONFIG.getOrDefault("client.gui.path.assets.avatars", "avatars/");
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_ICONS = (String) CONFIG.getOrDefault("client.gui.path.assets.icons", "icons/");
    public static final String CONFIG_CLIENT_GUI_PATH_ASSETS_MARKS = (String) CONFIG.getOrDefault("client.gui.path.assets.marks", "marks/");

    public static final String CONFIG_CLIENT_GUI_ASSETS_PREFIX_MAP = (String) CONFIG.getOrDefault("client.gui.assets.prefix.map", "Map_");
    public static final String CONFIG_CLIENT_GUI_ASSETS_PREFIX_POWER_UP = (String) CONFIG.getOrDefault("client.gui.assets.prefix.power_up", "AD_powerups_IT_");
    public static final String CONFIG_CLIENT_GUI_ASSETS_PREFIX_WEAPON = (String) CONFIG.getOrDefault("client.gui.assets.prefix.weapon", "AD_weapons_IT_");
    public static final String CONFIG_CLIENT_GUI_ASSETS_PREFIX_AMMO = (String) CONFIG.getOrDefault("client.gui.assets.prefix.ammo", "AD_ammo_");
    public static final String CONFIG_CLIENT_GUI_ASSETS_PREFIX_PLAYERBOARD = (String) CONFIG.getOrDefault("client.gui.assets.prefix.playerboard", "PlayerBoard_");
    public static final String CONFIG_CLIENT_GUI_ASSETS_PREFIX_AVATAR = (String) CONFIG.getOrDefault("client.gui.assets.prefix.avatar", "avatar_");
    public static final String CONFIG_CLIENT_GUI_ASSETS_PREFIX_MARK = (String) CONFIG.getOrDefault("client.gui.assets.prefix.mark", "mark_");
    public static final String CONFIG_CLIENT_GUI_ASSETS_PREFIX_ICONS_ARROW = (String) CONFIG.getOrDefault("client.gui.assets.prefix.icons.arrow", "arrow_");

    public static final String CONFIG_CLIENT_GUI_ASSETS_SUFFIX_PLAYERBOARD = (String) CONFIG.getOrDefault("client.gui.assets.suffix.playerboard", "_FF");

    public static final String CONFIG_CLIENT_GUI_ASSETS_ID_HIDDEN_CARD = (String) CONFIG.getOrDefault("client.gui.assets.id.hidden_card", "02");
    public static final String CONFIG_CLIENT_GUI_ASSETS_ID_HIDDEN_AMMO = (String) CONFIG.getOrDefault("client.gui.assets.id.hidden_ammo", "04");
    public static final String CONFIG_CLIENT_GUI_ASSETS_ID_SKULL = (String) CONFIG.getOrDefault("client.gui.assets.id.skull", "skull");
    public static final String CONFIG_CLIENT_GUI_ASSETS_ID_SKULL_ROTATED = (String) CONFIG.getOrDefault("client.gui.assets.id.skull_rotated", "skull_rotated");

    private Config() {
        throw new AssertionError();
    }

    public static String get(String key) {
        return CONFIG.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return CONFIG.getProperty(key, defaultValue);
    }
}