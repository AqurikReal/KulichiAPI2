package fun.aqurik.KulichiAPI;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Command to give kulichi to players.
 */
public class GetKulichiCommand implements CommandExecutor {
    private final KulichiAPI plugin;

    public GetKulichiCommand(KulichiAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageManager.getMessage("error.player-only", plugin.getConfig().getString("language", "en")));
            return true;
        }
        Player player = (Player) sender;
        if (player.hasPermission("KulichiAPI.getKulichi")) {
            int amount = plugin.getConfig().getInt("kulichi.amount", 10);
            ItemStack kulich = new ItemStack(KulichiAPI.KULICH_MATERIAL, amount);
            ItemMeta meta = kulich.getItemMeta();
            meta.setDisplayName(ChatColor.WHITE + MessageManager.getMessage("item.kulich-name", plugin.getConfig().getString("language", "en")));
            kulich.setItemMeta(meta);
            player.getInventory().addItem(kulich);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            player.sendMessage(MessageManager.getMessage("get-kulichi.success", plugin.getConfig().getString("language", "en")));
            LogManager.logInfo("Player " + player.getName() + " received " + amount + " kulichi");
        } else {
            player.sendMessage(MessageManager.getMessage("error.no-permission", plugin.getConfig().getString("language", "en"))
                    .replace("%permission%", "KulichiAPI.getKulichi"));
        }
        return true;
    }
}