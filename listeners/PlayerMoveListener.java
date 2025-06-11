package cc.dreamcode.dreamwarp.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import cc.dreamcode.dreamwarp.Main;

public class PlayerMoveListener implements Listener {

    private final Main plugin;

    public PlayerMoveListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!plugin.getTeleportManager().isTeleporting(player)) {
            return;
        }

        Location from = event.getFrom();
        Location to = event.getTo();

        if (to == null) {
            return;
        }

        Location startLocation = plugin.getTeleportManager().getTeleportStartLocation(player);
        if (startLocation == null) {
            return;
        }

        double threshold = plugin.getConfigManager().getMovementThreshold();
        double distance = startLocation.distance(to);

        if (distance > threshold) {
            plugin.getTeleportManager().cancelTeleport(player);
        }
    }
}
