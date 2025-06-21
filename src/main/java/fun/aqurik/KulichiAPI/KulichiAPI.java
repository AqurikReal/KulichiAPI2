package fun.aqurik.KulichiAPI;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.player.PlayerInputEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public final class KulichiAPI extends JavaPlugin implements Listener {

    public ArrayList<UUID> sued = new ArrayList<>(Arrays.asList());
    public FileConfiguration cfg;
    public ArrayList<Location> unbreakable = new ArrayList<>(Arrays.asList());
    public boolean isDeach = false;
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


        File sukalistpidorav = new File(getDataFolder(), "sued.yml");
        if (!sukalistpidorav.exists()) {saveDefaultConfig(); saveResource("sued.yml", false);}
        cfg = YamlConfiguration.loadConfiguration(sukalistpidorav);
        List<String> pidori = cfg.getStringList("sued");
        for (String uuidString : pidori) {
            sued.add(UUID.fromString(uuidString));
        }







        getServer().getPluginManager().registerEvents(this, this);
        getCommand("getmorekulich").setExecutor(new getKulichi());
        getCommand("bankulichi").setExecutor(new unfun(this));
        getCommand("fban").setExecutor(new ban(this));
        getCommand("sue").setExecutor(new sue(this));
        getCommand("unsue").setExecutor(new unsue(this));
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
        List<String> pidori = new ArrayList<>();
        for (UUID uuid : sued) {pidori.add(uuid.toString());}
        cfg.set("sued", pidori);
        try {
            cfg.save(new File(getDataFolder(), "sued.yml"));
        } catch (IOException e){
            getLogger().warning("йоуйоуйоу чото сломалось идите админу напишите");
        }
        getLogger().info("Конфиг сохранен");
        getLogger().info("Выключаюсь...");

    }

    @EventHandler
    public void onPlayerInput(PlayerInputEvent event){
        if (forceBanned){
            return;
        }
        if (isDeach){
            ItemStack kulich = new ItemStack(Material.BREAD, 1);
            ItemMeta kulichdeach = kulich.getItemMeta();


            kulichdeach.addEnchant(Enchantment.SHARPNESS, 1, true);
            kulichdeach.setDisplayName("§7[Basic] Кулич");
            kulich.setItemMeta(kulichdeach);


            event.getPlayer().give(kulich);
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);

            event.getPlayer().sendMessage("§7[KulichiAPI] §aЙоу йоу йоу, держи кулич");
        };



    };

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

    @EventHandler
    public void ban(PlayerJoinEvent event){
        if (sued.contains(event.getPlayer().getUniqueId())){
            event.getPlayer().kickPlayer("§cВас засудили");
            event.setJoinMessage(null);
        }
    }

    @EventHandler
    public void ban2(PlayerQuitEvent event){
        if (sued.contains(event.getPlayer().getUniqueId())){
            event.setQuitMessage(null);
        }

    }

    @EventHandler
    public void ban3(PlayerDeathEvent event){
        if (sued.contains(event.getPlayer().getUniqueId())){
            event.setDeathMessage(null);
            Bukkit.broadcastMessage("§a[KulichiAPI]§7 Игрок §c" + event.getPlayer().getName() + " §7Был §cПЕРМАНЕНТНО §7Забанен на этом сервере!");
            event.getPlayer().getInventory().clear();
            event.getPlayer().kickPlayer("§cВас засудили");

        }

    }

    @EventHandler
    public void banblock(BlockBreakEvent event){
        if (unbreakable.contains(event.getBlock().getLocation().toBlockLocation())){
            event.getPlayer().sendMessage("§7[KulichiAPI] §cНельзя ломать член!");
            event.setCancelled(true);
        }
    }


    public void toggleDeachLmfao() {
        isDeach = !isDeach;
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

