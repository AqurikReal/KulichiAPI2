package fun.aqurik.KulichiAPI;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command to toggle ban of kulichi scattering.
 */
public class BanKulichiCommand implements CommandExecutor {

    private final KulichiAPI plugin;

    public BanKulichiCommand(KulichiAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageManager.getMessage("error.player-only", plugin.getConfig().getString("language", "en")));
            return true;
        }
        Player player = (Player) sender;
        if (player.hasPermission("KulichiAPI.banKulichi")) {
            if (!plugin.forceBanned) {
                plugin.toggleKulichiBan();
                player.sendMessage(MessageManager.getMessage("ban.toggled", plugin.getConfig().getString("language", "en"))
                        .replace("%status%", plugin.isKulichiBanned ? MessageManager.getMessage("status.banned", plugin.getConfig().getString("language", "en"))
                                : MessageManager.getMessage("status.unbanned", plugin.getConfig().getString("language", "en"))));
                if (plugin.isKulichiBanned) {
                    Bukkit.broadcastMessage(MessageManager.getMessage("ban.broadcast-banned", plugin.getConfig().getString("language", "en"))
                            .replace("%player%", player.getName()));
                } else {
                    Bukkit.broadcastMessage(MessageManager.getMessage("ban.broadcast-unbanned", plugin.getConfig().getString("language", "en"))
                            .replace("%player%", player.getName()));
                }
                LogManager.logInfo("Player " + player.getName() + " toggled kulichi ban to " + plugin.isKulichiBanned);
            } else {
                player.sendMessage(MessageManager.getMessage("error.force-ban-active", plugin.getConfig().getString("language", "en")));
            }
        } else {
            player.sendMessage(MessageManager.getMessage("error.no-permission", plugin.getConfig().getString("language", "en"))
                    .replace("%permission%", "KulichiAPI.banKulichi"));
        }
        return true;
    }
}