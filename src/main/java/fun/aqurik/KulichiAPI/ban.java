// Забанить куличи

package fun.aqurik.KulichiAPI;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ban implements CommandExecutor {

    private final KulichiAPI plugin;
    public ban(KulichiAPI plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("KulichiAPI.fban")){
            plugin.forceKulichiLmfao();
            player.sendMessage("§7[KulichiAPI] §cForce Ban - Куличи были " + (plugin.isKulichiBanned ? "отключены" : "включены" ) + " (Обновляется при перезапуске сервера)");
            if (plugin.forceBanned) {
                Bukkit.broadcastMessage("§a[FBan]§c Игрок " + player.getName() + " §4Выключил " + "§cВсе куличи на сервере!\n" + "§7[KulichiAPI] §aПросьба хранить куличи в инвентаре и не выкидывать их!!!!");
            }else{
                Bukkit.broadcastMessage("§a[FBan]§9 Игрок " + player.getName() + " §bВключил " + "§9Все куличи на сервере!");
            }
        }else{
            player.sendMessage("§7[KulichiAPI] §aУ вас нету права §cKulichiAPI.fbanKulichi");
        }
        return true;
    }




}
