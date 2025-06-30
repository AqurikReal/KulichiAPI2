package fun.aqurik.KulichiAPI;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tab completer for KulichiAPI commands.
 */
public class KulichiTabCompleter implements TabCompleter {

    private static final List<String> COMMANDS = Arrays.asList("getkulichi", "bankulichi", "fban", "chlen");
    private static final List<String> GET_KULICHI_ALIASES = Arrays.asList("getkulich", "getmorekulich", "daikulich");
    private static final List<String> BAN_KULICHI_ALIASES = Arrays.asList("kban", "kulichiban", "block-kulichi");
    private static final List<String> FBAN_ALIASES = Arrays.asList("forceban");
    private static final List<String> CHLEN_ALIASES = Arrays.asList("penis", "pipirka");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        String commandName = command.getName().toLowerCase();

        // If no arguments, suggest commands or aliases based on permissions
        if (args.length == 0 || (args.length == 1 && args[0].isEmpty())) {
            if (sender.hasPermission("KulichiAPI.getKulichi")) {
                if (commandName.equals("getkulichi") || GET_KULICHI_ALIASES.contains(alias.toLowerCase())) {
                    completions.add("getkulichi");
                    completions.addAll(GET_KULICHI_ALIASES);
                }
            }
            if (sender.hasPermission("KulichiAPI.banKulichi")) {
                if (commandName.equals("bankulichi") || BAN_KULICHI_ALIASES.contains(alias.toLowerCase())) {
                    completions.add("bankulichi");
                    completions.addAll(BAN_KULICHI_ALIASES);
                }
            }
            if (sender.hasPermission("KulichiAPI.fban")) {
                if (commandName.equals("fban") || FBAN_ALIASES.contains(alias.toLowerCase())) {
                    completions.add("fban");
                    completions.addAll(FBAN_ALIASES);
                }
            }
            if (sender.hasPermission("KulichiAPI.chlen.use")) {
                if (commandName.equals("chlen") || CHLEN_ALIASES.contains(alias.toLowerCase())) {
                    completions.add("chlen");
                    completions.addAll(CHLEN_ALIASES);
                }
            }
        }

        // For commands that might accept player names as arguments (e.g., /fban, /bankulichi)
        if (args.length == 1 && (commandName.equals("fban") || commandName.equals("bankulichi"))) {
            if (sender.hasPermission("KulichiAPI." + commandName)) {
                for (Player player : sender.getServer().getOnlinePlayers()) {
                    completions.add(player.getName());
                }
            }
        }

        // Filter completions based on partial input
        return StringUtil.copyPartialMatches(args[args.length - 1], completions, new ArrayList<>());
    }
}