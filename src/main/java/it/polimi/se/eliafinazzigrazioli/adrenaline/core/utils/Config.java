package it.polimi.se.eliafinazzigrazioli.adrenaline.core.utils;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

// App configuration manager
public final class Config {
    private static final String CONFIG_FILE_NAME = "app.properties";
    private static final String CONFIG_FILE_PATH = "resources/app.properties";

    private static final Logger LOGGER = Logger.getLogger(Config.class.getName());
    private static final Properties CONFIG = new Properties();

    static {
        try {
            CONFIG.load(Config.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
        } catch (IOException e) {
            LOGGER.severe(Messages.MESSAGE_EXCEPTIONS_SERVER_CONFIG_FILE_NOT_FOUND + CONFIG_FILE_PATH);
        }
    }

    public static final String CONFIG_SERVER_MESSAGES_FILE_PATH = (String) CONFIG.getOrDefault("server.messages.messages_file_path", "resources/messages.properties");
    public static final String CONFIG_SERVER_MESSAGES_FILE_NAME = (String) CONFIG.getOrDefault("server.messages.messages_file_name", "messages.properties");

    public static final int CONFIG_SERVER_SOCKET_PORT = Integer.parseInt((String) CONFIG.getOrDefault("server.socket.port", 9999));

    public static final int CONFIG_SERVER_NEW_GAME_TIMEOUT = Integer.parseInt((String) CONFIG.getOrDefault("server.new_game_timeout", 30000));
    public static final int CONFIG_MATCH_TURN_TIMEOUT = Integer.parseInt((String) CONFIG.getOrDefault("server.new_game_timeout", 30000));

    public static final String CONFIG_CLIENT_GUI_ASSETS_MAP_PREFIX = (String) CONFIG.getOrDefault("client.gui.assets.map.prefix", "Map_");
    public static final String CONFIG_CLIENT_GUI_ASSETS_MAP_FORMAT = (String) CONFIG.getOrDefault("client.gui.assets.map.format", ".png");

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