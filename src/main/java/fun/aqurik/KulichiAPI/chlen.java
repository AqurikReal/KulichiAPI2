// Легендарный /chlen (тоже по правам)

package fun.aqurik.KulichiAPI;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class chlen implements CommandExecutor {
    public HashMap<String, Long> cooldowns = new HashMap<String, Long>();
    private final KulichiAPI plugin;
    public chlen(KulichiAPI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (!p.hasPermission("KulichiAPI.chlen")){
            p.sendMessage("§7[KulichiAPI] §aУ вас нету права §cKulichiAPI.chlen");
            return true;
        }

        if(cooldowns.containsKey(p.getName())) {
            long left = ((cooldowns.get(p.getName())/1000)+600) - (System.currentTimeMillis()/1000);
            if(left>0) {
                p.sendMessage("§7[KulichiAPI] §aПодождите "+ left +"сек. Перед использованием команд!");
                return true;
            }
        }
        cooldowns.put(p.getName(), System.currentTimeMillis());



        if (!p.getLocation().getBlock().getType().isAir()) {
            p.sendMessage("§7[KulichiAPI] §cНевозможно поставить член! Мало места!");
            return true;
        }



        Location locafter = p.getLocation().clone();
        Location verh = p.getLocation().clone().add(0, 1, 0).setRotation(0,0);
        Location verh5 = p.getLocation().clone().add(0, 2, 0).setRotation(0,0);
        Location verh2 = p.getLocation().clone().add(0, 3, 0).setRotation(0,0);
        Location verh3 = p.getLocation().clone().add(1, 0, 0).setRotation(0,0);
        Location verh4 = p.getLocation().clone().add(-1, 0, 0).setRotation(0,0);
        Location verhtp = p.getLocation().clone().add(0, 4, 0).setRotation(0,0);
        Location verhtp2 = p.getLocation().clone().add(0, 4, 0).setRotation(0,0);
        Location niz1 = p.getLocation().clone().add(0, -1, 0).setRotation(0,0);
        if (niz1.getBlock().getType() == Material.AIR){
            p.sendMessage("§7[KulichiAPI] §cНельзя ставить члены в воздухе");
            return true;
        }
        if (niz1.getBlock().getType() == Material.RED_WOOL){
            p.sendMessage("§7[KulichiAPI] §cНельзя ставить члены на члены");
            return true;
        }
        if (!verh.getBlock().getType().isAir()){p.sendMessage("§7[KulichiAPI] §cНевозможно поставить член! Мало места!"); return true;}
        if (!verh5.getBlock().getType().isAir()){p.sendMessage("§7[KulichiAPI] §cНевозможно поставить член! Мало места!"); return true;}
        if (!verh2.getBlock().getType().isAir()){p.sendMessage("§7[KulichiAPI] §cНевозможно поставить член! Мало места!"); return true;}
        if (!verh3.getBlock().getType().isAir()){p.sendMessage("§7[KulichiAPI] §cНевозможно поставить член! Мало места!"); return true;}
        if (!verh4.getBlock().getType().isAir()){p.sendMessage("§7[KulichiAPI] §cНевозможно поставить член! Мало места!"); return true;}
        if (!verhtp.getBlock().getType().isAir()){p.sendMessage("§7[KulichiAPI] §cНевозможно поставить член! Мало места!"); return true;}
        if (!verhtp2.getBlock().getType().isAir()){p.sendMessage("§7[KulichiAPI] §cНевозможно поставить член! Мало места!"); return true;}


        p.getLocation().getBlock().setType(Material.PINK_WOOL);
        verh.getBlock().setType(Material.PINK_WOOL);
        verh5.getBlock().setType(Material.PINK_WOOL);
        verh2.getBlock().setType(Material.RED_WOOL);
        verh3.getBlock().setType(Material.PINK_WOOL);
        verh4.getBlock().setType(Material.PINK_WOOL);

        verh.getBlock().getDrops().clear();
        verh2.getBlock().getDrops().clear();
        verh3.getBlock().getDrops().clear();
        verh4.getBlock().getDrops().clear();
        locafter.getBlock().getDrops().clear();
        verh5.getBlock().getDrops().clear();
        p.teleport(p.getLocation().clone().add(0, 4, 0).setRotation(0,0));
        plugin.unbreakable.add(verh.toBlockLocation());
        plugin.unbreakable.add(verh2.toBlockLocation());
        plugin.unbreakable.add(verh3.toBlockLocation());
        plugin.unbreakable.add(verh4.toBlockLocation());
        plugin.unbreakable.add(verh5.toBlockLocation());
        plugin.unbreakable.add(locafter.toBlockLocation());
        new BukkitRunnable() {
            public void run() {
                plugin.unbreakable.remove(verh.toBlockLocation());
                plugin.unbreakable.remove(verh2.toBlockLocation());
                plugin.unbreakable.remove(verh3.toBlockLocation());
                plugin.unbreakable.remove(verh4.toBlockLocation());
                plugin.unbreakable.remove(verh5.toBlockLocation());
                plugin.unbreakable.remove(locafter.toBlockLocation());
                locafter.getBlock().setType(Material.AIR);
                verh.getBlock().setType(Material.AIR);
                verh5.getBlock().setType(Material.AIR);
                verh2.getBlock().setType(Material.AIR);
                verh3.getBlock().setType(Material.AIR);
                verh4.getBlock().setType(Material.AIR);
            }
        }.runTaskLater(plugin, 60);

        return true;
    }
}