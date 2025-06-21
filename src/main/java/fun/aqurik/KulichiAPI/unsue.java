// Дядя денфри не ломай мне яица, это рофл команда. По-дефолту никто ее юзать не может

package fun.aqurik.KulichiAPI;

import com.destroystokyo.paper.Title;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class unsue implements CommandExecutor {
    private final KulichiAPI plugin;
    public unsue(KulichiAPI plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Player p = (Player) sender;



        OfflinePlayer bad = Bukkit.getOfflinePlayer(args[0]);

        if (!p.hasPermission("KulichiAPI.unsue")){
            p.sendMessage("§7[KulichiAPI] §aУ вас нету права §cKulichiAPI.unsue");
            return true;
        }

        if (!plugin.sued.contains(bad.getUniqueId())){
            p.sendMessage("§7[KulichiAPI] §aИгрок не был засужен!");
            return true;
        }


        plugin.sued.remove(bad.getUniqueId());
        Bukkit.broadcastMessage("§a[Суд]§7 Игрок §c" + p.getName() + "§7 Снял статус виновного с " + "§c" + bad.getName());
        if (bad.isOnline()){
            Player badnew = Bukkit.getPlayer(args[0]);
            badnew.clearActivePotionEffects();
            badnew.setFoodLevel(20);
            badnew.setHealth(20);
            badnew.resetTitle();
            badnew.sendTitle(new Title("§aВас пощадили!"));
        }
        return true;
    }
}
