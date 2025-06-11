package cc.dreamcode.dreamwarp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import cc.dreamcode.dreamwarp.Main;

public class aWarpCommand implements CommandExecutor {

    private final Main plugin;

    public aWarpCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cTa komenda może być wykonana tylko przez gracza!");
            return true;
        }

        Player player = (Player) sender;

        if (!sender.hasPermission("dreamwarp.admin")) {
            plugin.getMessageManager().sendMessage(player, "command-no-permission");
            return true;
        }

        if (args.length == 0) {
            plugin.getMessageManager().sendMessage(player, "awarp-usage");
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "reload":
                reloadPlugin(player);
                break;
            default:
                plugin.getMessageManager().sendMessage(player, "awarp-usage");
                break;
        }

        return true;
    }

    private void reloadPlugin(Player player) {
        try {
            plugin.getConfigManager().reloadConfig();
            plugin.getMessageManager().reloadMessages();
            plugin.getWarpManager().reloadWarps();
            plugin.getMessageManager().sendMessage(player, "awarp-reloaded");
        } catch (Exception e) {
            plugin.getMessageManager().sendMessage(player, "awarp-reload-error");
            plugin.getLogger().severe("Błąd podczas przeładowywania konfiguracji: " + e.getMessage());
        }
    }
}
