package cc.dreamcode.dreamwarp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import cc.dreamcode.dreamwarp.Main;
import cc.dreamcode.dreamwarp.models.Warp;

public class WarpCommand implements CommandExecutor {

    private final Main plugin;

    public WarpCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (!sender.hasPermission("dreamwarp.use")) {
            plugin.getMessageManager().sendMessage(player, "command-no-permission");
            return true;
        }

        if (args.length != 1) {
            plugin.getMessageManager().sendMessage(player, "warp-warp-usage");
            return true;
        }

        String warpName = args[0];

        Warp warp = plugin.getWarpManager().getWarp(warpName);
        if (warp == null) {
            plugin.getMessageManager().sendMessage(player, "warp-not-found", "name", warpName);
            return true;
        }

        if (!player.hasPermission(warp.getPermission())) {
            plugin.getMessageManager().sendMessage(player, "warp-no-permission");
            return true;
        }

        if (plugin.getTeleportManager().isTeleporting(player)) {
            plugin.getMessageManager().sendMessage(player, "warp-already-teleporting");
            return true;
        }

        plugin.getTeleportManager().startTeleport(player, warp.getLocation(), warp.getName());
        return true;
    }
}
