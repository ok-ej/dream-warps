package cc.dreamcode.dreamwarp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import cc.dreamcode.dreamwarp.Main;

public class DelWarpCommand implements CommandExecutor {

    private final Main plugin;

    public DelWarpCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (!sender.hasPermission("dreamwarp.admin")) {
            plugin.getMessageManager().sendMessage(player, "command-no-permission");
            return true;
        }

        if (args.length != 1) {
            plugin.getMessageManager().sendMessage(player, "warp-delwarp-usage");
            return true;
        }

        String warpName = args[0];

        if (!plugin.getWarpManager().warpExists(warpName)) {
            plugin.getMessageManager().sendMessage(player, "warp-not-found", "name", warpName);
            return true;
        }

        if (plugin.getWarpManager().isManualWarp(warpName)) {
            plugin.getMessageManager().sendMessage(player, "command-no-permission");
            return true;
        }

        plugin.getWarpManager().removeWarp(warpName);
        plugin.getMessageManager().sendMessage(player, "warp-deleted", "name", warpName);

        return true;
    }
}
