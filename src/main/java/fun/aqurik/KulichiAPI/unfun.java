// Забанить куличи

package fun.aqurik.KulichiAPI;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class unfun implements CommandExecutor {

    private final KulichiAPI plugin;
    public unfun(KulichiAPI plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("KulichiAPI.banKulichi")){
            if (!plugin.forceBanned) {
                plugin.toggleKulichiLmfao();
                player.sendMessage("§7[KulichiAPI] §aКуличилы были " + (plugin.isKulichiBanned ? "забанены :((" : "разбанены *yay*"));
                if (plugin.isKulichiBanned) {
                    Bukkit.broadcastMessage("§a[Ban]§e Игрок " + player.getName() + " §cЗаблокировал " + "§eРазброс куличей на сервере!");
                }else{
                    Bukkit.broadcastMessage("§e[Ban]§a Игрок " + player.getName() + " §2Разблокировал " + "§aРазброс куличей на сервере!");
                }
            }else{
                player.sendMessage("§7[KulichiAPI] §4Ошибка! §cНевозможно изменить состояние куличей! Включен force ban.");
            }
        }else{
            player.sendMessage("§7[KulichiAPI] §aУ вас нету права §cKulichiAPI.banKulichi");
        }
        return true;
    }




    }

