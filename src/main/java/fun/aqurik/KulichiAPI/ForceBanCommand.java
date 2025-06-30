package fun.aqurik.KulichiAPI;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command to toggle force ban of kulichi.
 */
public class ForceBanCommand implements CommandExecutor {

    private final KulichiAPI plugin;

    public ForceBanCommand(KulichiAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageManager.getMessage("error.player-only", plugin.getConfig().getString("language", "en")));
            return true;
        }
        Player player = (Player) sender;
        if (player.hasPermission("KulichiAPI.fban")) {
            plugin.toggleForceBan();
            player.sendMessage(MessageManager.getMessage("force-ban.toggled", plugin.getConfig().getString("language", "en"))
                    .replace("%status%", plugin.isKulichiBanned ? MessageManager.getMessage("status.disabled", plugin.getConfig().getString("language", "en"))
                            : MessageManager.getMessage("status.enabled", plugin.getConfig().getString("language", "en"))));
            if (plugin.forceBanned) {
                Bukkit.broadcastMessage(MessageManager.getMessage("force-ban.broadcast-disabled", plugin.getConfig().getString("language", "en"))
                        .replace("%player%", player.getName()));
            } else {
                Bukkit.broadcastMessage(MessageManager.getMessage("force-ban.broadcast-enabled", plugin.getConfig().getString("language", "en"))
                        .replace("%player%", player.getName()));
            }
            LogManager.logInfo("Player " + player.getName() + " toggled force ban to " + plugin.forceBanned);
        } else {
            player.sendMessage(MessageManager.getMessage("error.no-permission", plugin.getConfig().getString("language", "en"))
                    .replace("%permission%", "KulichiAPI.fban"));
        }
        return true;
    }
}