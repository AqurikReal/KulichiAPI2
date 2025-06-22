package fun.aqurik.KulichiAPI;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;


public final class KulichiAPI extends JavaPlugin implements Listener {

    public ArrayList<UUID> sued = new ArrayList<>(Arrays.asList());
    public FileConfiguration cfg;
    public ArrayList<Location> unbreakable = new ArrayList<>(Arrays.asList());
    public boolean forceBanned = false;
    public boolean isKulichiBanned = false;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration cfg = getConfig();
        isKulichiBanned = cfg.getBoolean("isKulichiBanned", false);

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        getServer().getPluginManager().registerEvents(this, this);
        getCommand("getmorekulich").setExecutor(new getKulichi());
        getCommand("bankulichi").setExecutor(new unfun(this));
        getCommand("fban").setExecutor(new ban(this));
        getCommand("chlen").setExecutor(new chlen(this));

        getLogger().info("Йоу йоу йоу я живой");


    }




    @Override
    public void onDisable() {
        saveDefaultConfig();
        getLogger().info("Сохраняю конфиг...");
        FileConfiguration cfg = getConfig();
        cfg.set("isKulichiBanned", isKulichiBanned);
        saveConfig();
        getLogger().info("Конфиг сохранен");
        getLogger().info("Выключаюсь...");

    }

    @EventHandler
    public void delete(PlayerDropItemEvent event){
        if (isKulichiBanned){
            if (!event.getPlayer().hasPermission("KulichiAPI.banKulichi.bypass") && !forceBanned) {
                if (event.getItemDrop().getItemStack().getType() == Material.BREAD) {

                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 1.0f);
                    event.getPlayer().sendMessage("§7[KulichiAPI] §cКуличи были заблокированы на этом сервере.");
                    event.getItemDrop().remove();
                }
            }
        }
    }

    @EventHandler
    public void delete2(BlockDispenseEvent event){
        if (isKulichiBanned){
            if (event.getItem().getType() == Material.BREAD){
                event.setCancelled(true);
            }
        }

    }
    @EventHandler
    public void delete3(ItemSpawnEvent event) {
        if (forceBanned) {
            if (event.getEntityType() == EntityType.ITEM) {
                Item item = event.getEntity();
                if (item.getItemStack().getType() == Material.BREAD) {
                    event.setCancelled(true);
                }
            }
        }
    }






    public void toggleKulichiLmfao() {
        isKulichiBanned = !isKulichiBanned;
    }

    public void forceKulichiLmfao(){
        forceBanned = !forceBanned;
        if (!isKulichiBanned){
            isKulichiBanned = true;
        }else{
            if (forceBanned){
                isKulichiBanned = true;
            }else{
                isKulichiBanned = false;
            }
        }

    }


}

