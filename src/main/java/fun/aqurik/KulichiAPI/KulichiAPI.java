package fun.aqurik.KulichiAPI;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Main plugin class for KulichiAPI.
 */
public final class KulichiAPI extends JavaPlugin implements Listener {

    public static final Material KULICH_MATERIAL = Material.BREAD;
    public static final String PREFIX = "§7[KulichiAPI] ";
    public ArrayList<UUID> sued = new ArrayList<>(Arrays.asList());
    public ArrayList<Location> unbreakable = new ArrayList<>(Arrays.asList());
    public boolean forceBanned = false;
    public boolean isKulichiBanned = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration cfg = getConfig();
        isKulichiBanned = cfg.getBoolean("isKulichiBanned", false);
        MessageManager.loadMessages(this);

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        getServer().getPluginManager().registerEvents(this, this);
        getCommand("getkulichi").setExecutor(new GetKulichiCommand(this));
        getCommand("getkulichi").setTabCompleter(new KulichiTabCompleter());
        getCommand("bankulichi").setExecutor(new BanKulichiCommand(this));
        getCommand("bankulichi").setTabCompleter(new KulichiTabCompleter());
        getCommand("fban").setExecutor(new ForceBanCommand(this));
        getCommand("fban").setTabCompleter(new KulichiTabCompleter());
        getCommand("chlen").setExecutor(new ChlenCommand(this));
        getCommand("chlen").setTabCompleter(new KulichiTabCompleter());

        LogManager.init(this);
        LogManager.logInfo("Plugin enabled successfully");
    }

    @Override
    public void onDisable() {
        LogManager.logInfo("Saving configuration...");
        FileConfiguration cfg = getConfig();
        cfg.set("isKulichiBanned", isKulichiBanned);
        saveConfig();
        LogManager.logInfo("Configuration saved");
        LogManager.logInfo("Plugin disabled");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDrop(PlayerDropItemEvent event) {
        Item item = event.getItemDrop();
        if (item.getItemStack().getType() == KULICH_MATERIAL) {
            if (!event.getPlayer().hasPermission("KulichiAPI.banKulichi.bypass") && (isKulichiBanned || forceBanned)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(MessageManager.getMessage("error.kulichi-banned", getConfig().getString("language", "en")));
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
                LogManager.logInfo("Player " + event.getPlayer().getName() + " attempted to drop kulichi, but it was blocked. isKulichiBanned=" + isKulichiBanned + ", forceBanned=" + forceBanned);
            } else if (event.getPlayer().hasPermission("KulichiAPI.banKulichi.bypass")) {
                LogManager.logInfo("Player " + event.getPlayer().getName() + " bypassed kulichi ban due to permission.");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockDispense(BlockDispenseEvent event) {
        if (event.getItem().getType() == KULICH_MATERIAL && (isKulichiBanned || forceBanned)) {
            event.setCancelled(true);
            LogManager.logInfo("Blocked kulichi dispense from dispenser at " + event.getBlock().getLocation());
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onItemSpawn(ItemSpawnEvent event) {
        if (forceBanned && event.getEntityType() == EntityType.ITEM) {
            Item item = (Item) event.getEntity();
            if (item.getItemStack().getType() == KULICH_MATERIAL) {
                event.setCancelled(true);
                LogManager.logInfo("Blocked kulichi spawn at " + event.getLocation());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        if (unbreakable.contains(event.getBlock().getLocation())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(MessageManager.getMessage("error.cannot-break", getConfig().getString("language", "en")));
            LogManager.logInfo("Player " + event.getPlayer().getName() + " attempted to break protected block at " + event.getBlock().getLocation());
        }
    }

    /**
     * Toggles the kulichi ban status and saves the config.
     */
    public void toggleKulichiBan() {
        isKulichiBanned = !isKulichiBanned;
        FileConfiguration cfg = getConfig();
        cfg.set("isKulichiBanned", isKulichiBanned);
        saveConfig();
        LogManager.logInfo("Kulichi ban toggled to " + isKulichiBanned);
    }

    /**
     * Toggles the force ban status and saves the config.
     */
    public void toggleForceBan() {
        forceBanned = !forceBanned;
        if (!isKulichiBanned) {
            isKulichiBanned = true;
        } else if (!forceBanned) {
            isKulichiBanned = false;
        }
        FileConfiguration cfg = getConfig();
        cfg.set("isKulichiBanned", isKulichiBanned);
        saveConfig();
        LogManager.logInfo("Force ban toggled to " + forceBanned);
    }
}