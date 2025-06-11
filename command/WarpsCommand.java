package cc.dreamcode.dreamwarp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import cc.dreamcode.dreamwarp.Main;
import cc.dreamcode.dreamwarp.models.Warp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WarpsCommand implements CommandExecutor {

    private final Main plugin;

    public WarpsCommand(Main plugin) {
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

        openWarpsGui(player);
        return true;
    }

    private void openWarpsGui(Player player) {
        String title = plugin.getConfigManager().getGuiTitle();
        int size = plugin.getConfigManager().getGuiSize();

        Inventory gui = Bukkit.createInventory(null, size, title);

        Map<String, Warp> warps = plugin.getWarpManager().getAllWarps();

        for (Warp warp : warps.values()) {
            if (player.hasPermission(warp.getPermission())) {
                ItemStack item = new ItemStack(warp.getIcon());
                ItemMeta meta = item.getItemMeta();

                if (meta != null) {
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', warp.getDisplayName()));

                    List<String> lore = new ArrayList<>();
                    String[] descriptionLines = warp.getDescription().split("\n");
                    for (String line : descriptionLines) {
                        lore.add(ChatColor.translateAlternateColorCodes('&', line));
                    }

                    meta.setLore(lore);
                    item.setItemMeta(meta);
                }

                if (warp.getGuiSlot() < size) {
                    gui.setItem(warp.getGuiSlot(), item);
                }
            }
        }

        for (int i = 0; i < size; i++) {
            if (gui.getItem(i) == null) {
                ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta meta = filler.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(" ");
                    filler.setItemMeta(meta);
                }
                gui.setItem(i, filler);
            }
        }

        player.openInventory(gui);
    }
}
