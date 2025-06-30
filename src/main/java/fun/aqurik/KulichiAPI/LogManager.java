package fun.aqurik.KulichiAPI;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Manages logging for the plugin.
 */
public class LogManager {
    private static Logger logger;
    private static boolean loggingEnabled;

    /**
     * Initializes the logger.
     * @param plugin The plugin instance.
     */
    public static void init(JavaPlugin plugin) {
        logger = plugin.getLogger();
        loggingEnabled = plugin.getConfig().getBoolean("logging.enabled", true);
    }

    /**
     * Logs an info message.
     * @param message The message to log.
     */
    public static void logInfo(String message) {
        if (loggingEnabled) {
            logger.log(Level.INFO, message);
        }
    }

    /**
     * Logs a warning message.
     * @param message The message to log.
     */
    public static void logWarning(String message) {
        if (loggingEnabled) {
            logger.log(Level.WARNING, message);
        }
    }

    /**
     * Logs a severe message.
     * @param message The message to log.
     */
    public static void logSevere(String message) {
        if (loggingEnabled) {
            logger.log(Level.SEVERE, message);
        }
    }
}