package fun.aqurik.KulichiAPI;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages localization messages for the plugin.
 */
public class MessageManager {
    private static final Map<String, YamlConfiguration> messages = new HashMap<>();
    private static final String[] SUPPORTED_LANGUAGES = {"en", "ru"};

    /**
     * Loads message files for supported languages.
     * @param plugin The plugin instance.
     */
    public static void loadMessages(JavaPlugin plugin) {
        File messagesFolder = new File(plugin.getDataFolder(), "messages");
        if (!messagesFolder.exists()) {
            messagesFolder.mkdirs();
        }

        for (String lang : SUPPORTED_LANGUAGES) {
            File file = new File(messagesFolder, "messages_" + lang + ".yml");
            if (!file.exists()) {
                try (InputStream in = plugin.getResource("messages_" + lang + ".yml")) {
                    if (in != null) {
                        Files.copy(in, file.toPath());
                    } else {
                        LogManager.logWarning("Message file for language " + lang + " not found in resources!");
                    }
                } catch (IOException e) {
                    LogManager.logSevere("Failed to copy messages_" + lang + ".yml: " + e.getMessage());
                }
            }
            messages.put(lang, YamlConfiguration.loadConfiguration(file));
        }
    }

    /**
     * Gets a localized message.
     * @param key The message key.
     * @param language The language code.
     * @return The localized message or a default message if not found.
     */
    public static String getMessage(String key, String language) {
        YamlConfiguration config = messages.getOrDefault(language, messages.get("en"));
        String message = config.getString(key, "Message not found: " + key);
        return KulichiAPI.PREFIX + ChatColor.translateAlternateColorCodes('&', message);
    }
}