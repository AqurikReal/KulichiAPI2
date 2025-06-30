package fun.aqurik.KulichiAPI;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

/**
 * Command to create a temporary structure.
 */
public class ChlenCommand implements CommandExecutor {
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final KulichiAPI plugin;

    public ChlenCommand(KulichiAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageManager.getMessage("error.player-only", plugin.getConfig().getString("language", "en")));
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("KulichiAPI.chlen.use")) {
            player.sendMessage(MessageManager.getMessage("error.no-permission", plugin.getConfig().getString("language", "en"))
                    .replace("%permission%", "KulichiAPI.chlen.use"));
            return true;
        }

        Location baseLoc = player.getLocation().clone();
        if (!baseLoc.getBlock().getType().isAir()) {
            player.sendMessage(MessageManager.getMessage("chlen.no-space", plugin.getConfig().getString("language", "en")));
            return true;
        }

        Location below = baseLoc.clone().add(0, -1, 0);
        if (!below.getBlock().getType().isSolid()) {
            player.sendMessage(MessageManager.getMessage("chlen.no-ground", plugin.getConfig().getString("language", "en")));
            return true;
        }

        Location[] checkLocations = {
                baseLoc.clone().add(0, 1, 0),
                baseLoc.clone().add(0, 2, 0),
                baseLoc.clone().add(0, 3, 0),
                baseLoc.clone().add(1, 0, 0),
                baseLoc.clone().add(-1, 0, 0),
                baseLoc.clone().add(0, 4, 0)
        };

        for (Location loc : checkLocations) {
            if (!loc.getBlock().getType().isAir()) {
                player.sendMessage(MessageManager.getMessage("chlen.no-space", plugin.getConfig().getString("language", "en")));
                return true;
            }
        }

        long cooldownTime = plugin.getConfig().getLong("chlen.cooldown", 600);
        if (cooldowns.containsKey(player.getUniqueId())) {
            long timeLeft = ((cooldowns.get(player.getUniqueId()) / 1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
            if (timeLeft > 0) {
                player.sendMessage(MessageManager.getMessage("chlen.cooldown", plugin.getConfig().getString("language", "en"))
                        .replace("%time%", String.valueOf(timeLeft)));
                return true;
            }
        }
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());

        Location[] structure = {
                baseLoc,
                baseLoc.clone().add(0, 1, 0),
                baseLoc.clone().add(0, 2, 0),
                baseLoc.clone().add(0, 3, 0),
                baseLoc.clone().add(1, 0, 0),
                baseLoc.clone().add(-1, 0, 0)
        };

        structure[0].getBlock().setType(Material.PINK_WOOL);
        structure[1].getBlock().setType(Material.PINK_WOOL);
        structure[2].getBlock().setType(Material.PINK_WOOL);
        structure[3].getBlock().setType(Material.RED_WOOL);
        structure[4].getBlock().setType(Material.PINK_WOOL);
        structure[5].getBlock().setType(Material.PINK_WOOL);

        for (Location loc : structure) {
            plugin.unbreakable.add(loc.toBlockLocation());
        }

        Location teleportLoc = baseLoc.clone().add(0, 4, 0);
        teleportLoc.setYaw(player.getLocation().getYaw());
        teleportLoc.setPitch(player.getLocation().getPitch());
        player.teleport(teleportLoc);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Location loc : structure) {
                    plugin.unbreakable.remove(loc.toBlockLocation());
                    loc.getBlock().setType(Material.AIR);
                }
            }
        }.runTaskLater(plugin, 60);

        LogManager.logInfo("Player " + player.getName() + " executed /chlen command at " + baseLoc);
        return true;
    }
}