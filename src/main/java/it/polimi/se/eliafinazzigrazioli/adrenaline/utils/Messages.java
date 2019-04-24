package it.polimi.se.eliafinazzigrazioli.adrenaline.utils;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

// Centralized message manager
public final class Messages {
    private static final String MESSAGES_FILE_PATH = Config.get("messages.messages_file_path", "resources/messages.properties");
    private static final String MESSAGES_FILE_NAME = Config.get("messages.messages_file_name", "messages.properties");

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

    public static String get(String key) {
        return MESSAGES.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return MESSAGES.getProperty(key, defaultValue);
    }
}
