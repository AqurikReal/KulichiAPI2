// Получить куличи (нужно определенное право)

package fun.aqurik.KulichiAPI;


import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class getKulichi implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (p.hasPermission("KulichiAPI.getKulichi")) {
            ItemStack kulich = new ItemStack(Material.BREAD, 10);
            ItemMeta kulichdeach = kulich.getItemMeta();
            kulichdeach.setDisplayName("§fКулич");
            kulich.setItemMeta(kulichdeach);

            p.give(kulich);
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            p.sendMessage("§7[KulichiAPI] §aЙоу йоу йоу, держи куличи");
        }else{
            p.sendMessage("§7[KulichiAPI] §aУ вас нету права §cKulichiAPI.getKulichi");
        }
        return true;
    }
}