package it.polimi.se.eliafinazzigrazioli.adrenaline.utils;

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
            LOGGER.severe(Messages.get("app.exceptions.server.config_file_not_found") + CONFIG_FILE_PATH);
        }
    }

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