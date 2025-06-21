// Дядя денфри не ломай мне яица, это рофл команда. По-дефолту никто ее юзать не может

package fun.aqurik.KulichiAPI;

import com.destroystokyo.paper.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class sue implements CommandExecutor {
    private final KulichiAPI plugin;
    public sue(KulichiAPI plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        Player p = (Player) sender;

        if (!p.hasPermission("KulichiAPI.sue")){
            p.sendMessage("§7[KulichiAPI] §aУ вас нету права §cKulichiAPI.sue");
            return true;
        }

        if (args.length < 1) {
            p.sendMessage("§7[KulichiAPI] §cУкажите игрока!");
            return true;
        }



        boolean nasilna = false;
        try {
            if (args[1] != null || !args[1].isEmpty() || !args[1].isBlank()){
                if (args[1].startsWith("-") && args[1].toLowerCase().contains("f")){
                    if (p.hasPermission("KulichiAPI.sue.force")){
                        nasilna = true;
                    }
                }
            }
        } catch (Exception e) {}

        Player bad = Bukkit.getPlayer(args[0]);

        if (plugin.sued.contains(bad.getUniqueId())){
            p.sendMessage("§7[KulichiAPI] §cИгрока уже засудили!");
            return true;
        }

        if (bad == null) {
            p.sendMessage("§7[KulichiAPI] §cИгрок не найден.");
            return true;
        }
        int nulls = 0;
        int thorns = 4;

        if (!nasilna) {
            for (ItemStack item : bad.getInventory().getArmorContents()) {
                if (item != null) {
                    if (!item.containsEnchantment(Enchantment.THORNS)) {
                        thorns -= 1;
                    } else {
                        p.sendMessage("§7[KulichiAPI] §cНа броне игрока есть шипы!");
                        return true;
                    }
                } else {
                    thorns -= 1;
                    nulls += 1;
                }
            }
            if (nulls == 4) {
                p.sendMessage("§7[KulichiAPI] §cНа игроке нету брони!");
                return true;
            }
            if (thorns >= 1) {
                p.sendMessage("§7[KulichiAPI] §cНа броне игрока есть шипы!");
                return true;
            }
        }else{
            p.sendMessage("§7[KulichiAPI] §aНасильно судим игрока...");
        }

        plugin.sued.add(bad.getUniqueId());
        Bukkit.broadcastMessage("§a[Суд]§7 Игрок §c" + p.getName() + " §7Засудил " + "§c" + bad.getName());
        bad.setHealth(0.1);
        bad.setFoodLevel(1);
        bad.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 300000, 255));
        bad.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300000, 255));
        bad.sendTitle(new Title("§cВас засудили!"));
        bad.sendMessage("§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!");
        bad.sendMessage("§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!");
        bad.sendMessage("§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!");
        bad.sendMessage("§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!");
        bad.sendMessage("§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!");
        bad.sendMessage("§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!");
        bad.sendMessage("§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!\n§cВас засудили!");
        return true;
    }
}
