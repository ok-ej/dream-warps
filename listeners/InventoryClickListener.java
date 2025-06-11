package cc.dreamcode.dreamwarp.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import cc.dreamcode.dreamwarp.Main;
import cc.dreamcode.dreamwarp.models.Warp;

import java.util.Map;

public class InventoryClickListener implements Listener {

    private final Main plugin;

    public InventoryClickListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        String title = plugin.getConfigManager().getGuiTitle();

        if (!event.getView().getTitle().equals(title)) {
            return;
        }

        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.GRAY_STAINED_GLASS_PANE) {
            return;
        }

        int slot = event.getSlot();
        Map<String, Warp> warps = plugin.getWarpManager().getAllWarps();

        for (Warp warp : warps.values()) {
            if (warp.getGuiSlot() == slot && player.hasPermission(warp.getPermission())) {
                player.closeInventory();

                if (plugin.getTeleportManager().isTeleporting(player)) {
                    player.sendMessage("§cJuż się teleportujesz!");
                    return;
                }

                plugin.getTeleportManager().startTeleport(player, warp.getLocation(), warp.getName());
                break;
            }
        }
    }
}
