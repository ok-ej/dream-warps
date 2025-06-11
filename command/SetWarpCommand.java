package cc.dreamcode.dreamwarp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import cc.dreamcode.dreamwarp.Main;

import java.util.HashMap;
import java.util.Map;

public class SetWarpCommand implements CommandExecutor {

    private final Main plugin;

    public SetWarpCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getMessageManager().sendMessage((Player) sender, "command-not-player");
            return true;
        }

        if (!sender.hasPermission("dreamwarp.admin")) {
            plugin.getMessageManager().sendMessage((Player) sender, "command-no-permission");
            return true;
        }

        if (args.length != 3) {
            plugin.getMessageManager().sendMessage((Player) sender, "warp-setwarp-usage");
            return true;
        }

        Player player = (Player) sender;
        String warpName = args[0];

        int slot;
        try {
            slot = Integer.parseInt(args[1]);
            if (slot < 0 || slot >= plugin.getConfigManager().getGuiSize()) {
                Map<String, String> placeholders = new HashMap<>();
                placeholders.put("max", String.valueOf(plugin.getConfigManager().getGuiSize() - 1));
                plugin.getMessageManager().sendMessage(player, "warp-invalid-slot", placeholders);
                return true;
            }
        } catch (NumberFormatException e) {
            plugin.getMessageManager().sendMessage(player, "command-invalid-format", "input", args[1]);
            return true;
        }

        String permission = args[2];

        if (plugin.getWarpManager().warpExists(warpName)) {
            plugin.getMessageManager().sendMessage(player, "warp-already-exists", "name", warpName);
            return true;
        }

        plugin.getWarpManager().addWarp(warpName, player.getLocation(), slot, permission);
        plugin.getMessageManager().sendMessage(player, "warp-created", "name", warpName);

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("slot", String.valueOf(slot));
        placeholders.put("permission", permission);
        plugin.getMessageManager().sendMessage(player, "warp-setwarp-info", placeholders);

        return true;
    }
}
